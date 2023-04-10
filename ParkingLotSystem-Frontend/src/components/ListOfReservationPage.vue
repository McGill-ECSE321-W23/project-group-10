<template>
  <div class="list-of-reservation">
    <NavBar 
        :navItems="['dashboard']" 
        activeNav="ListOfReservationPage" 
        :username="username" 
      />

    <h1>All Reservations</h1>
    
    <div class="table-container">
      <table>
        <caption>All Subscriptions With Account</caption>
        <thead>
          <tr>
            <th>Reservation ID</th>
            <th>Date</th>
            <th>License Number</th>
            <th>Customer Email</th>
            <th>Parking Spot Number</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sub in subWithAccounts" :key="sub.reservationId">
            <td>{{ sub.reservationId }}</td>
            <td>{{ sub.date }}</td>
            <td>{{ sub.monthlyCustomer.licenseNumber }}</td>
            <td>{{ sub.monthlyCustomer.email }}</td>
            <td>{{ sub.parkingSpotDto.id }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div class="table-container">
      <table>
        <caption>All Subscriptions Without Account</caption>
        <thead>
          <tr>
            <th>Reservation ID</th>
            <th>Date</th>
            <th>License Number</th>
            <th>Parking Spot Number</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sub in subWithoutAccounts" :key="sub.reservationId">
            <td>{{ sub.reservationId }}</td>
            <td>{{ sub.date }}</td>
            <td>{{ sub.licenseNumber }}</td>
            <td>{{ sub.parkingSpotDto.id }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div class="table-container">
      <table>
        <caption>All Single Reservations</caption>
        <thead>
          <tr>
            <th>Reservation ID</th>
            <th>Date</th>
            <th>License Number</th>
            <th>Parking Spot Number</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="reservation in singleReservations" :key="reservation.id">
            <td>{{ reservation.reservationId }}</td>
            <td>{{ reservation.date }}</td>
            <td>{{ reservation.licenseNumber }}</td>
            <td>{{ reservation.parkingSpotDto.id }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    
  </div>
</template>

<style scoped>
.list-of-reservation {
  margin: 50px;
}

h1 {
  font-size: 32px;
  margin-bottom: 20px;
}

.table-container {
  margin-bottom: 50px;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: #fff;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

caption {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

thead th {
  background-color: #f5f5f5;
  color: #000;
  font-weight: bold;
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

tbody td {
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}
</style>

<script src="@/scripts/ListOfReservationPage.js"></script>
