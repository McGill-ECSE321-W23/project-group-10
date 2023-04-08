<template>
    <div class="guest">
      <NavBar 
        :navItems="['dashboard-guest', 'reservations-view']" 
        activeNav="dashboard-guest" 
        :username="username" 
      />
        <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
        <div class="content">
            <h2>Welcome, Customer: {{licenseNumber}}</h2>
        </div>
    </div>
</template>

<script src="@/scripts/Guest.js"></script>

<style scoped>
  h3 {
    margin-bottom: 30px;
  }
  .card {
    min-width: 30%;
    max-width: 100%;
    margin-bottom: 30px;
  }
  .form {
    justify-content: center;
    margin-bottom: 30px;
  }
  .underline-button {
    border: none;
    color: #007aff;
    text-decoration: underline;
    background-color: transparent;
    cursor: pointer;
    font-size: 20px;
    font-family: 20px;
  }
</style>