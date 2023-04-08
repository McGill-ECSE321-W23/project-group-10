<template>
  <div>

    <div class="registration">
        <NavBar 
            :navItems="[]" 
            activeNav="payment" 
            :username="username" 
        />
    </div>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>

    <!-- <div class="title-bar">
      <h1>Parking Lot System---Payment</h1>
    </div> -->

    <div class="form-container">
      <form @submit.prevent="pay">
        <div class="input-group">
          <label for="amount">Amount: $</label>
          <input type="number" id="amount" step="0.01" min="0" v-model="amount" required />
        </div>

        <div class="output-group">
          <label>Current Date:</label>
          <output>{{ currentDate }}</output>
        </div>

        <div class="output-group">
          <label>Payment ID:</label>
          <output>{{ paymentId }}</output>
        </div>

        <div class="button-group">
          <button type="submit">Pay</button>
          <button type="button" @click="returnToMenu">Return</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script src="@/scripts/Payment.js"></script>

<style scoped>
.title-bar {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  background-color: #f3f3f3;
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.input-group,
.output-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.label {
  margin-bottom: 0.5rem;
}

.button-group {
  display: flex;
  justify-content: space-between;
}
</style>
