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
            curretNbrOfMonths: '',
            error:'',
            fee:'',
            amount: '',
            newNbrOfMonths: '',
            amount:'',
        }
        
    },
    async created() {
        try {
            console.log(this.monthlyCustomerEmail)
            let response = await AXIOS.get(`/api/sub-with-account/active-by-customer/${localStorage.getItem('monthlyCustomerEmail')}`)
            console.log(response.data)
            this.reservationId = response.data.reservationId
            this.reservationStartDate = response.data.date
            this.curretNbrOfMonths = response.data.nbrMonths
            this.newNbrOfMonths = response.data.nbrMonths
            this.fee = response.data.parkingSpotDto.type.fee
            this.amount = 0
        } catch (error) {
            this.reservationId = 'invalid'
            this.reservationStartDate = 'invalid'
            this.nbrOfMonths = 'invalid'
            this.error = 'You have no active subscription'
            console.log(error)
        }
    },
    methods: {
        goToPayment(){
            // TODO: go to payment page
        },
        increaseMonth(){
            this.newNbrOfMonths++
            this.amount = (this.newNbrOfMonths - this.curretNbrOfMonths) * this.fee
            // TODO: update currentNrOfMonths and call location.reload();
        }
    }
    
}