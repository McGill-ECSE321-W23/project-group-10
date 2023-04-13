<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Montserrat&family=Poppins&display=swap');

html, body {
  margin:0;
  height:100%;
  box-sizing: border-box;
  overflow-x: hidden;
}

body {
  background-image: linear-gradient(135deg, #ffffff 0%, #ffffff 100%);
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
}

#app {
  font-family: 'Montserrat', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.content {
  margin-top: 60px;
}

.custom-width {
  max-width: 300px;
}
</style>
