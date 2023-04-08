import NavBar from '@/components/NavBar.vue'
import axios from 'axios'

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
            navItems: [
                { text: "Dashboard", href: "#"},
                { text: "Settings", href: "#"},
                { text: "Services", href: "#"},
                { text: "Reservations", href: "#"}

            ],
            username: "Mike",
            errorMessage: "",
            showError: false
        }
    },
    created() {
        this.refresh();
    },
    methods: {
        
    }
 }