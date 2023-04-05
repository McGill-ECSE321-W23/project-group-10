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
  name: "services-customer",
  data() {
    return {
      navItems: [
        { text: "Subscription", href: "#"},
        { text: "Services", href: "#"}
      ],
      username: "Marco"
    }
  },
  components: {
    NavBar
  }
}