import axios from 'axios'
import NavBar from '@/components/NavBar.vue'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'monthly-customer-reservation',
    data() {
      return {
        //subscription without account variables
        reservationType: "subWithoutAccount",
        licenseNumber: "",
        selectedSpot: null,
        parkingSpots: [],
        monthlyCustomerEmail: "",
        errorMessage: "",
        showError: false
      };
    },
    async created() {
      this.refresh();
    },
    methods: {
      /** Create reservation with either type of subWithAccount or subWithoutAccount. */
      async createReservation() {
        if (this.reservationType === 'subWithAccount') {
          console.log('create reservation with account');
          try {
            await AXIOS.post(
              `/api/sub-with-account/`,
              {},
              {
                  params: { monthlyCustomerEmail: this.monthlyCustomerEmail, parkingSpotId: this.selectedSpot }
              }
            )
            this.licenseNumber = "";
            this.refresh();
          } catch (error) {
            this.error(error)
          }
        }
        else if (this.reservationType === 'subWithoutAccount') {
          try {
            await AXIOS.post(
              `/api/sub-without-account/`,
              {},
              {
                  params: { licenseNumber: this.licenseNumber, parkingSpotId: this.selectedSpot }
              }
            )
            this.licenseNumber = "";
            this.refresh();
          } catch (error) {
            this.error(error)
        }
        }
      },
      async refresh() {
        try {
          // Get available parking spots
          let response = await AXIOS.get(`/api/reservation/available-parking-spots`);
          let parkingSpotsWithIdsInRange = response.data.filter(parkingSpot => {
            return parkingSpot.id >= 2000 && parkingSpot.id < 4000;
          }).map(parkingSpot => {
            return {
              value: parkingSpot.id,
              text: parkingSpot.id
            };
          });
          this.parkingSpots = parkingSpotsWithIdsInRange;
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
