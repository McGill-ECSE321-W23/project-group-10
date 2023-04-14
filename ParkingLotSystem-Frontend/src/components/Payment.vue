<template>
  <div class="payment">
    <!--Page appear for guests and monthly customers to pay for services/subscriptions. -->
    <b-button v-b-modal.paymentModal variant="primary" :disabled="disabled">Pay</b-button>
    <b-modal
      id="paymentModal"
      ref="modal"
      title="Enter Payment Information"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          label="Credit Card Number"
          label-for="credit-card-input"
          invalid-feedback="This field is required"
          :state="valueState"
        >
          <b-form-input
            id="credit-card-input"
            v-model="creditCardNbr"
            :state="valueState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
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
