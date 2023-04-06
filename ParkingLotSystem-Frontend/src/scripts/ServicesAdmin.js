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
  name: "services-admin",
  data() {
    return {
      serviceRequests: [],
      fields: [
        { key: 'service', label: 'Service', sortDirection: 'desc' },
        { key: 'licenseNumber', label: 'License number', class: 'text-center' },
        { key: 'paymentDate', label: 'Payment date' },
        { key: 'assignment', label: 'Assignment' }
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [5, 10, 15, { value: 100, text: "Show a lot" }],
      navItems: [
        { text: "Dashboard", href: "#"},
        { text: "Settings", href: "#"},
        { text: "Services", href: "#"},
        { text: "Reservations", href: "#"}
      ],
      username: "Marco",
      errorMessage: "",
      showError: false,
      isBusy: false
    }
  },
  async created() {
    // Set table to busy state
    this.isBusy = true;

    try {
      // Get service requests without account
      let response = await AXIOS.get("/api/service-req-without-account");
      this.serviceRequests = response.data;

      // Get service requests with account
      response = await AXIOS.get("/api/service-req-with-account");
      let serviceReqsWithAccount = response.data;
      serviceReqsWithAccount.forEach(serviceReq => {
        serviceReq.licenseNumber = serviceReq.monthlyCustomerDto.licenseNumber;
      });
      this.serviceRequests = [...this.serviceRequests, ...serviceReqsWithAccount];

      // Set row variants and get service payments if applicable
      for(let i in this.serviceRequests) {
        let serviceReq = this.serviceRequests[i];
        serviceReq._rowVariant = !serviceReq.isAssigned ? 'info':'';
        response = await AXIOS.get(`/api/payment-service/all-by-service-request/${serviceReq.id}`);
        if(!response.data.length) continue;
        serviceReq.paymentDate = response.data[0].dateTime.split("T")[0];
      }

      // Set the initial number of rows in the table
      this.totalRows = this.serviceRequests.length
    } catch(e) {
      this.error(e);
    }

    // Set table to normal state
    this.isBusy = false;
  },
  methods: {
    async assign(serviceReq) {
      console.log("Assign button pressed for: ", serviceReq.id);
      try {
        let serviceReqType = 
          serviceReq.hasOwnProperty("monthlyCustomerDto") ? 
          "service-req-with-account" : 
          "service-req-without-account";

        let response = await AXIOS.put(
          `/api/${serviceReqType}/${serviceReq.id}`,
          {},
          {
            params: { isAssigned: true },
            headers: { token: "dev" } // TODO: Get token from localStorage
          }
          );
        serviceReq.isAssigned = response.data.isAssigned;
        serviceReq._rowVariant = !serviceReq.isAssigned ? 'info':'';
      } catch(e) {
        this.error(e);
      }
    },
    createTooltip(serviceReq) {
      let customer = serviceReq.monthlyCustomerDto;
      return `Email: ${customer.email}`;
    },
    refresh() {
      this.$refs.servicesTable.refresh();
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
  components: {NavBar}
}