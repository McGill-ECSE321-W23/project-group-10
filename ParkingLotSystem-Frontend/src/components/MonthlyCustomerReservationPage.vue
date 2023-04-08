<template>
  <div class="monthly-customer-reservation">

    <p>Please select the reservation type:</p>
    <input type="radio" id="sub-with-account" name="reservation-type" value="subWithAccount" v-model="reservationType">
    <label for="sub-with-account">subscription with account</label><br>
    <input type="radio" id="sub-without-account" name="reservation-type" value="subWithoutAccount" v-model="reservationType">
    <label for="sub-without-account">subscription without account</label><br>
    <div v-if="reservationType === 'subWithAccount'">
      <label for="monthly-customer-email">Monthly customer email:</label>
      <input type="email" id="monthly-customer-email" v-model="monthlyCustomerEmail">
    </div>

    <div v-if="reservationType === 'subWithoutAccount'">
      <label for="license-number">License number:</label>
      <input type="text" id="license-number" v-model="licenseNumber">
    </div>
    <div v-if="selectedSpot">
      Selected parking spot: {{ selectedSpot.id }} - {{ selectedSpot.status }}
    </div>

    <button v-on:click="createReservation">Submit</button>

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

<script src="@/scripts/MonthlyCustomerReservationPage.js"> </script>

<style scoped>


.monthly-customer-reservation {
  width: 1300px;
  max-height: 300px;
  
}

.table {

  height: 200px;
  width: 200px;
  overflow: scroll;
  border-collapse: collapse;
}

td,
th {
  padding: 8px;
  text-align: left;
  border: 1px solid #ddd;
  box-sizing: border-box;
}



</style>

</style>