import NavBar from "@/components/NavBar.vue";
import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "admin-settings",
  data() {
    return {
      // Opening hours variables
      openTime: null,
      closeTime: null,

      // Parking fees variables
      spotTypes: [],
      selectedSpotType: null,
      spotTypeFee: 0,

      // Available services variables
      services: [],
      selectedService: null,
      serviceDesc: "",
      serviceFee: 0,

      // Error variables
      errorMessage: "",
      showError: false
    };
  },

  created() {
    this.refresh();
  },

  methods: {
    /** Updates the description and price fields. Resets fields if no service is selected. */
    async getServiceInfo() {
      if (this.selectedService) {
        try {
          let response = await AXIOS.get(
            `/api/service/${this.selectedService}`
          );
          let service = response.data;
          this.serviceDesc = service.description;
          this.serviceFee = service.price;
        } catch (e) {
          this.error(e);
        }
      } else {
        this.serviceDesc = "";
        this.serviceFee = 0;
      }
    },
    /** Updates the selected service. Creates a service if no service is selected. */
    async saveService() {
      try {
        if (this.selectedService) {
          await AXIOS.put(
            `/api/service/${this.serviceDesc}`,
            {},
            { params: { price: this.serviceFee }, headers: { token: "dev" } }
          );
        } else {
          await AXIOS.post(
            `/api/service/${this.serviceDesc}`,
            {},
            { params: { price: this.serviceFee }, headers: { token: "dev" } }
          );
        }
      } catch (e) {
        this.error(e);
      }
      this.refresh();
    },
    /** Deletes a service. */
    async deleteService() {
      try {
        await AXIOS.delete(
          `/api/service/${this.selectedService}`,
          {},
          { headers: { token: "dev" } }
        );
      } catch (e) {
        this.error(e);
      }
      this.refresh();
    },
    /** Gets the fee for the parking spot type. */
    async getSpotTypeInfo() {
      if (this.selectedSpotType) {
        try {
          let response = await AXIOS.get(
            `/api/parking-spot-type/${this.selectedSpotType}`
          );
          let spotType = response.data;
          this.spotTypeFee = spotType.fee;
        } catch (e) {
          this.error(e);
        }
      } else {
        this.spotTypeFee = 0;
      }
    },
    /** Sets the fee of the selected parking spot type. */
    async setFee() {
      try {
        await AXIOS.put(
          `/api/parking-spot-type/${this.selectedSpotType}`,
          {},
          {
            params: { fee: this.spotTypeFee },
            headers: { token: localStorage.getItem("token") }
          }
        );
        this.refresh();
      } catch (e) {
        this.error(e);
      }
    },
    /** Sets the open time & close time of the parking lot. */
    async setHours() {
      try {
        await AXIOS.put(
          "/api/parking-lot-system/0",
          {},
          {
            params: { openTime: this.openTime, closeTime: this.closeTime },
            headers: { token: localStorage.getItem("token") }
          }
        );
      } catch (e) {
        this.error(e);
      }
    },
    /** Gets the data from the database and updates relevant fields. */
    async refresh() {
      try {
        // Get parking lot system settings
        let response = await AXIOS.get("/api/parking-lot-system/0");
        let pls = response.data;
        this.openTime = pls.openTime;
        this.closeTime = pls.closeTime;

        // Get parking fees
        response = await AXIOS.get("/api/parking-spot-type");
        this.spotTypes = response.data.map(spotType => {
          return {
            value: spotType.name,
            text: `${spotType.name}: $${spotType.fee} CAD`
          };
        });

        // Get services
        response = await AXIOS.get("/api/service");
        this.services = response.data.map(service => {
          return {
            value: service.description,
            text: `${service.description}: $${service.price} CAD`
          };
        });
      } catch (e) {
        this.error(e);
      }
    },
    /** Displays the error message. */
    error(e) {
      if (e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      } else {
        this.errorMessage = e.message;
      }
      this.showError = true;
    }
  },
  components: { NavBar }
};
