<template>
    <div class="AdminSettings">
        <NavBar :navItems="navItems" :username="username"/>
        <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
        <div class="content">
            <h2>Parking Lot System Setting</h2>
            <p id="msg">
                Hello Admin, you can edit the settings below.
            </p>
            <div class="Opening Hours" id="OH">
                <h3>Opening Hours</h3>
                <label for="hour">From : </label>
                <select name="hour" id="startHour">
                <option value=0>0</option>
                <option value=1>1</option>
                <option value=2>2</option>
                <option value=3>3</option>
                <option value=4>4</option>
                <option value=5>5</option>
                <option value=6>6</option>
                <option value=7>7</option>
                <option value=8>8</option>
                <option value=9>9</option>
                <option value=10>10</option>
                <option value=11>11</option>
                <option value=12>12</option>
                </select>

                <select name="time" id="startTime">
                <option value="AM">AM</option>
                <option value="PM">PM</option>
                </select>
                <br>
                <label for="hour">To :&nbsp; &nbsp; &nbsp; </label>
                <select name="hour" id="closeHour">
                <option value=0>0</option>
                <option value=1>1</option>
                <option value=2>2</option>
                <option value=3>3</option>
                <option value=4>4</option>
                <option value=5>5</option>
                <option value=6>6</option>
                <option value=7>7</option>
                <option value=8>8</option>
                <option value=9>9</option>
                <option value=10>10</option>
                <option value=11>11</option>
                <option value=12>12</option>
                </select>

                <select name="time" id="closeTime">
                <option value="AM">AM</option>
                <option value="PM">PM</option>
                </select>
                <button type="submit" id="setTime" @click="setHours()"> Set</button><br>

            
            <h3>Parking Fees</h3>
            <label for="type" >Parking Spot Type :  </label>
            <select name="type" id="type" :parkingSpotType="parkingSpotType">
            <option value="">select a type</option>
            </select>
            <br>
            <label for="fee">Fee : </label>
            <input type="number" step=".01" id="parkingFee" name = "parkingFee"> <label>in CAD$ per</label>
            <select name="unit" id="unit">
                <option value="Hour">Hour</option>
                <option value="Month">Month</option>
            </select>
            <br>
            <button type="submit" id="setFee" @click="setFee()"> Set</button>

            <h3>Available Services</h3>
            <label for="add a service">Add a service : </label><br>
            <label for="name of the new service">Description :  </label>
            <input type="text" id ="typeName" name = "typeName"><br>
            <label for="price of the new service">Price : &nbsp; &nbsp; &nbsp;</label>
            <input type="number" step=".01" id="serviceFee" name = "serviceFee"><label> &nbsp;in CAD$</label><br>
            <button type="submit" id="addService" @click="addService()"> Add</button><br><br>

            <label for="delete a service"> Delete a service : </label>
            <select name ="name of the service" id="serviceDelete" :service="service">
                <option value="">Select an existing service to delete</option>
            </select><br>
            <button type="submit" id="deleteService"> Delete</button><br><br>

            </div>
        </div>
    </div>
</template>
<script src="@/scripts/AdminSetting.js"></script>
<style>
    h2 {
        text-align: left;
    }

    #msg {
        color : red;
        text-align: justify;
        margin-left: 2%;
    }
    
    #OH {
        text-align: left;
        margin-left: 500px;
        
    }
    h3 {
            position: relative;
            right:200px
        }
    
    #setFee{
        border-color: dodgerblue;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 23%;
    }
    
    #setFee:Hover{
        background-color: dodgerblue;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 23%;
    }

    #setTime{
        border-color: dodgerblue;
        padding: 3px 10px;
        border-radius: 8px;
    }
    
    #setTime:Hover{
        background-color: dodgerblue;
        padding: 3px 10px;
        border-radius: 8px;
    }
    #addService{
        border-color: chartreuse;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 23%;

    }
    #addService:Hover{
        background-color:chartreuse;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 23%;

    }

    #deleteService{
        border-color: red;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 22%;

    }
    #deleteService:Hover{
        background-color:red;
        padding: 3px 10px;
        border-radius: 8px;
        margin-left: 22%;

    }

</style>