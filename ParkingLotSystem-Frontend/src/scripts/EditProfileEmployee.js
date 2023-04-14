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
        phone: '',
        password: ''
      },
      showError: false,
      errorMessage: "",
      alertVariant: "danger"
    }
  },
  /** get the employee from the local database by email. */
  async created() {
    try {
      let response = await AXIOS.get(`/api/employee/${localStorage.getItem("email")}`);
      this.employee = response.data;
    } catch(e) {
      this.error(e);
    }
  },
  methods: {
    /** update the employee name, phone and password. */
    async updateEmployee() {
      try {
        let response = await AXIOS.put(
          `/api/employee/${this.employee.email}`,
          {},
          {
            params: {name: this.employee.name, phone: this.employee.phone, password: this.employee.password}
          }
        )
        this.success("Update successful");
      } catch (error) {
        this.error(error);
      }
    },
    error(e) {
      this.alertVariant = "danger";
      if(e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      }
      else {
        this.errorMessage = e.message;
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
