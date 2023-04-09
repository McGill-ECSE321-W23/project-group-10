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
          username: "Marco",
          creditCardNumber: ""
        }
      },
      methods: {
        onSubmit() {
          // TODO: Close page
          console.log("Submit");
        },
        onCancel() {
          // TODO: Close page
          console.log("cancel");
        }
      },
  
    
    components:{NavBar}
  }