<template>
    <div>
      <h1>Ažuriranje knjige</h1>
      <form @submit.prevent="azurirajKnjigu">
        <label for="naslovKnjige">Naslov knjige:</label>
        <input type="text" id="naslovKnjige" v-model="knjiga.naslov" required>
  
        <!-- Dodajte ostala polja forme za ažuriranje knjige -->
        <label for="naslovnaFotografija">Naslovna fotografija:</label>
        <input type="text" id="naslovnaFotografija" v-model="knjiga.naslovnaFotografija" required>
  
        <label for="ISBN">ISBN:</label>
        <input type="text" id="ISBN" v-model="knjiga.ISBN" required>
  
        <!-- Dodajte ostala polja forme za ažuriranje knjige -->
  
        <button type="submit">Ažuriraj knjigu</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        knjiga: {
          naslov: '',
          naslovnaFotografija: '',
          ISBN: '',
          // Dodajte ostala polja za ažuriranje knjige
        },
      };
    },
    methods: {
      azurirajKnjigu() {
        fetch(`/azuriranje-knjige/${this.$route.params.naslovKnjige}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.knjiga),
          credentials: 'include',
        })
          .then((response) => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error('Greška prilikom ažuriranja knjige.');
            }
          })
          .then((data) => {
            console.log(data);
            alert('Knjiga uspešno ažurirana!');
            // Osvježavanje stranice ili vršenje odgovarajućih akcija nakon uspešnog ažuriranja knjige
          })
          .catch((error) => {
            console.error(error);
            alert('Došlo je do greške prilikom ažuriranja knjige.');
          });
      },
    },
  };
  </script>
  