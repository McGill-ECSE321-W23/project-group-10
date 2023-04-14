<template>
  <div class="RegistrationCustomer">
    <!--Page to register as a customer. -->
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <!-- <div class="title-bar">
      <h1>Parking Lot System---Registration</h1>
    </div> -->
    <h2>Register</h2>
    <div class="form-container">
      <form @submit.prevent="register">
        <div class="input-group">
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="name" required />
        </div>

        <div class="input-group">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" required />
        </div>

        <div class="input-group">
          <label for="phone">Phone:</label>
          <input type="tel" id="phone" v-model="phone" required />
        </div>

        <div class="input-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required />
        </div>

        <div class="input-group">
          <label for="confirm-password">Confirm Password:</label>
          <input type="password" id="confirm-password" v-model="confirmPassword" required />
        </div>

        <div class="input-group">
          <label for="license">License Number:</label>
          <input type="password" id="license" v-model="licenseNumber" required />
        </div>

        <div class="button-group">
          <b-button type="submit">Register</b-button>
          <b-button type="button" @click="returnToWelcome">Return</b-button>
        </div>
      </form>
    </div>
  </div>
</template>

<script src="@/scripts/RegistrationCustomer.js"></script>


<style scoped>
h2 {
  margin-top: 30px;
}

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

.input-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 0.5rem;
}

.label {
  margin-bottom: 0.5rem;
}

.button-group {
  display: flex;
  justify-content: space-between;
}
</style>
