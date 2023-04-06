<template>
  <div class="monthly-customer-reservation">
    <table>
        <thead>
            <tr>
                <th>Parking Spot ID</th>
                <th>Parking Spot Status</th>
            </tr>
        </thead>
        <tbody class = "scrollable-parking-spot-list">
            <tr v-for="parkingSpot in parkingSpots" :key="parkingSpot.id">
                <td>{{ parkingSpot.id }}</td>
                <td>{{ parkingSpot.id }}</td>
            </tr>
        </tbody>
    </table>
  </div>
</template>

<script src="@/scripts/MonthlyCustomerReservationPage.js"> </script>

<style scoped>

.monthly-customer-reservation{
    width: 300px;
    max-height: 200px;
    overflow:scroll
}
table {
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