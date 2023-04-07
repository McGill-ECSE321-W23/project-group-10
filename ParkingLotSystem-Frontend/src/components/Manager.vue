<template>
  <div class="manager">
    <NavBar 
      :navItems="['dashboard', 'settings', 'services-admin', 'reservations']" 
      activeNav="dashboard" 
      :username="username" 
    />
    <div class="content">
      <h1>Hello world</h1>
    </div>
  </div>
</template>

<script src="../scripts/Manager.js"></script>

<style scoped></style>