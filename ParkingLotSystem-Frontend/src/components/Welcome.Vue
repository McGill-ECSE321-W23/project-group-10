<template>
  <div class="Welcome">
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <img src="@/assets/parkin_logo.png" id="logo" alt="logo">
      <h2>Welcome to our Parking Lot System</h2>
      <b-button class="mb-2 mr-2" href="/#/login">Login</b-button>
      <b-button class="mb-2" href="/#/registration-customer">Register</b-button>
      <br>
      <b-link href="/#/guest">Continue as guest</b-link>
    </div>

  </div>
</template>

<script src="@/scripts/Welcome.js">
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
}

#logo {
  width: 20%;
  justify-self: center;
  margin-bottom: 30px;
  margin-top: 20px;
}
</style>