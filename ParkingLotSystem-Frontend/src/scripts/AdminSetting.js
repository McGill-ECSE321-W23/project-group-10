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
    name: "admin-settings",
    data() {
        return{
            parkingSpotType: [],
            services: [],
            selectedService: null,
            username: "Mike",
            errorMessage: "",
            showError: false,
            typeName: "",
            serviceFee: 0
        }
    },

    created() {
        this.refresh();
    },
    
    methods: {
        async addService(){
            try{
                await AXIOS.post(
                    `/api/service/${this.typeName}`,
                    {},
                    {params: {price: this.serviceFee}, headers: {token: "dev"}}
                );
            } catch(e){
                this.error(e);
            }
            this.refresh();
        },
        async deleteService() {
            try{
                await AXIOS.delete(
                    `/api/service/${this.selectedService}`,
                    {},
                    {headers: {token: "dev"}}
                );
            } catch(e){
                this.error(e);
            }
            this.refresh();
        },
        setFee: function() {
            return; // TODO: FIX THIS
            try{
                var type = document.getElementById("type");
                var fee = document.getElementById("parkingFee");

                let parkingFee = AXIOS.put(`/api/parking-spot-type/${type}/`, {fee});
            }catch(e){
                this.error(e);
            }
        },

        setHours: function() {
            return; // TODO: FIX THIS
            try{
                var startHour = document.getElementById("startHour");
                var closeHour = document.getElementById("closeHour");
                var startTime = document.getElementById("startTime");
                var closeTime = document.getElementById("closeTime");
                if (startTime == "PM"){
                    startHour+=12;
                }
                if (closeTime == "PM"){
                    closeHour+=12;
                }

                let openingHours = AXIOS.put(`/api/parking-lot-system/${id}/`, {startHour, closeHour});
            }catch(e){
                this.error(e);
            }
        },

        async refresh() {
            let response = await AXIOS.get("/api/service");
            this.services = response.data.map(service => {
                return {
                value: service.description,
                text: `${service.description}: $${service.price} CAD`
                }
            });
        },

        error(e) {
            if(e.hasOwnProperty("response")) {
              this.errorMessage = e.response.data.message;
            }
            else {
              this.errorMessage = e.message;
            }
            this.showError = true;
          }
    },
    components: {NavBar}

 }