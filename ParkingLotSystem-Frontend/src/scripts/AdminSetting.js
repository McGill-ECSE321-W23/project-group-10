import NavBar from '@/components/NavBar.vue'
import axios, { Axios } from 'axios'
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
            service: [],
            navItems: [
                "dashboard","settings","services-admin","reservations-admin"
            ],
            username: "Mike",
            errorMessage: "",
            showError: false
        }
    },
    
    methods: {
        addService: function(){
            try{
                var serviceName = document.getElementById("typeName");
                var serviceFee = document.getElementById("serviceFee");

                let service = AXIOS.post(`/api/service/${serviceName}`, {serviceFee});
            } catch(e){
                this.error(e);
            }
        },
        setFee: function() {
            try{
                var type = document.getElementById("type");
                var fee = document.getElementById("parkingFee");

                let parkingFee = AXIOS.put(`/api/parking-spot-type/${type}/`, {fee});
            }catch(e){
                this.error(e);
            }
        },

        setHours: function() {
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