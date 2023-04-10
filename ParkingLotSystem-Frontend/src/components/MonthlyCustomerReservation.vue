<template>
  <div class="monthly-customer-reservation">
    <NavBar 
        :navItems="['monthly-customer', 'services-customer', 'monthly-customer-reservation']" 
        activeNav="'monthly-customer'" 
        :username="username" 
    />

    <div class ="main-body"> 

      <h1>Monthly Customer Reservation</h1>
      <div class="reservation-info">
        <label>Reservation ID: </label>
        <span>{{ reservationId }}</span>
      </div>
      <div class="reservation-info">
        <label>Start Date: </label>
        <span>{{ reservationStartDate }}</span>
      </div>
      <div class="reservation-info">
        <label>Number of Months: </label>
        <span>{{ newNbrOfMonths }}</span>
      </div>
      <div class="reservation-info">
        <label>Amount to pay: </label>
        <span>{{ amount }}</span>
      </div>
      <p class="error-msg" v-if="errorMessage">{{ errorMessage }}</p>

      <div class="button-group">
        <Payment @submit=" submitPayment()" />
        <button @click="increaseMonth" class="btn">Increase month</button>
      </div>

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

.btn {
  background-color: #007bff;
  border-color: #007bff;
  color: #fff;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
}

.btn:hover {
  background-color: #0069d9;
  border-color: #0062cc;
  color: #fff;
}

</style>