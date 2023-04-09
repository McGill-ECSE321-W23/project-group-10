<template>
  <div class="Edit-Profile-Page-Monthly-Customer">
    <NavBar
      :navItems="['dashboard', 'settings', 'services-admin', 'reservations']"
      activeNav="services-admin"
      :username="username"
    />
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Edit Your Profile (Monthly Customer)</h2>
      <div>
        <label for="name">Name: </label>
        <input type="text" id="name" v-model="monthlyCustomer.name" placeholder="Enter New Name">
      </div>
      <div>
        <label for="email">Email: </label>
        <input type="text" id="email" v-model="monthlyCustomer.email" placeholder="Enter New Email">
      </div>
      <div>
        <label for="phoneNumber">Phone Number: </label>
        <input type="text" id="phoneNumber" v-model="monthlyCustomer.phone" placeholder="Enter New Phone Number">
      </div>
      <div>
        <label for="password">Password: </label>
        <input type="text" id="password" v-model="monthlyCustomer.password" placeholder="Enter New Password">
      </div>
      <div>
        <label for="licenseNumber">License Number: </label>
        <input type="text" id="licenseNumber" v-model="monthlyCustomer.licenseNumber"
               placeholder="Enter New License Number">
      </div>
      <div class="button"><button v-on:click="updateMonthlyCustomer">Update</button></div>
    </div>
  </div>

</template>

<script src="../scripts/EditProfileMonthlyCustomer.js"></script>

<style scoped>

</style>
