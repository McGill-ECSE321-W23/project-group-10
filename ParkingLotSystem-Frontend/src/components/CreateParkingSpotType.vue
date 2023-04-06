<template>
  <div class="parking-spot-type">
    <label for="name">Parking Type Name:</label>
    <input type="text" id="name" v-model="parkingSpotType.typeName">

    <label for="fee">Fee:</label>
    <input type="text" id="fee" v-model="parkingSpotType.fee" >

    <button v-on:click="createParkingSpotType">Submit</button>
  </div>
</template>
<script src =  "@/scripts/CreateParkingSpot.js"> </script>
