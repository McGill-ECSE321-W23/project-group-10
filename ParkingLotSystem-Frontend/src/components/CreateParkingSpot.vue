<template>
  <div class="parking-spot">
    <NavBar :navItems="navItems" :username="username"/>
    <h1>Create Parking Spot</h1>
    <form>
      <div class="form-group">
        <label for="id">Parking ID:</label>
        <input type="text" id="id" v-model="parkingSpot.id" class="form-control" placeholder="Enter parking ID">
      </div>
      <div class="form-group">
        <label for="type">Parking Type:</label>
        <input type="text" id="type" v-model="parkingSpot.type" class="form-control" placeholder="Enter parking type">
      </div>
      <button type="submit" class="btn btn-primary" v-on:click="createParkingSpot">Create</button>
    </form>
  </div>
</template>

<script src="@/scripts/CreateParkingSpot.js"></script>

<style scoped>
.parking-spot {
  margin: 50px auto;
  width: 600px;
}

h1 {
  font-size: 36px;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 18px;
  margin-bottom: 10px;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.btn {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  color: #fff;
  background-color: #007bff;
  cursor: pointer;
}

.btn:hover {
  background-color: #0069d9;
}

.btn:focus {
  outline: none;
  box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.5);
}
</style>
