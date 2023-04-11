import NavBar from '@/components/NavBar.vue'
import Payment from '@/components/Payment.vue'
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
            reservationId: null,
            reservationStartDate: '',
            curretNbrOfMonths: '',
            errorMessage:'',
            fee:'',
            amount: '',
            newNbrOfMonths: '',
            amount:'',
            username: '',
        }
        
    },
    async created() {
        try {

            let responseFee = await AXIOS.get(`/api/sub-with-account/get-parking-fee/${localStorage.getItem('email')}`)
            this.fee = responseFee.data
            
            let responseId = await AXIOS.get(`/api/sub-with-account/get-id/${localStorage.getItem('email')}`)
            this.reservationId = responseId.data

            let response = await AXIOS.get(`/api/sub-with-account/active-by-customer/${localStorage.getItem('email')}`)
            this.reservationStartDate = response.data.date
            this.curretNbrOfMonths = response.data.nbrMonths
            this.newNbrOfMonths = response.data.nbrMonths
            this.amount = 0
        } catch (e) {
            this.reservationStartDate = 'invalid'
            this.nbrOfMonths = 'invalid'
            this.fee = 70 
            this.error(e)
        }
    },
    methods: {
        async submitPayment(){
            try {
                let paymentResponse = await AXIOS.post(`/api/payment-reservation/`, 
                {},
                {
                    params: {
                        amount: this.amount, reservationId: this.reservationId
                    },
                });
                let responseUpdate = await AXIOS.put(`/api/sub-with-account/${localStorage.getItem('monthlyCustomerEmail')}`,
                {},
                {
                    params: {
                        numberOfMonths: this.newNbrOfMonths
                    },
                });
                location.reload();

            }
            catch (e) {
                console.log(e)
                this.error(e)
            }
            
        },
        increaseMonth(){
            this.newNbrOfMonths++
            this.amount = (this.newNbrOfMonths - this.curretNbrOfMonths) * this.fee
            // TODO: update currentNrOfMonths and call location.reload();
        },
        error(e) {
            if(e.hasOwnProperty("response")) {
              this.errorMessage = e.response.data.message;
            }
            else {
              this.errorMessage = e.message;
            }
          }
    },
    components: {
        Payment,
        NavBar
    }
    
}