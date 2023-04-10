import axios from 'axios'
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
        licenseNumber: "",
        selectedSpot: null,
        parkingSpots: [],
        monthlyCustomerEmail: "",
        errorMessage: "",
      };
    },
    async created() {
        let response = await AXIOS.get(`/api/parking-spot/`);
        let parkingSpotsWithIdsInRange = response.data.filter(parkingSpot => {
         
          return parkingSpot.id >= 2000 && parkingSpot.id <= 3000;
        }).map(parkingSpot => {
          // add status property to each parking spot
          
          return {
            ...parkingSpot,
            status: 'available' // default status can be 'available'
          };
        });
        // see if parking spot is reserved
        let reservationResponse = await AXIOS.get(
          `/api/reservation/`, 
          
          {
              headers: { token: "dev" } // TODO: Get token from localStorage
          }
          )
        let reservations = reservationResponse.data;
        console.log('reservations', reservations);
        parkingSpotsWithIdsInRange.forEach(parkingSpot => {
          let reservation = reservations.find(reservation => reservation.parkingSpotDto.id === parkingSpot.id);
          parkingSpot.status = reservation ? 'reserved' : 'available';
        });
        
        this.parkingSpots = parkingSpotsWithIdsInRange;
    },
    methods: {
      showSelectedSpot(parkingSpot) {
        this.selectedSpot = parkingSpot;
      },
      async createReservation() {
        console.log('create reservation with account');
        try {
          let response = await AXIOS.post(
              `/api/sub-with-account/`, 
              {},
              {
                  params: { monthlyCustomerEmail: this.monthlyCustomerEmail, parkingSpotId: this.selectedSpot.id },
                  headers: { token: "dev" } // TODO: Get token from localStorage
              }
              )
              .then(response => {
                  console.log('Created Parking Spot:', response.data);
                  // this.parkingSpots.find(parkingSpot => parkingSpot.id === selectedSpot.id).status = 'reserved';
                  location.reload();
              });
        } catch (error) {
            console.log(error)
            this.error(error)
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
    }
  }