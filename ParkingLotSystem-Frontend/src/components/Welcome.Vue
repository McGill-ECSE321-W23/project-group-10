<template>
    <div class="Welcome">
        <NavBar :navItems="navItems" :username="username"/>
        <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
        <div class="content">
            <img src="@/assets/logoApp.png" id="logo" style="float:right;width:300px;height:300px;" alt="alternatetext">
            <!--From Figma Widget-->
            <h2>Welcome to Parking Lot System</h2>

        </div>
        
    </div>
</template>
<script src="@/scripts/Welcome.js">
</script>
<style>
    #logo{
        display: block;
        width: 50%;
    }
    @keyframes floating {
    0% { transform: translate(0,  0px); }
    50%  { transform: translate(0, 20px); }
    100%   { transform: translate(0, 0px); }   
    }
    h2{
        left: 30%;
        position: absolute;
        top: 40%;
        animation: floating 3s infinite;
    }
</style>