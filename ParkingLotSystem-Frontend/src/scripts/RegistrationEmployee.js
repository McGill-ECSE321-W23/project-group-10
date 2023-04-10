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
  name: "registrationEmployee",
  data() {
    return {
      name: '',
      email: '',
      phone: '',
      password: '',
      confirmPassword: '',
      showError: false,
      errorMessage: '',
    };
  },
  methods: {
    async register() {
      // Handle registration logic here
      try{
        if (this.password.localeCompare(this.confirmPassword)!=0){
          // throw exceptionMessage("password does not match confirmed passowrod");
          throw new Error("password does not match confirmed password!")
        }
        let response = await AXIOS.post(
          '/api/employee/${email}',
          {

          },
          {
            params: {
              name: this.name,
              phone: this.phone,
              password: this.password},
            // headers: {}
          }
        );
      } catch(e){
        this.error(e);
      }
    },
    // async returnToLogin() {
    //   // Redirect to login page
    //   this.$router.push({ name: 'Manager' })
    // },


    error(e) {
      if(e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      }
      else {
        this.errorMessage = e.message;
      }
      this.showError = true;
    },
  },

  
  components:{NavBar}
}