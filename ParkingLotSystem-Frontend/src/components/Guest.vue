<template>
  <div class="guest">
    <b-alert v-model="showError" :variant="alertVariant" dismissible>{{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Welcome, Customer !</h2>
      <label class="body-label">
        License Number:
        <input type="text" v-model="licenseNumber" style="width: 100px;height:28px" />
      </label>
    </div>

    <div class="radio-button-container">
      <label v-for="(option, index) in options" :key="index" class="body-label">
        <input type="radio" :value="option" v-model="selectedOption">
        {{ option }}
      </label>
    </div>

    <div v-if="selectedOption == 'For Service'">
      <label class="body-label" for="dropdown_service">Service Type:</label>
      <select id="dropdown_service" v-model="selectedService" style="width: 200x;height:28px">
        <option value="">--Service--</option>
        <option v-for="service in services" :key="service" :value="service">{{ service }}</option>
      </select>
      <div class="row">
        <label class="body-label">Price:</label>
        <label v-if="displayServicePrice == 1 && selectedService != ''" class="body-label">${{ selectedServicePrice }}</label>
      </div>
      <div>
        <b-button @click="service_update" class="submit-button">Calculate Price</b-button>
        <b-button @click="service_submit" class="submit-button">Proceed Service</b-button>
      </div>
    </div>

    <div v-if="selectedOption == 'For Reservation'">
      <label class="body-label">
        Parking Spot Number:
        <input type="text" v-model="parkingSpotNumber_reservation" style="width: 80px;height:28px">
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
      <div class="row">
        <label class="body-label">Price:</label>
        <label v-if="displayReservationPrice == 1" class="body-label">${{ reservationFee }}</label>
      </div>
      <div>
        <b-button @click="reservation_update" class="submit-button mb-2">Calculate Price</b-button>
        <Payment @submit="reservation_submit" :disabled="false" />
      </div>
    </div>

    <div v-if="selectedOption == 'For Subscription'">
      <b-button class="mb-3" @click="refreshSubInfo()">View and pay subscription</b-button>

      <div class="reservation-info">
        <label>Reservation ID:&nbsp;</label>
        <span>{{ reservationId }}</span>
      </div>
      <div class="reservation-info">
        <label>Start Date:&nbsp;</label>
        <span>{{ reservationStartDate }}</span>
      </div>
      <div class="reservation-info">
        <label>End Date:&nbsp;</label>
        <span>{{ reservationEndDate }}</span>
      </div>
      <div class="reservation-info">
        <label>Number of Months:&nbsp;</label>
        <span>{{ newNbrOfMonths }}</span>
      </div>
      <div class="reservation-info">
        <label>Amount to pay:&nbsp;</label>
        <span>{{ amount }}</span>
      </div>

      <b-button class="mb-2" @click="increaseMonth" :disabled="!reservationId">Increase month</b-button>
      <Payment @submit="submitSubscriptionPayment()" :disabled="!reservationId" />
    </div>

  </div>
</template>

<script src="@/scripts/Guest.js"></script>

<style scoped>
.row {
  display: flex;
  align-items: center;
  justify-content: center;
}

.body-label {
  font-size: 20px;
  margin-right: 10px;
  margin-left: 10px;
  margin-top: 5px;
}

.submit-button {
  margin-top: 10px;
  width: 200px;
}

.radio-button-container {
  margin-bottom: 20px;
}</style>