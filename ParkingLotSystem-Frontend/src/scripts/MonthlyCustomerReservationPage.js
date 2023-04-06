import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    data() {
      return {
          parkingSpots: [],      
      };
    },
    created() {
      this.parkingSpots= [ //hardcoded for now
  
          {
              id: 1,
              parkingSpotStatus: "Available",
          },
          {
              id: 2,
              parkingSpotStatus: "Available",
          },
          {
              id: 3,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 4,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 5,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 6,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 7,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 8,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 9,
              parkingSpotStatus: "Reserved",
          },
          {
              id: 10,
              parkingSpotStatus: "Reserved",
          },
  
      ]
      
    }
  }