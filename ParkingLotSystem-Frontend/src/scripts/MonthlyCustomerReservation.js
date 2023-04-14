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

/** Adds months to the provided date */
function addMonths(date, months) {
  var d = date.getDate();
  date.setMonth(date.getMonth() + +months);
  if (date.getDate() != d) {
    date.setDate(0);
  }
  return date;
}

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
    async refresh() {
      try {
        let responseFee = await AXIOS.get(
          `/api/sub-with-account/get-parking-fee/${localStorage.getItem(
            "email"
          )}`
        );
        this.fee = responseFee.data;

        let responseId = await AXIOS.get(
          `/api/sub-with-account/get-id/${localStorage.getItem("email")}`
        );
        this.reservationId = responseId.data;

        let response = await AXIOS.get(
          `/api/sub-with-account/active-by-customer/${localStorage.getItem(
            "email"
          )}`
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
    increaseMonth() {
      this.newNbrOfMonths++;
      this.amount = (this.newNbrOfMonths - this.currentNbrOfMonths) * this.fee;
    },
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
