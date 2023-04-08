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
    name: "payment",
    data() {
        return {
          amount: '',
          currentDate: new Date().toLocaleDateString(),
          paymentId: '', // Generate a unique payment ID when the payment is made
          username: "Marco",
        };
      },
      methods: {
        pay() {
          // Handle payment logic here
        },
        returnToMenu() {
          // Redirect to the main menu
        },
      },
  
    
    components:{NavBar}
  }