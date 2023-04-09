<template>
  <div class="monthly-customer-reservation">
    <h1>Monthly Customer Reservation</h1>
    <div class="reservation-info">
      <label>Reservation ID:</label>
      <span>{{ reservationId }}</span>
    </div>
    <div class="reservation-info">
      <label>Start Date:</label>
      <span>{{ reservationStartDate }}</span>
    </div>
    <div class="reservation-info">
      <label>Number of Months:</label>
      <span>{{ nbrOfMonths }}</span>
    </div>
    <p class="error-msg" v-if="error">{{ error }}</p>
  </div>
</template>
<script src="@/scripts/MonthlyCustomerReservation.js"></script>
<style scoped>
.monthly-customer-reservation {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}

.monthly-customer-reservation h1 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
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