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
  name: "monthly-customer",
  data() {
    return {
      username: "Weiheng",
      email: "weiheng.xiao@mail.mcgill.ca",
      errorMessage: "",
      showError: false,
    }
  },

  
  components:{NavBar}
}