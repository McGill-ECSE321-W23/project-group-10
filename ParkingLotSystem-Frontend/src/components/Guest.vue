<template>
    <div class="guest">
        <NavBar 
            :navItems="['dashboard-guest', 'reservations-view']" 
            activeNav="dashboard-guest" 
            :username="username" 
        />
        <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
        <div class="content">
            <h2>Welcome, Customer !</h2>
                <label class="body-label">
                    License Number:
                    <input type="text" v-model="licenseNumber" style="width: 100px;height:28px"/>
                </label>
        </div>

        <div class="radio-button-container">
            <label v-for="(option, index) in options" :key="index"  class="body-label">
                <input type="radio" :value="option" v-model="selectedOption">
                {{ option }}
            </label>
        </div>

        <div v-if="selectedOption=='For Service'">
            <h2>Hi</h2>
        </div>

        <div v-if="selectedOption=='For Reservation'" >
            <label class="body-label">
                Parking Spot Number:
                <input type="text" v-model="parkingSpotNumber" style="width: 100px;height:28px">
            </label>
            <div class="row">
                <label class="body-label" for="dropdown_hour1">Parking Timespan:</label>
                <select id="dropdown_hour1" v-model="reservation_hour" style="width: 80x;height:28px">
                    <option value="">-Hour-</option>
                    <option v-for="hour in hours" :key="hour.value" :value="hour.value">{{ hour.label }}</option>
                </select>
                <label class="body-label"> : </label>
                <select id="dropdown_minute1" v-model="reservation_minute" style="width: 80x;height:28px">
                    <option value="">-Min-</option>
                    <option v-for="minute in minutes" :key="minute.value" :value="minute.value">{{ minute.label }}</option>
                </select>
            </div>
                <div>
                    <button @click="reservation_submit" class="submit-button">Proceed Reservation</button>
                </div>
            </div>

        <div v-if="selectedOption=='For Subscription'">
            <h2>Hi</h2>
        </div>

    </div>
</template>

<script src="@/scripts/Guest.js"></script>

<style scoped>
  .row {
    display: flex;
    align-items: center;
    justify-content:center;
  }
  .body-label{
    font-size: 20px;
    margin-right: 10px;
    margin-left: 10px;
    margin-top: 5px;
  }
  .input-box{
    width: 10px;
    height: 10px;
  }
  .submit-button{
    margin-top: 10px;
    width: 300px;
  }
  .radio-button-container{
    margin-bottom: 20px;
  }
</style>