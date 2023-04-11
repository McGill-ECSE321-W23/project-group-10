<template>
  <div class="login-page">
    <h2>Login</h2>
    <div class="form-group">
      <input type="email" id="email" v-model="email" class="form-control" placeholder="Enter email">
    </div>
    <div class="form-group">
      <input type="password" id="password" v-model="password" class="form-control" placeholder="Enter password">
    </div>
    <div class="button-group">
      <button @click="loginAsManager" class="btn btn-primary">Login as Manager</button>
      <button @click="loginAsEmployee" class="btn btn-primary">Login as Employee</button>
      <button @click="loginAsCustomer" class="btn btn-primary">Login as Customer</button>
    </div>
    <span v-if="errorEvent" style="color:red">Error: {{errorEvent}} </span>
  </div>
</template>

<script src="@/scripts/LoginPage.js"> </script>

<style>
.login-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: center;
}

button {
  margin: 0 0.5rem;
}
</style>