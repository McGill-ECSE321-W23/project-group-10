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
  name: 'update-monthly-customer',
  data() {
    return {
      monthlyCustomer: {
        name: '',
        email: '',
        phoneNumber: '',
        password: '',
        licenseNumber: ''
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
    async updateMonthlyCustomer() {
      try {
        console.log('Update Monthly Customer: ' + this.monthlyCustomer.name + ' ' + this.monthlyCustomer.email+ ' '
          + this.monthlyCustomer.phoneNumber + ' ' + this.monthlyCustomer.password + ' ' + this.monthlyCustomer.licenseNumber)
        let response = await AXIOS.put(
          `/api/monthly-customer/${this.monthlyCustomer.email}`,
          {},
          {
            params: {monthlyCustomerEmail: this.monthlyCustomer.email},
            headers: {token: "dev"} // TODO: Get token from localStorage
          }
        )
          .then(response => {
            console.log('Update Monthly Customer:', response.data);
          });

        // this.$router.push('/parking-spot-type')
      } catch (error) {
        console.log(error)
      }
    }
  },
  components: {NavBar}
}
