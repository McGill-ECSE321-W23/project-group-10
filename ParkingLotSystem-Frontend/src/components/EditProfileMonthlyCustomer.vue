<template>
  <div class="Edit-Profile-Page-Monthly-Customer">
    <NavBar activeNav="services-admin"/>
    <b-alert v-model="showError" :variant="alertVariant" dismissible>{{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Edit Your Profile (Monthly Customer)</h2>
      <div class="name">
        <label for="name">Name: </label>
        <b-input 
          type="text" 
          id="name" 
          v-model="monthlyCustomer.name" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Name">
        </b-input>
      </div>
      <div class="email">
        <label for="email">Email: </label>
        <b-input 
          type="text" 
          id="email" 
          v-model="monthlyCustomer.email" 
          class="mx-auto custom-width mb-2" 
          readonly>
        </b-input>
      </div>
      <div class="phoneNumber">
        <label for="phoneNumber">Phone Number: </label>
        <b-input 
          type="text" 
          id="phoneNumber" 
          v-model="monthlyCustomer.phone" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Phone Number">
        </b-input>
      </div>
      <div class="password">
        <label for="password">Password: </label>
        <b-input 
          type="password" 
          id="password" 
          v-model="monthlyCustomer.password" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Password">
        </b-input>
      </div>
      <div class="license-number">
        <label for="license-number">License Number: </label>
        <b-input 
          type="text" 
          id="license-number" 
          v-model="monthlyCustomer.licenseNumber" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New License number">
        </b-input>
      </div>
      <div class="button">
        <b-button type="submit" variant="info" class="mb-3" @click="updateMonthlyCustomer">Update</b-button>
      </div>
    </div>
  </div>

</template>

<script src="../scripts/EditProfileMonthlyCustomer.js"></script>

<style scoped>
</style>
