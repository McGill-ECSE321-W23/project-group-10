<template>
  <div class="monthly-customer-reservation">

    <NavBar 
        :navItems="['dashboard']" 
        activeNav="CreateMonthlyCustomerReservationPage" 
        :username="username" 
      />
    <p>Create Monthly customer reservation:</p>

    <input type="email" id="monthly-customer-email" v-model="monthlyCustomerEmail" placeholder="Enter monthly customer email">

    <div v-if="selectedSpot">
      Selected parking spot: {{ selectedSpot.id }} - {{ selectedSpot.status }}
    </div>

    <button v-on:click="createReservation">Submit</button>
    <p>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}} </span>
    </p>

    <div class = "table">
      <table class="parking-spot-list">
        <thead>
            <tr>
                <th>Parking Spot ID</th>
                <th>Parking Spot Status</th>
            </tr>
        </thead>
        <tbody class = "scrollable-parking-spot-list" style="max-height: 200px; overflow-y: scroll;">
            <tr v-for="parkingSpot in parkingSpots" :key="parkingSpot.id" @click="showSelectedSpot(parkingSpot)">
                <td>{{ parkingSpot.id }}</td>
                <td>{{ parkingSpot.status }}</td>
            </tr>
        </tbody>
     </table>
    </div>
  </div>
</template>

<script src="@/scripts/CreateMonthlyCustomerReservationPage.js"> </script>

<style scoped>
.monthly-customer-reservation {
  width: 1300px;
  max-height: 300px;
  align-content: center;
  
}

.table {

  height: 200px;
  width: 200px;
  overflow: scroll;
  border-collapse: collapse;
  margin: 0 auto;
}

td,
th {
  padding: 8px;
  text-align: left;
  border: 1px solid #ddd;
  box-sizing: border-box;
}
</style>
