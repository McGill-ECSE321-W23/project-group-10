<template>
  <div class="monthly-customer-reservation">
    <!--Page to make subscriptions as a monthly customer. -->
    <NavBar activeNav="subscription"/>

    <div class ="main-body">

      <h1>Monthly Customer Reservation</h1>
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
      <p class="error-msg" v-if="errorMessage">{{ errorMessage }}</p>

      <b-button class="mb-2" @click="increaseMonth" :disabled="!reservationId">Increase month</b-button>
      <Payment @submit=" submitPayment()" :disabled="!reservationId"/>

    </div>

  </div>
</template>
<script src="@/scripts/MonthlyCustomerReservation.js"></script>
<style scoped>
.main-body {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}

.monthly-customer-reservation h1 {
  font-size: 32px;
  margin-bottom: 20px;
  margin-top: 30px;
}

.reservation-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.reservation-info label {
  font-weight: bold;
}

.error-msg {
  color: red;
  font-size: 18px;
  font-weight: bold;
  margin-top: 20px;
}

</style>
