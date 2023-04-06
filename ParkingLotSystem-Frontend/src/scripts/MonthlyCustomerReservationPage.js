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
          parkingSpots: [],      
      };
    },
    async created() {
        let response = await AXIOS.get(`/api/parking-spot/`);
        let parkingSpotsWithIdsInRange = response.data.filter(parkingSpot => {
          return parkingSpot.id >= 0 && parkingSpot.id <= 19;
        });
        this.parkingSpots = parkingSpotsWithIdsInRange;
        // this.parkingSpots = response.data;
    }
  }