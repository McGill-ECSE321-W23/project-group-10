<template>
  <div class="monthly-customer-reservation">
    <!--create reservation for different type of subscriptions.-->
    <NavBar activeNav="create-customer-reservation"/>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Create New Subscription:</h2>
      <input type="radio" id="sub-with-account" name="reservation-type" value="subWithAccount" v-model="reservationType">
      <label for="sub-with-account">subscription with account</label><br>
      <input type="radio" id="sub-without-account" name="reservation-type" value="subWithoutAccount" v-model="reservationType">
      <label for="sub-without-account">subscription without account</label><br>

      <label>License number:</label>
      <div v-if="reservationType === 'subWithAccount'">
        <input type="email" class="mb-2" v-model="monthlyCustomerEmail" placeholder="Enter monthly customer email">
      </div>

      <div v-if="reservationType === 'subWithoutAccount'">
        <input type="text" class="mb-2" v-model="licenseNumber" placeholder="Enter license number">
      </div>

      <label>Parking spot:</label><br>
      <b-form-select
        class="custom-width mx-auto mb-3"
        v-model="selectedSpot"
        :options="parkingSpots"
        :select-size="6">
      </b-form-select><br>

      <b-button v-on:click="createReservation">Submit</b-button>
    </div>
  </div>
</template>

<script src="@/scripts/CreateMonthlyCustomerReservationPage.js"> </script>

<style scoped>
td,
th {
  padding: 8px;
  text-align: left;
  border: 1px solid #ddd;
  box-sizing: border-box;
}

tbody:hover {
  cursor: pointer;
}
</style>
