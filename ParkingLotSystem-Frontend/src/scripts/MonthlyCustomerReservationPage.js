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
    name: "monthly-customer-reservation",
    data() {
        return {
            reservationId: '',
            reservationStartDate: '',
            nrOfMonths: '',
        }
        
    },
    async created() {
        try {
            console.log(this.monthlyCustomerEmail)
            let response = await AXIOS.get(`/api/sub-with-account/active-by-customer/${monthlyCustomerEmail}`)
        } catch (error) {
            console.log(error)
        }
    },
    computed: {
        monthlyCustomerEmail() {
          return JSON.parse(localStorage.getItem('monthlyCustomerEmail'))
        }
    }

}