<template>
  <div>
    <h1>Prikaz svih profila korisnika</h1>
    <ul>
      <li v-for="profil in profiler" :key="profil.id">
        <h2>Ime: {{ profil.ime }}</h2>
        <p>Prezime: {{ profil.prezime }}</p>
        <!-- Ostali prikazi podataka -->
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GetProfili',
  data() {
    return {
      profiler: []
    };
  },
  mounted() {
    this.pregledajProfile();
  },
  methods: {
    pregledajProfile() {
      axios
        .get("http://localhost:7070/api/korisnici/prikaz-profila", { withCredentials: true })
        .then((response) => {
          this.profiler = response.data;
        })
        .catch((error) => {
          console.log(error);
          alert("Failed to fetch korisnici");
        });
    }
  }
};
</script>