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
            openTime: null,
            closeTime: null,
            spotTypes: [],
            selectedSpotType: null,
            spotTypeFee: 0,
            services: [],
            selectedService: null,
            errorMessage: "",
            showError: false,
            serviceDesc: "",
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
                    `/api/service/${this.serviceDesc}`,
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
        async setFee() {
            try {
                let res = await AXIOS.put(
                    `/api/parking-spot-type/${this.selectedSpotType}`,
                    {},
                    {
                        params: { fee: this.spotTypeFee },
                        headers: { token: localStorage.getItem("token")}
                    }
                );
                this.refresh();
            } catch(e) {
                this.error(e);
            }
        },
        async setHours() {
            try {
                await AXIOS.put(
                    "/api/parking-lot-system/0",
                    {},
                    {
                        params: { openTime: this.openTime, closeTime: this.closeTime },
                        headers: { token: localStorage.getItem("token")}
                    }
                );
            } catch(e) {
                this.error(e);
            }
        },

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
                    }
                });

                // Get services
                response = await AXIOS.get("/api/service");
                this.services = response.data.map(service => {
                    return {
                    value: service.description,
                    text: `${service.description}: $${service.price} CAD`
                    }
                });
            } catch(e) {
                this.error(e);
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
          }
    },
    components: {NavBar}

 }