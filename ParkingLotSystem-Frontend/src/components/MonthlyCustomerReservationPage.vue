<template>
  <div class="list-container">
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
                <td>{{ parkingSpot.parkingSpotStatus }}</td>
            </tr>
        </tbody>
    </table>
    <ul>
      <li v-for="item in items" :key="item.id">{{ item.name }}</li>
    </ul>
  </div>
</template>

<script> src="@/scripts/MonthlyCustomerReservationPage.js"</script>

<style scoped>

.list-container {
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