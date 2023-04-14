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
        phone: '',
        password: ''
      },
      errorMessage: "",
      showError: false,
      alertVariant: "danger"
    }
  },
  /** get the manager from the local database by email. */
  async created() {
    try {
      let response = await AXIOS.get(`/api/manager/${localStorage.getItem("email")}`);
      this.manager = response.data;
    } catch(e) {
      this.error(e);
    }
  },
  methods: {
    /** update the manager name, phone and password. */
    async updateManager() {
      try {
        let response = await AXIOS.put(
          `/api/manager/${this.manager.email}`,
          {},
          {
            params: {name: this.manager.name, phone: this.manager.phone, password: this.manager.password}
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
