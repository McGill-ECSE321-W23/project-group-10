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
            { value: 15, label: '15' },
            { value: 30, label: '30' },
            { value: 45, label: '45' },
        ],

    }
  },


  methods:{

    async reservation_submit(){
      try {
        let response = await AXIOS.post(
          '/api/single-reservation',
          {},
          {
            params: {
              licenseNumber: this.licenseNumber,
              parkingTime: (this.reservation_hour*60+this.reservation_minute),
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
    },

    error(e) {
      if(e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      }
      else {
        this.errorMessage = e.message;
      }
      this.showError = true;
    }
  },


  
  components:{NavBar}
}