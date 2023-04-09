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
  name: "guest",

  data() {
    return {
        username: '',
        licenseNumber: '',
        parkingSpotNumber_reservation:'',
        reservation_hour: '',
        reservation_minute: '',
        errorMessage: '',
        showError: false,
        options: ['For Service', 'For Reservation', 'For Subscription'],
        selectedOption: '',

        services: ['Tire Changing', 'Car Cleaning', 'Oil Change'],
        selectedService:'',

        subscriptionMonths:[1,2,3,4,5,6,7,8,9,10,11,12],
        selectedMonth:'',
        parkingSpotNumber_subscription:'',

        subscription_request: null,
        service_request:null,


        hours: [
            { value: 0, label: '00' },
            { value: 1, label: '01' },
            { value: 2, label: '02' },
            { value: 3, label: '03' },
            { value: 4, label: '04' },
            { value: 5, label: '05' },
            { value: 6, label: '06' },
            { value: 7, label: '07' },
            { value: 8, label: '08' },
            { value: 9, label: '09' },
            { value: 10, label: '10' },
            { value: 11, label: '11' },
        ],

        minutes: [
            { value: 0, label: '00' },
            { value: 1, label: '01' },
            { value: 2, label: '02' },
            { value: 3, label: '03' },
            { value: 4, label: '04' },
            { value: 5, label: '05' },
            { value: 6, label: '06' },
            { value: 7, label: '07' },
            { value: 8, label: '08' },
            { value: 9, label: '09' },
            { value: 10, label: '10' },
            { value: 11, label: '11' },
            { value: 12, label: '12' },
            { value: 13, label: '13' },
            { value: 14, label: '14' },
            { value: 15, label: '15' },
            { value: 16, label: '16' },
            { value: 17, label: '17' },
            { value: 18, label: '18' },
            { value: 19, label: '19' },
            { value: 20, label: '20' },
            { value: 21, label: '21' },
            { value: 22, label: '22' },
            { value: 23, label: '23' },
            { value: 24, label: '24' },
            { value: 25, label: '25' },
            { value: 26, label: '26' },
            { value: 27, label: '27' },
            { value: 28, label: '28' },
            { value: 29, label: '29' },
            { value: 30, label: '30' },
            { value: 31, label: '31' },
            { value: 32, label: '32' },
            { value: 33, label: '33' },
            { value: 34, label: '34' },
            { value: 35, label: '35' },
            { value: 36, label: '36' },
            { value: 37, label: '37' },
            { value: 38, label: '38' },
            { value: 39, label: '39' },
            { value: 40, label: '40' },
            { value: 41, label: '41' },
            { value: 42, label: '42' },
            { value: 43, label: '43' },
            { value: 44, label: '44' },
            { value: 45, label: '45' },
            { value: 46, label: '46' },
            { value: 47, label: '47' },
            { value: 48, label: '48' },
            { value: 49, label: '49' },
            { value: 50, label: '50' },
            { value: 51, label: '51' },
            { value: 52, label: '52' },
            { value: 53, label: '53' },
            { value: 54, label: '54' },
            { value: 55, label: '55' },
            { value: 56, label: '56' },
            { value: 57, label: '57' },
            { value: 58, label: '58' },
            { value: 59, label: '59' },
        ],

    }
  },


  methods:{

    async reservation_submit(){
      var currentDate = new Date();
      console.log(currentDate);
      try {
        let response = await AXIOS.post(
          '/api/reservation',
          {},
          {
            params: {
              date: currentDate,
              parkingSpotId: this.parkingSpotNumber_reservation,
            },
            headers: { token: "dev" } 
          }
        );
        this.service_request = response.data;
      } catch(e) {
        this.error(e);
      }
    },


    async service_submit(){
      try {
        let response = await AXIOS.post(
          '/api/service-req-without-account',
          {},
          {
            params: {
              licenseNumber: this.licenseNumber,
              description: this.selectedService,
            },
            headers: { token: "dev" } 
          }
        );
        this.service_request = response.data;
      } catch(e) {
        this.error(e);
      }
    },


    async subscription_submit(){
      try {
        let response = await AXIOS.post(
          '/api/sub-without-account',
          {},
          {
            params: {
              licenseNumber: this.licenseNumber,
              parkingSpotId: this.parkingSpotNumber_subscription
            },
            headers: { token: "dev" } 
          }
        );
        this.subscription_request = response.data;
      } catch(e) {
        this.error(e);
      }
    }
  },


  
  components:{NavBar}
}