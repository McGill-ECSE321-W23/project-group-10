<template>
  <div class="payment">
    <!-- <form @submit.prevent="pay">
        <div class="input-group">
          <label for="amount">Credit Card Number:</label>
          <input type="text" id="amount" v-model="amount" required />
        </div>

        <div class="button-group">
          <button type="submit">Pay</button>
          <button type="button" @click="returnToMenu">Return</button>
        </div>
      </form> -->
    <b-form @submit="onSubmit" class="mx-auto" style="max-width: 400px; margin-top: 50px;" @reset="onCancel">
      <b-form-group
        id="input-group-1"
        label="Credit Card Number:"
        label-for="creditCardInput"
      >
        <b-form-input
          id="creditCardInput"
          v-model="creditCardNumber"
          type="text"
          placeholder="Enter Credit Card Number"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Cancel</b-button>
    </b-form>
  </div>
</template>

<script src="@/scripts/Payment.js"></script>

<style scoped>
.title-bar {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  background-color: #f3f3f3;
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.input-group,
.output-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.label {
  margin-bottom: 0.5rem;
}

.button-group {
  display: flex;
  justify-content: space-between;
}
</style>
