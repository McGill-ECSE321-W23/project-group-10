<template>
  <div class="parking-spot">
    <!--Page to create the parking spot.-->
    <h1>Create Parking Spot</h1>
    <form>
      <div class="form-group">
        <input type="text" id="id" v-model="parkingSpot.id" class="form-control" placeholder="Enter parking ID">
      </div>
      <div class="form-group">
        <input type="text" id="type" v-model="parkingSpot.type" class="form-control" placeholder="Enter parking type">
      </div>
      <button type="submit" class="btn btn-primary" v-on:click="createParkingSpot">Create</button>
    </form>
  </div>
</template>
<script src="@/scripts/CreateParkingSpot.js"></script>

<style scoped>


.parking-spot {
  max-width: 500px;
  margin: 0 auto;
  padding: 40px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}
h1 {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
}


input[type="text"] {
  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 10px;
  font-size: 16px;
}

.btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 3px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #3e8e41;
}
</style>
