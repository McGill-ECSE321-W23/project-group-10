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
  name: "guest",

  data() {
    return {
      // General variables
      licenseNumber: '',
      options: ['For Service', 'For Reservation', 'For Subscription'],
      selectedOption: '',

      // Service variables
      services: [],
      selectedService:'',
      selectedServicePrice:'',
      service_request:null,
      displayServicePrice:0,
      displayReservationPrice:0,

      // Reservation variables
      parkingSpotNumber_reservation:'',
      reservation_hour: 0,
      reservation_minute: 0,
      reservationFee:0,

      // Subscription variables
      reservationId: null,
      reservationStartDate: "",
      currentNbrOfMonths: "",
      fee: "",
      amount: "",
      newNbrOfMonths: "",

      // Other
      errorMessage: '',
      alertVariant: 'danger',
      showError: false,
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

  computed: {
    reservationEndDate() {
      if (!this.reservationStartDate) return "";
      let date = new Date(this.reservationStartDate);
      let d = date.getDate();
      date.setMonth(date.getMonth() + +this.newNbrOfMonths);
      if (date.getDate() != d) {
        date.setDate(0);
      }
      return date.toLocaleDateString();
    }
  },

  methods:{
    /** Gets the service info. */
    async service_update(){
      try {
        let response = await AXIOS.get(`/api/service/${this.selectedService}`);
        this.selectedServicePrice = response.data.price;
        this.displayServicePrice=1;
      } catch(e) {
        this.error(e);
      }
    },

    /** Gets the parking spot info. */
    async reservation_update(){
      try {
        let response = await AXIOS.get(`/api/parking-spot/${this.parkingSpotNumber_reservation}`);
        this.reservationFee = (response.data.type.fee)*(4*this.reservation_hour+this.reservation_minute/15);
        this.displayReservationPrice=1;
      } catch(e) {
        this.error(e);
      }
    },

    /** Creates the payment and single reservation. */
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
            }
          }
        );
        this.service_request = response.data;
        this.success("Parking spot reserved successfully");
      } catch(e) {
        this.error(e);
      }
    },

    /** Creates the payment and the service. */
    async service_submit(){
      try {
        let response = await AXIOS.post(
          '/api/service-req-without-account',
          {},
          {
            params: {
              licenseNumber: this.licenseNumber,
              description: this.selectedService,
            }
          }
        );
        this.service_request = response.data;
        this.success("Service request created successfully");
      } catch(e) {
        this.error(e);
      }
    },
    /** Gets the data for the subscription. */
    async refreshSubInfo() {
      try {
        // Get the active subscription
        let response = await AXIOS.get(
          `/api/sub-without-account/active-by-license-number/${this.licenseNumber}`
        );
        let sub = response.data;
        this.reservationId = sub.reservationId;
        this.fee = sub.parkingSpotDto.type.fee;
        this.reservationStartDate = new Date(
          sub.date
        ).toLocaleDateString();
        this.currentNbrOfMonths = sub.nbrMonths;
        this.newNbrOfMonths = sub.nbrMonths;
        this.amount = 0;
      } catch (e) {
        this.reservationId = null;
        this.reservationStartDate = "Invalid Date";
        this.nbrOfMonths = "invalid";
        this.currentNbrOfMonths = 0;
        this.newNbrOfMonths = 0;
        this.fee = 70;
        this.error(e);
      }
    },

    /** Submits the payment and updates the subscription. */
    async submitSubscriptionPayment() {
      try {
        await AXIOS.post(
          `/api/payment-reservation/`,
          {},
          {
            params: {
              amount: this.amount,
              reservationId: this.reservationId
            }
          }
        );
        let res = await AXIOS.put(
          `/api/sub-without-account/${this.licenseNumber}`,
          {},
          {
            params: {
              numberOfMonths: this.newNbrOfMonths
            }
          }
        );
        console.log(res.data);
        this.refreshSubInfo();
      } catch (e) {
        this.error(e);
      }
    },

    /** Increases the number of months of the subscription. */
    increaseMonth() {
      this.newNbrOfMonths++;
      this.amount = (this.newNbrOfMonths - this.currentNbrOfMonths) * this.fee;
    },

    /** Displays the error message */
    error(e) {
      this.alertVariant = "danger";
      this.errorMessage = "Error: ";
      if(e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      }
      else {
        this.errorMessage = e.message;
      }
      this.showError = true;
    },

    /** Displays the success message. */
    success(message) {
      this.alertVariant = "success";
      this.errorMessage = message;
      this.showError = true;
    },

    /** Gets the list of services */
    async refresh(){
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

  components:{NavBar, Payment}
}
