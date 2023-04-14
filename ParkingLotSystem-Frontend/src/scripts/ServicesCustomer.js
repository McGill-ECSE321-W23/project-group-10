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
  name: "services-customer",
  data() {
    return {
      services: [],
      selectedService: null,
      currServiceReq: null,
      paidServiceReqs: [],
      fields: [
        { key: 'service', label: 'Service' },
        { key: 'paymentDate', label: 'Payment date' }
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [5, 10, 15, { value: 100, text: "Show a lot" }],
      isBusy: false,
      username: "",
      userEmail: "",
      errorMessage: "",
      showError: false
    }
  },
  created() {
    this.username = localStorage.getItem("username");
    this.userEmail = localStorage.getItem("email");
    this.refresh();
  },
  methods: {
    /** Method to create a service request. */
    async createServiceReq() {
      console.log(`Button pressed: ${this.selectedService}`);
      try {
        let response = await AXIOS.post(
          `/api/service-req-with-account`,
          {},
          {
            params: {
              monthlyCustomerEmail: this.userEmail,
              description: this.selectedService
            },
            headers: { token: "dev" }
          }
        );
        this.currServiceReq = response.data;
      } catch(e) {
        this.error(e);
      }
    },
    /** Method to pay for the selected service request. */
    async payServiceReq() {
      try {
        let response = await AXIOS.post(
          "/api/payment-service",
          {},
          {
            params: {
              serviceRequest: this.currServiceReq.id
            },
            headers: { token: "dev" }
          }
        );
        this.currServiceReq = response.data;
      } catch(e) {
        this.error(e);
        console.log(e);
      }
      this.refresh()
    },
    async refresh() {
      // Set table to busy state and clear data
      this.isBusy = true;
      this.currServiceReq = null;
      this.paidServiceReqs = [];

      try {
        // Get service requests
        let response = await AXIOS.get(`/api/service-req-with-account/all-by-customer/${this.userEmail}`);
        let serviceReqs = response.data;

        // Get current service request and paid service requests
        for(let i in serviceReqs) {
          let serviceReq = serviceReqs[i];
          response = await AXIOS.get(`/api/payment-service/all-by-service-request/${serviceReq.id}`);
          if(response.data.length) {
            serviceReq.paymentDate = new Date(response.data[0].dateTime).toLocaleString();
            this.paidServiceReqs.push(serviceReq);
          }
          else {
            this.currServiceReq = serviceReq;
          }
        }

        if(this.currServiceReq == null) {
          // Get services
          response = await AXIOS.get("/api/service");
          this.services = response.data.map(service => {
            return {
              value: service.description,
              text: `${service.description}: $${service.price} CAD`
            }
          });
        }

        // Set the initial number of rows in the table
        this.totalRows = this.paidServiceReqs.length
      } catch(e) {
        this.error(e);
      }

      // Set table to normal state
      this.isBusy = false;
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
  components: {
    NavBar,
    Payment
  }
}
