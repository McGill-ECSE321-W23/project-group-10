import NavBar from '@/components/NavBar.vue'
import axios, { Axios } from 'axios'
var config = require('../../config')


var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
    name: "admin-settings",
    data() {
        return{
            parkingSpotType: [],
            service: [],
            navItems: [
                { text: "ServiceOffered", href: "#"},
                { text: "Pricing and Opening Hours", href: "#"},
            
            ],
            username: "",
            errorMessage: "",
            showError: false
        }
    },
    components: {NavBar}
}