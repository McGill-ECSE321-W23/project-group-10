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
      <div class="name">
        <label for="name">Name: </label>
        <input type="text" id="name" v-model="monthlyCustomer.name" placeholder="Enter New Name">
      </div>
      <div class="email">
        <label for="email">Email: </label>
        <input type="text" id="email" v-model="monthlyCustomer.email" placeholder="Enter New Email">
      </div>
      <div class="phoneNumber">
        <label for="phoneNumber">Phone Number: </label>
        <input type="text" id="phoneNumber" v-model="monthlyCustomer.phone" placeholder="Enter New Phone Number">
      </div>
      <div class="password">
        <label for="password">Password: </label>
        <input type="text" id="password" v-model="monthlyCustomer.password" placeholder="Enter New Password">
      </div>
      <div class="licenseNumber">
        <label for="licenseNumber">License Number: </label>
        <input type="text" id="licenseNumber" v-model="monthlyCustomer.licenseNumber"
               placeholder="Enter New License Number">
      </div>
      <div class="button"><button v-on:click="updateMonthlyCustomer">Update!</button></div>
    </div>
  </div>

</template>

<script src="../scripts/EditProfileMonthlyCustomer.js"></script>

<style scoped>

input {
  width: 300px;
}
button {
  width: 200px;
  height: 50px;
  margin-top: 50px;
}
.Edit-Profile-Page-Monthly-Customer{
  height: 100%;
  width: 100%;
  display: block;
  position: relative;
}

.content{
  position: relative;
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

.name{
  display: flex;
  justify-content: space-around;
  margin-inline-start: 450px;
  margin-inline-end: 417px;
  align-items: flex-start;
  margin-top: 50px;
}

.email{
  display: flex;
  justify-content: space-around;
  margin-inline-start: 450px;
  margin-inline-end: 415px;
  align-items: flex-start;
  margin-top: 20px;
}

.phoneNumber{
  display: flex;
  justify-content: space-around;
  margin-inline-start: 425px;
  margin-inline-end: 432px;
  align-items: flex-start;
  margin-top: 20px;
}

.password{
  display: flex;
  justify-content: space-around;
  margin-inline-start: 440px;
  margin-inline-end: 425px;
  align-items: flex-start;
  margin-top: 20px;
}

.licenseNumber{
  display: flex;
  justify-content: space-around;
  margin-inline-start: 420px;
  margin-inline-end: 435px;
  align-items: flex-start;
  margin-top: 20px;
}

.button{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: -50px;
}
</style>
