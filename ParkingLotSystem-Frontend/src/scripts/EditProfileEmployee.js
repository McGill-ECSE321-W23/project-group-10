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
  name: 'update-employee',
  data() {
    return {
      employee: {
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
      showError: false,
      errorMessage: ""
    }
  },
  methods: {
    async updateEmployee() {
      try {
        console.log('Update Employee: ' + this.employee.name + ' ' + this.employee.email+ ' '
          + this.employee.phoneNumber + ' ' + this.employee.password)
        let response = await AXIOS.put(
          `/api/employee/${this.employee.email}`,
          {},
          {
            params: {name: this.employee.name, phone: this.employee.phoneNumber, password: this.employee.password}
          }
        )
          .then(response => {
            console.log('Update Employee:', response.data);
          });

        // this.$router.push('/parking-spot-type')
      } catch (error) {
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
    },
  },
  components: {NavBar}
}
