<template>
    <div class="employee">
      <!--Display at first after an employee login, provide a portal to edit the profile. -->
      <NavBar activeNav="employee" :username="username"/>
      <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
        <div class="content">
            <br>
            <br>
            <br>
            <h2>Welcome,</h2>
            <h2>{{username}}</h2>
            <router-link to="/edit-profile-employee" class="underline-button">Edit My Profile</router-link>
        </div>
    </div>
</template>

<script src="@/scripts/Employee.js"></script>

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
