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
            // TODO: navigate to manager page
            //this.$router.push('/ManagerPage');
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
            // TODO: navigate to employee page
            //this.$router.push('/employeePage');
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
            // TODO: navigate to employee page
            //this.$router.push('/employeePage');
        } catch (e) {
            this.errorEvent = "invalid customer email";
        }
      }
    }
  }