<template>
  <div class="manager">
    <NavBar 
      :navItems="['dashboard', 'settings', 'services-admin', 'reservations']" 
      activeNav="dashboard" 
      :username="username" 
    />
    <div class="content">
      <h2>Dashboard</h2>
      <h5>Value label</h5>
      <b-progress :value="progress" max="100" show-value class="mb-3"></b-progress>
    </div>
  </div>
</template>

<script src="../scripts/ManagerDashboard.js"></script>

<style scoped></style>