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
html, body {
  margin:0;
  height:100%;
  box-sizing: border-box;
}

body {
  background-image: linear-gradient(135deg, #dbe5f3 0%, #ffffff 100%);
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
}

#app {
  font-family: 'Poppins', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.content {
  margin-top: 60px;
}
</style>
