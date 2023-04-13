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
      alertVariant: "danger"
    };
  },
  methods: {
    /** Registers an employee. */
    async register() {
      try{
        if (this.password.localeCompare(this.confirmPassword)!=0){
          throw new Error("password does not match confirmed password!")
        }
        await AXIOS.post(
          `/api/employee/${this.email}`,
          {},
          {
            params: {
              name: this.name,
              phone: this.phone,
              password: this.password
            }
          }
        );
        this.success("Registration successful")
        this.name = "";
        this.email = "";
        this.phone = "";
        this.password = "";
        this.confirmPassword = "";
      } catch(e){
        this.error(e);
      }
    },
    /** Displays the error message. */
    error(e) {
      this.alertVariant = "danger";
      this.errorMessage = "Error: ";
      if(e.hasOwnProperty("response")) {
        this.errorMessage += e.response.data.message;
      }
      else {
        this.errorMessage += e.message;
      }
      this.showError = true;
    },
    /** Displays the success message. */
    success(message) {
      this.alertVariant = "success";
      this.errorMessage = message;
      this.showError = true;
    }
  },

  
  components:{NavBar}
}