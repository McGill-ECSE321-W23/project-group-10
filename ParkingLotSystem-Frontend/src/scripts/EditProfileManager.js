import NavBar from '@/components/NavBar.vue'
import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {'Access-Control-Allow-Origin': frontendUrl}
})

export default {
  name: 'update-manager',
  data() {
    return {
      manager: {
        name: '',
        email: '',
        phoneNumber: '',
        password: ''
      },
      username: "Marco",
      navItems: [
        {text: "Dashboard", href: "#"},
        {text: "Settings", href: "#"},
        {text: "Services", href: "#"},
        {text: "Reservations", href: "#"}
      ],

    }
  },
  methods: {
    async updateManager() {
      try {
        console.log('Update Monthly Customer: ' + this.manager.name + ' ' + this.manager.email + ' '
          + this.manager.phoneNumber + ' ' + this.manager.password)
        let response = await AXIOS.put(
          `/api/manager/${this.manager.email}`,
          {},
          {
            params: {name: this.manager.name, phone: this.manager.phoneNumber, password: this.manager.password}
          }
        )
          .then(response => {
            console.log('Update Manager:', response.data);
          });

        // this.$router.push('/parking-spot-type')
      } catch (error) {
        console.log(error)
      }
    }
  },
  components: {NavBar}
}
