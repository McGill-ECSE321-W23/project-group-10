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
        async createParkingSpotType() {
            try {
                console.log('Creating Parking Spot Type: ' + this.parkingSpotType.typeName + ' ' + this.parkingSpotType.fee)
                let response = await AXIOS.get(
                    `/api/parking-spot-type/`, 
                    // `/api/parking-spot-type/${this.typeName}`, 
                    {},{}
                    // {
                    //     params: { fee: parseFloat(this.fee)},
                    //     headers: { token: "dev" } // TODO: Get token from localStorage
                    // }
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