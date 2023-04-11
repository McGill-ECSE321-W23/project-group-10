import NavBar from '@/components/NavBar.vue'
import axios from 'axios'
import PageTwo from '@/components/Hello.vue'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {

  name: "RegistrationCustomer",
  data() {
    return {
      name: '',
      email: '',
      phone: '',
      password: '',
      confirmPassword: '',
      licenseNumber: '',
      showError: false,
      errorMessage: '',
    };
  },
  methods: {
    async register() {
      // Handle registration logic here
      try{
        if (this.password.localeCompare(this.confirmPassword)!=0){
          throw new Error("password does not match confirmed password!")
        }
        let response = await AXIOS.post(
          `/api/monthly-customer/${this.email}`,
          {

          },
          {
            params: {
              name: this.name,
              phone: this.phone,
              password: this.password,
              licenseNumber: this.licenseNumber}
          }
        );
        this.$router.push("/login");
      } catch(e){
        this.error(e);
      }
    },
    async returnToWelcome() {
      // Redirect to welcome page
      this.$router.push("/")
    },

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