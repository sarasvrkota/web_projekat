<template>
  <div>
    <h1>Dobrodošli na stranicu</h1>
    <router-link to="/registracija">
      <button>Registracija</button>
    </router-link>
    

    <div class="button-container">
      <a type="button" href="/login" class="btn">Prijavite se</a>
    </div>

    <registracija v-if="showRegistracija" @register="handleRegister"></registracija>
  </div>
</template>

<script>
import Registracija from "./Registracija.vue";

export default {
  name: "HomeView",
  components: {
    Registracija,
  },
  data() {
    return {
      showRegistracija: false,
    };
  },
  methods: {
    redirectToRegistracija() {
      this.showRegistracija = true;
    },
    handleRegister() {
      console.log("Registracija uspešno obavljena!");
      this.showRegistracija = false;
    },
  },
};
</script>



<style>
.btn {
  background-color: #c0d642;
  color: white;
  padding: 10px 20px;
  text-decoration: none;
  border-radius: 2px;
  font-weight: bold;
}

.btn:hover {
  background-color: #23833a;
}
</style>

