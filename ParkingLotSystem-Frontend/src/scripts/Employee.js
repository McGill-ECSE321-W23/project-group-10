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
      navItems: [
        { text: "Dashboard", href: "#"},
        { text: "Services", href: "#"},
        { text: "Reservations", href: "#"}
      ],
      username: "Weiheng",
      email: "weiheng.xiao@mail.mcgill.ca",
      phone: "4387228120",

      
      errorMessage: "",
      showError: false,
    }
  },

  
  components:{NavBar}
}