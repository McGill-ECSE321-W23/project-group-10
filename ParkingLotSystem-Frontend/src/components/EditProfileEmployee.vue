<template>
  <div class="Edit-Profile-Employee">
    <NavBar activeNav=""/>
    <b-alert v-model="showError" :variant="alertVariant" dismissible>{{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Edit Your Profile (Employee)</h2>
      <div class="name">
        <label for="name">Name: </label>
        <b-input 
          type="text" 
          id="name" 
          v-model="employee.name" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Name">
        </b-input>
      </div>
      <div class="email">
        <label for="email">Email: </label>
        <b-input 
          type="text" 
          id="email" 
          v-model="employee.email" 
          class="mx-auto custom-width mb-2" 
          readonly>
        </b-input>
      </div>
      <div class="phoneNumber">
        <label for="phoneNumber">Phone Number: </label>
        <b-input 
          type="text" 
          id="phoneNumber" 
          v-model="employee.phone" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Phone Number">
        </b-input>
      </div>
      <div class="password">
        <label for="password">Password: </label>
        <b-input 
          type="password" 
          id="password" 
          v-model="employee.password" 
          class="mx-auto custom-width mb-2" 
          placeholder="Enter New Password">
        </b-input>
      </div>
      <div class="button">
        <b-button type="submit" variant="info" class="mb-3" @click="updateEmployee">Update</b-button>
      </div>
    </div>
  </div>
</template>

<script src="../scripts/EditProfileEmployee.js"></script>

<style scoped>
</style>
