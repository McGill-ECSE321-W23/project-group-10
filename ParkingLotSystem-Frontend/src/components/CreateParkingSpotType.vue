<template>
  <div class="parking-spot-type">
    <!--Page to create the parking spot type.-->
    <h1>Create Parking Spot Type</h1>
    <form>
      <div class="form-group">
        <input type="text" id="name" v-model="parkingSpotType.typeName" placeholder="Enter type name">
      </div>

      <div class="form-group">
        <input type="text" id="fee" v-model="parkingSpotType.fee" placeholder="Enter fee">
      </div>

      <button class="btn" v-on:click="createParkingSpotType">Submit</button>
    </form>
  </div>
</template>

<script src =  "@/scripts/CreateParkingSpotType.js"> </script>

<style scoped>
.parking-spot-type {
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
