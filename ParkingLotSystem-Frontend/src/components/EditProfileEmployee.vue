<template>
  <div class="Edit-Profile-Employee">
    <NavBar
      :navItems="['dashboard', 'settings', 'services-admin', 'reservations']"
      activeNav="services-admin"
      :username="username"
    />
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Edit Your Profile (Employee)</h2>
      <div>
        <label for="name">Name: </label>
        <input type="text" id="name" v-model="employee.name" placeholder="Enter New Name">
      </div>
      <div>
        <label for="email">Email: </label>
        <input type="text" id="email" v-model="employee.email" placeholder="Enter New Email">
      </div>
      <div>
        <label for="phoneNumber">Phone Number: </label>
        <input type="text" id="phoneNumber" v-model="employee.phone" placeholder="Enter New Phone Number">
      </div>
      <div>
        <label for="password">Password: </label>
        <input type="text" id="password" v-model="employee.password" placeholder="Enter New Password">
      </div>
      <div class="button"><button v-on:click="updateEmployee">Update</button></div>

    </div>
  </div>
</template>

<script src="../scripts/EditProfileEmployee.js"></script>

<style scoped>

</style>
