import NavBar from "@/components/NavBar.vue";
import Payment from "@/components/Payment.vue";
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
  name: "monthly-customer-reservation",
  data() {
    return {
      reservationId: null,
      reservationStartDate: "",
      currentNbrOfMonths: "",
      errorMessage: "",
      fee: "",
      amount: "",
      newNbrOfMonths: "",
      amount: "",
      username: ""
    };
  },
  computed: {
    reservationEndDate() {
      if (!this.reservationStartDate) return "";
      let date = new Date(this.reservationStartDate);
      let d = date.getDate();
      date.setMonth(date.getMonth() + +this.newNbrOfMonths);
      if (date.getDate() != d) {
        date.setDate(0);
      }
      return date.toLocaleDateString();
    }
  },
  async created() {
    this.refresh();
  },
  methods: {
    /** Submits the payment and updates the subscription. */
    async submitPayment() {
      try {
        await AXIOS.post(
          `/api/payment-reservation/`,
          {},
          {
            params: {
              amount: this.amount,
              reservationId: this.reservationId
            }
          }
        );
        await AXIOS.put(
          `/api/sub-with-account/${localStorage.getItem("email")}`,
          {},
          {
            params: {
              numberOfMonths: this.newNbrOfMonths
            }
          }
        );
        this.refresh();
      } catch (e) {
        this.error(e);
      }
    },
    /** Gets the data from the database and updates relevant fields. */
    async refresh() {
      try {
        // Get the fee of the subscription
        let responseFee = await AXIOS.get(
          `/api/sub-with-account/get-parking-fee/${localStorage.getItem(
            "email"
          )}`
        );
        this.fee = responseFee.data;

        // Get the ID of the active subscription
        let responseId = await AXIOS.get(
          `/api/sub-with-account/get-id/${localStorage.getItem("email")}`
        );
        this.reservationId = responseId.data;

        // Get the active subscription
        let response = await AXIOS.get(
          `/api/sub-with-account/active-by-customer/${localStorage.getItem("email")}`
        );
        this.reservationStartDate = new Date(
          response.data.date
        ).toLocaleDateString();
        this.currentNbrOfMonths = response.data.nbrMonths;
        this.newNbrOfMonths = response.data.nbrMonths;
        this.amount = 0;
      } catch (e) {
        this.reservationStartDate = "invalid";
        this.nbrOfMonths = "invalid";
        this.fee = 70;
        this.error(e);
      }
    },
    /** Increases the number of months of the subscription. */
    increaseMonth() {
      this.newNbrOfMonths++;
      this.amount = (this.newNbrOfMonths - this.currentNbrOfMonths) * this.fee;
    },
    /** Displays the error message. */
    error(e) {
      if (e.hasOwnProperty("response")) {
        this.errorMessage = e.response.data.message;
      } else {
        this.errorMessage = e.message;
      }
    }
  },
  components: {
    Payment,
    NavBar
  }
};
