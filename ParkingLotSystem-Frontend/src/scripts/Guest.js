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
        reservation_hour: 0,
        reservation_minute: 0,
        errorMessage: '',
        showError: false,
        options: ['For Service', 'For Reservation', 'For Subscription'],
        selectedOption: '',
        services: [],
        selectedService:'',
        selectedServicePrice:'',
        parkingSpotNumber_subscription:'',
        subscription_request: null,
        service_request:null,
        displayServicePrice:0,
        displayReservationPrice:0,
        reservationFee:0,
        displaySubscriptionPrice:0,
        subscriptionPrice:0,


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

  created() {
    this.refresh();
  },


  methods:{

    async subscription_update(){
      try {
        console.log(this.parkingSpotNumber_subscription);
        let response = await AXIOS.get(`/api/sub-without-account/${this.parkingSpotNumber_subscription}`);
        this.subscriptionPrice = response.data.price;
        console.log(response.data);
        this.displaySubscriptionPrice=1;
      } catch(e) {
        this.error(e);
      }
    },

    async service_update(){
      try {
        let response = await AXIOS.get(`/api/service/${this.selectedService}`);
        this.selectedServicePrice = response.data.price;
        this.displayServicePrice=1;
      } catch(e) {
        this.error(e);
      }
    },

    
    async reservation_update(){
      try {
        let response = await AXIOS.get(`/api/parking-spot/${this.parkingSpotNumber_reservation}`);
        this.reservationFee = (response.data.type.fee)*(60*this.reservation_hour+this.reservation_minute);
        this.displayReservationPrice=1;
      } catch(e) {
        this.error(e);
      }
    },

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
        this.$router.push('/payment');
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
        this.$router.push('/payment');
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
        this.$router.push('/payment');
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
    },

    async refresh(){
      console.log("hi");
      try{
        let response = await AXIOS.get('/api/service');
        let data_list=response.data;
        data_list.forEach(item => {
          this.services.push(item.description);
        });
      }catch(e) {
        this.error(e);
      }
    },

  },
  
  components:{NavBar}
}