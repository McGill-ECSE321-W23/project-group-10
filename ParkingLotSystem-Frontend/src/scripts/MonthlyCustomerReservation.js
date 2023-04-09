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
            nbrOfMonths: '',
            error:'',
        }
        
    },
    async created() {
        try {
            console.log(this.monthlyCustomerEmail)
            let response = await AXIOS.get(`/api/sub-with-account/active-by-customer/${localStorage.getItem('monthlyCustomerEmail')}`)
            console.log(response.data)
            this.reservationId = response.data.reservationId
            this.reservationStartDate = response.data.date
            this.nbrOfMonths = response.data.nbrMonths
        } catch (error) {
            this.reservationId = 'invalid'
            this.reservationStartDate = 'invalid'
            this.nbrOfMonths = 'invalid'
            this.error = 'You have no active subscription'
            console.log(error)
        }
    },
    methods: {
        goToPayment
    }
    
}