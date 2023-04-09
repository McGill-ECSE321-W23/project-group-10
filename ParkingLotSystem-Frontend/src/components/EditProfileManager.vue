<template>
  <div class="Edit-Profile-Manager">
      <NavBar
        :navItems="['dashboard', 'settings', 'services-admin', 'reservations']"
        activeNav="services-admin"
        :username="username"
      />
      <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Edit Your Profile (Manager)</h2>
      <div>
        <label for="name">Name: </label>
        <input type="text" id="name" v-model="manager.name" placeholder="Enter New Name">
      </div>
      <div>
        <label for="name">Email: </label>
        <input type="text" id="email" v-model="manager.email" placeholder="Enter New Email">
      </div>
      <div>
        <label for="name">Phone Number: </label>
        <input type="text" id="phoneNumber" v-model="manager.phone" placeholder="Enter New Phone Number">
      </div>
      <div>
        <label for="name">Password: </label>
        <input type="text" id="password" v-model="manager.password" placeholder="Enter New Password">
      </div>
      <div class="button">
        <button v-on:click="updateManager">Update</button>
      </div>
    </div>
  </div>
</template>

<script src="../scripts/EditProfileManager.js"></script>

<style scoped>

</style>
