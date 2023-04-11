<template>
  <div class="Welcome">
    <!-- <NavBar :navItems="navItems" :username="username"/> -->
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <img src="@/assets/logoApp.png" id="logo" alt="logo">
      <!--From Figma Widget-->
      <h2>Welcome to Parking Lot System</h2>
      <b-button href="/#/login">Login</b-button>
      <br>
      <b-link href="/#/guest">Continue as guest</b-link>
    </div>

  </div>
</template>
<script src="@/scripts/Welcome.js">
</script>
<style>
#logo {
  display: float;
  width: 10%;
  justify-self: center;
}

@keyframes floating {
  0% {
    transform: translate(0, 0px);
  }

  50% {
    transform: translate(0, 20px);
  }

  100% {
    transform: translate(0, 0px);
  }
}

#appTitle {
  left: 30%;
  position: absolute;
  top: 40%;
  animation: floating 3s infinite;
}</style>