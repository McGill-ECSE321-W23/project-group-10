import NavBar from '@/components/NavBar.vue'
import VueApexCharts from 'vue-apexcharts'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: "manager",
  data() {
    return {
      // General + error variables
      username: "Marco", // TODO: Implement authentication
      errorMessage: "",
      showError: false,

      // Service requests table & Revenue table variables
      serviceRequests: [],
      serviceReqsFields: [
        { key: 'service', label: 'Service' },
        { key: 'licenseNumber', label: 'License number', class: 'text-center' }
      ],
      payments: [],
      paymentsFields: [
        { key: 'dateTime', label: 'Payment date', sortDirection: 'desc'},
        { key: 'type', label: 'Type', class: 'text-center'},
        { key: 'amount', label: 'Amount' }
      ],
      currentPage: 1,
      perPage: 10,
      isBusy: false,

      // Overall capacity chart variables
      capacityChartOptions: {
        labels: ["Reserved spots"],
        plotOptions: {
          radialBar: {
            dataLabels: {
              value: {
                formatter: function(val) { return val + " / 100"; }
              }
            }
          }
        }
      },
      capacityChartSeries: [25],

      // Floor capacity chart variables & settings
      floorCapacityChartOptions: {
        plotOptions: {
          bar: {
            horizontal: true
          }
        },
        xaxis: {
          categories: ["Floor 5", "Floor 4", "Floor 3", "Floor 2", "Ground"],
          tickAmount: 1,
          labels: {
            formatter: function(val) {
              return val.toFixed(0);
            }
          }
        },
        tooltip: {
          y: {
              formatter: function(val) {
                  return val.toFixed(0);
              }
          }
        }
      },
      floorCapacityChartSeries: [{
        name: 'Reserved spots',
        data: [0, 10, 20, 30, 40]
      }],
    }
  },
  created() {
    this.refresh();
  },
  methods: {
    async refresh() {
      // Set table to busy state
      this.isBusy = true;

      try {

        // Get reserved parking spots
        let response = await AXIOS.get("/api/reservation/reserved-parking-spots");
        let spots = response.data;
        let nbrSpotsReserved = spots.length;
        let nbrSpotsG = spots.filter(spot => (spot.id < 2000)).length;
        let nbrSpots2 = spots.filter(spot => (spot.id >= 2000 && spot.id < 3000)).length;
        let nbrSpots3 = spots.filter(spot => (spot.id >= 3000 && spot.id < 4000)).length;
        let nbrSpots4 = spots.filter(spot => (spot.id >= 4000 && spot.id < 5000)).length;
        let nbrSpots5 = spots.filter(spot => (spot.id >= 5000 && spot.id < 6000)).length;

        // Get all parking spots (for computation purposes)
        response = await AXIOS.get("/api/parking-spot");
        let nbrSpotsTotal = response.data.length;

        // Update chart data
        this.floorCapacityChartSeries = [{
          ...this.floorCapacityChartSeries[0],
          data: [nbrSpots5, nbrSpots4, nbrSpots3, nbrSpots2, nbrSpotsG]
        }]
        this.capacityChartSeries = [Math.floor((nbrSpotsReserved / nbrSpotsTotal) * 100)];
        this.capacityChartOptions = {
          ...this.capacityChartOptions, 
          plotOptions: {
            radialBar: {
              dataLabels: {
                value: {
                  formatter: function(val) { return nbrSpotsReserved + " / " + nbrSpotsTotal; }
                }
              }
            }
        }}

        // Get service requests without account
        response = await AXIOS.get("/api/service-req-without-account");
        this.serviceRequests = response.data;

        // Get service requests with account
        response = await AXIOS.get("/api/service-req-with-account");
        let serviceReqsWithAccount = response.data;
        serviceReqsWithAccount.forEach(serviceReq => {
          serviceReq.licenseNumber = serviceReq.monthlyCustomerDto.licenseNumber;
        });
        this.serviceRequests = [...this.serviceRequests, ...serviceReqsWithAccount];
        this.serviceRequests = this.serviceRequests.filter(serviceReq => !serviceReq.isAssigned);

        // Get reservation payments
        response = await AXIOS.get("/api/payment-reservation", { headers: { token: "dev" } });
        let pReservations = response.data;
        pReservations.forEach(payment => payment.type = "Reservation");

        // Get service payments
        response = await AXIOS.get("/api/payment-service", { headers: { token: "dev" } });
        let pServices = response.data;
        pServices.forEach(payment => payment.type = "Service");

        this.payments = [...pReservations, ...pServices]
        this.payments.forEach(
          payment => payment.dateTime = new Date(payment.dateTime).toLocaleString()
        );

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
  components: {NavBar, VueApexCharts}
}