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

  name: "list-of-reservation",
  data() {
    return {
      subWithAccounts: [],
      subWithoutAccounts: [],
      singleReservations: [],
      errorEvent: "",
    };

  },
  async created() {
        try {
            let withAccountRes = await AXIOS.get(`/api/sub-with-account/`)
            this.subWithAccounts = withAccountRes.data
            console.log(this.subWithAccounts)

            let withoutAccountRes = await AXIOS.get(`/api/sub-without-account/`)
            this.subWithoutAccounts = withoutAccountRes.data
            console.log(this.subWithoutAccounts)

            let singleReservationRes = await AXIOS.get(`/api/single-reservation/`)
            this.singleReservations = singleReservationRes.data
            console.log(this.singleReservations)

        } catch (error) {
            console.log(error)
        }
  },
  components:{NavBar}

};