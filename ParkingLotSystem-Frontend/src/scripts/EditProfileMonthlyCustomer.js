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
        phone: '',
        password: '',
        licenseNumber: ''
      },
      errorMessage: "",
      showError: false,
      alertVariant: "danger"
    }
  },
  /** get the monthly customer from the local database by email. */
  async created() {
    try {
      let response = await AXIOS.get(`/api/monthly-customer/${localStorage.getItem("email")}`);
      this.monthlyCustomer = response.data;
    } catch(e) {
      this.error(e);
    }
  },
  methods: {
    /** update the manager name, phone, password and license number. */
    async updateMonthlyCustomer() {
      try {
        let response = await AXIOS.put(
          `/api/monthly-customer/${this.monthlyCustomer.email}`,
          {},
          {
            params: {
              name: this.monthlyCustomer.name,
              phone: this.monthlyCustomer.phone,
              password: this.monthlyCustomer.password,
              licenseNumber: this.monthlyCustomer.licenseNumber
            }
          }
        )
        this.success("Update successful");
      } catch (error) {
        this.error(error);
      }
    },
    error(e) {
      this.alertVariant = "danger";
      this.errorMessage = "Error: ";
      if(e.hasOwnProperty("response")) {
        this.errorMessage += e.response.data.message;
      }
      else {
        this.errorMessage += e.message;
      }
      this.showError = true;
    },
    success(message) {
      this.alertVariant = "success";
      this.errorMessage = message;
      this.showError = true;
    }
  },
  components: {NavBar}
}
