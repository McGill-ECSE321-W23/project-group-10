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
  name: "employee",
  data() {
    return {
      username: "",
      email: "",
      errorMessage: "",
      showError: false
    }
  },
  /** get the employee from local storage by email */
  async created() {
    try {
      let response = await AXIOS.get(`/api/employee/${localStorage.getItem('email')}`)
      console.log(response.data)
      this.email = response.data.email
      this.username = response.data.name
    } catch (error) {
      this.error(error);
    }
  },
  methods: {
    error(e) {
      if(e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      }
      else {
        this.errorMessage = e.message;
      }
      this.showError = true;
    }
  },
  components:{NavBar}
}
