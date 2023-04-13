<template>
  <div>
    <NavBar activeNav="employee-registration"/>
    <b-alert v-model="showError" :variant="alertVariant" dismissible>{{ errorMessage }}</b-alert>
    <div class="form-container content">
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
          <input type="text" id="phone" v-model="phone" required />
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
          <b-button type="submit" variant="primary">Register</b-button>
        </div>
      </form>
    </div>
  </div>
</template>

<script src="@/scripts/registrationEmployee.js"></script>


<style scoped>
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

.button-group {
  display: flex;
  justify-content: center;
}
</style>
