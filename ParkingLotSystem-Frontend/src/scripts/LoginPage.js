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
        userName: ''
      }
    },
    methods: {
      async loginAsManager() {
        this.errorEvent = '';
        try {
            let response = await AXIOS.post(
              "/api/authentication/login-manager", 
              {email: this.email, password: this.password}
            );
            
            // save token, email and name in local storage
            localStorage.setItem('token', response.data);
            localStorage.setItem('email', this.email);
            response = await AXIOS.get(`api/manager/${this.email}`);
            localStorage.setItem('username', response.data.name);
            localStorage.setItem('personType', 'manager')

            // Navigate to the main page
            this.$router.push('/manager/dashboard');
        } catch (e) {
            this.error(e);
        }
      },
      async loginAsEmployee() {
        this.errorEvent = '';
        try {
            let response = await AXIOS.post(
              "/api/authentication/login-employee", 
              {email: this.email, password: this.password}
            );
            
            // save token, email and name in local storage
            localStorage.setItem('token', response.data);
            localStorage.setItem('email', this.email);
            response = await AXIOS.get(`api/employee/${this.email}`);
            localStorage.setItem('username', response.data.name);
            localStorage.setItem('personType', 'employee')

            // Navigate to the main page
            this.$router.push('/employee');
        } catch (e) {
            this.error(e);
        }
      },
      async loginAsCustomer() {
        this.errorEvent = '';
        try {
            let response = await AXIOS.post(
              "/api/authentication/login-customer", 
              {email: this.email, password: this.password}
            );
            
            // save token, email and name in local storage
            localStorage.setItem('token', response.data);
            localStorage.setItem('email', this.email);
            response = await AXIOS.get(`api/monthly-customer/${this.email}`);
            localStorage.setItem('username', response.data.name);
            localStorage.setItem('personType', 'monthly-customer')

            // Navigate to the main page
            this.$router.push('/monthly-customer-reservation');
        } catch (e) {
            this.error(e);
        }
      },
      error(e) {
        if(e.hasOwnProperty("response")) {
          this.errorEvent = e.response.data.message;
        }
        else {
          this.errorEvent = e.message;
        }
      }
    }
  }