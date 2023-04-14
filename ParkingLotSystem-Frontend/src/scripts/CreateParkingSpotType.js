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
    name: 'parking-spot-type',
    data() {
      return {
        parkingSpotType: {
            typeName: '',
            fee: ''
        }
      }
    },
    methods: {
      /** create a parking spot type with charge fee and parking spot type name. */
        async createParkingSpotType() {
            try {
                console.log('Creating Parking Spot Type: ' + this.parkingSpotType.typeName + ' ' + this.parkingSpotType.fee)
                let response = await AXIOS.post(
                    `/api/parking-spot-type/${this.parkingSpotType.typeName}`,
                    {},
                    {
                        params: { fee: this.parkingSpotType.fee},
                        headers: { token: "dev" } // TODO: Get token from localStorage
                    }
                    )
                    .then(response => {
                        console.log('Created Parking Spot Type:', response.data);
                    });

                // this.$router.push('/parking-spot-type')
            } catch (error) {
                console.log(error)
            }
        }
    }

  }
