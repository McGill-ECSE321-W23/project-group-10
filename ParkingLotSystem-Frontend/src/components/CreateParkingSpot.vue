<template>
  <div class="parking-spot">
  
    <input type="text" id="id" v-model="parkingSpot.id" placeholder="enter parking id">
    <input type="text" id="type" v-model="parkingSpot.type" placeholder="enter parking type" >

    <button v-on:click="createParkingSpot">Submit</button>
  </div>
</template>
<script src =  "@/scripts/CreateParkingSpot.js"> </script>