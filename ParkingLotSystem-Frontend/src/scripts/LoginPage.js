import NavBar from '@/components/NavBar.vue'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name : 'login-page',
    data() {
      return {
        email: '',
        password: '',
        errorEvent: '',
        userName: '',
      }
    },
    methods: {
      async loginAsManager() {
        this.errorEvent = '';
        try {
            let managerResponse = await AXIOS.get(`/api/manager/verify/`, 
            {params : {email: this.email, password: this.password}});
            
            console.log(managerResponse.data);
            if (!managerResponse.data) {
                this.errorEvent = 'wrong password';
                return;
            }
            // TODO: how to pass login dto? send body in request
            //let managerAuthenticationRes = await Axios.post('/api/authentication/login-manager')
            // Store the email of the employee in localStorage
            localStorage.setItem('managerEmail', this.email);

            //set user name
            let managerNameResponse = await AXIOS.get(`/api/manager/${this.email}`)
            localStorage.setItem('username', managerNameResponse.data.name);

            // Navigate to the monthly-customer page
            this.$router.push('/manager/dashboard');
        } catch (e) {
            this.errorEvent = "invalid manager email";
        }
        

      },
      async loginAsEmployee() {
        try {
            let employeeResponse = await AXIOS.get(`/api/employee/verify/`, 
            {params : {email: this.email, password: this.password}});

            if (!employeeResponse.data) {
                this.errorEvent = 'wrong password';
                return;
            }
            // TODO: how to pass login dto?
            //let managerAuthenticationRes = await Axios.post('/api/authentication/login-manager')
            
            // Store the email of the employee in localStorage
            localStorage.setItem('employeeEmail', this.email);

            //set user name
            let employeeNameResponse = await AXIOS.get(`/api/employee/${this.email}`)
            localStorage.setItem('username', employeeNameResponse.data.name);

            // Navigate to the monthly-customer page
            this.$router.push('/employee');
        } catch (e) {
            this.errorEvent = "invalid employee email";
        }
      },
      async loginAsCustomer() {
        try {
            let cusResponse = await AXIOS.get(`/api/monthly-customer/verify/`, 
            {params : {email: this.email, password: this.password}});
            console.log(cusResponse.data);
            if (!cusResponse.data) {
                this.errorEvent = 'wrong password';
                return;
            }
            // TODO: how to pass login dto?
            //let managerAuthenticationRes = await Axios.post('/api/authentication/login-manager')
       
            // Store the email of the customer in localStorage
            localStorage.setItem('monthlyCustomerEmail', this.email);

            //set user name
            let cusNameResponse = await AXIOS.get(`/api/monthly-customer/${this.email}`)
            localStorage.setItem('username', cusNameResponse.data.name);

            // Navigate to the monthly-customer page
            this.$router.push('/monthly-customer');
        } catch (e) {
            this.errorEvent = "invalid customer email";
        }
      }
    }
  }