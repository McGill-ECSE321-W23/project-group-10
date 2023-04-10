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
    }
  },
  async created() {
    try {
      let response = await AXIOS.get(`/api/employee/${localStorage.getItem('employeeEmail')}`)
      console.log(response.data)
      this.email = response.data.email
      this.username = response.data.name
    } catch (error) {
      console.log(error)
    }
  },

  
  components:{NavBar}
}