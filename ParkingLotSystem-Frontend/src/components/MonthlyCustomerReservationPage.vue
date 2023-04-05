<template>
  <div class="dropdown-container">
    <input type="text" v-model="selectedOption" @click="toggleOptions">
    <i class="arrow-icon" @click="toggleOptions"></i>
    <ul v-show="showOptions">
      <li v-for="(option, index) in options" :key="index" @click="selectOption(option)">{{ option }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedOption: '',
      options: ['Option 1', 'Option 2', 'Option 3', 'Option 4'],
      showOptions: false,
    };
  },
  methods: {
    toggleOptions() {
      this.showOptions = !this.showOptions;
    },
    selectOption(option) {
      this.selectedOption = option;
      this.showOptions = false;
    },
  },
};
</script>

<style>
.dropdown-container {
  position: relative;
}
.arrow-icon {
  position: absolute;
  top: 50%;
  right: 5px;
  transform: translateY(-50%);
  pointer-events: none;
}
ul {
  max-height: 200px;
  overflow-y: scroll;
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  margin: 0;
  padding: 0;
  list-style: none;
  background-color: #fff;
  border: 1px solid #ccc;
  border-top: none;
}
li {
  padding: 5px 10px;
  cursor: pointer;
}
li:hover {
  background-color: #f2f2f2;
}
</style>
