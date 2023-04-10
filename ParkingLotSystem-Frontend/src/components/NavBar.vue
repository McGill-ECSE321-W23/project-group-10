<template>
<div>
  <b-navbar toggleable="md"  fixed="top" sticky>
    <b-navbar-brand href="#/Welcome">Parking Lot System</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item v-for="item in filterNavItems()" :key="item.text" :href="item.href" :active="item.active">{{ item.text }}</b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown v-if="username" right>
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <b>{{ username }}</b>
          </template>
          <b-dropdown-item :href="profilePageURL">Profile</b-dropdown-item>
          <b-dropdown-item @click="logout()">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item v-else><b>Login</b></b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script src="../scripts/NavBar.js"></script>

<style>

</style>