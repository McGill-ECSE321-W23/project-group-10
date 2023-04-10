import NavBar from '@/components/NavBar.vue'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: "payment",
    props: {
      disabled: Boolean
    },
    data() {
        return {
          creditCardNbr: '',
          valueState: null
        }
    },
    methods: {
      checkFormValidity() {
        const valid = this.$refs.form.checkValidity();
        this.valueState = valid;
        return valid;
      },
      resetModal() {
        this.creditCardNbr = '';
        this.valueState = null;
      },
      handleOk(bvModalEvent) {
        // Prevent modal from closing
        bvModalEvent.preventDefault();
        // Trigger submit handler
        this.handleSubmit();
      },
      handleSubmit() {
        // Exit when the form isn't valid
        if (!this.checkFormValidity()) {
          return;
        }
        this.$emit('submit');
        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('paymentModal');
        })
      }
    },
    components:{NavBar}
  }