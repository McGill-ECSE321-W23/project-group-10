
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'parking-spot',
    data() {
      return {
        parkingSpot: {
            id: '',
            type: ''
        }

      }
    },
    methods: {
      /** Create a parking spot with spot id and parking spot type. */
        async createParkingSpot() {
            try {
                console.log('Creating Parking Spot: ' + this.parkingSpot.id + ' ' + this.parkingSpot.type)
                let response = await AXIOS.post(
                    `/api/parking-spot/${this.parkingSpot.id}`,
                    {},
                    {
                        params: { parkingSpotTypeName: this.parkingSpot.type},
                        headers: { token: "dev" }
                    }
                    )
                    .then(response => {
                        console.log('Created Parking Spot:', response.data);
                    });

            } catch (error) {
                console.log(error)
            }
        }
    }
}
