<template>
  <div>
    <div class="registrationEmployee">
        <NavBar 
            :navItems="['dashboard', 'settings', 'services-admin', 'monthly-customer-reservation', 'employee-registration']" 
            activeNav="registrationEmployee" 
            :username="username" 
        />
    </div>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <!-- <div class="title-bar">
      <h1>Parking Lot System---Registration</h1>
    </div> -->

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

        <div class="button-group">
          <button type="submit">Register</button>
          <!-- <button type="button" @click="returnToLogin">Return</button> -->
        </div>
      </form>
    </div>
  </div>
</template>

<script src="@/scripts/registrationEmployee.js"></script>


<style scoped>
.exception-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.exception-modal-content {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  text-align: center;
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
  margin-bottom: 1rem;
}

.label {
  margin-bottom: 0.5rem;
}

.button-group {
  display: flex;
  justify-content: center;
}
</style>
