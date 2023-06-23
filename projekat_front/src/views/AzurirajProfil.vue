<template>
    <div>
      <h1>Ažuriraj profil</h1>
  
      <form @submit.prevent="submitForm">
        <label for="ime">Ime:</label>
        <input type="text" id="ime" v-model="korisnik.ime">
  
        <label for="prezime">Prezime:</label>
        <input type="text" id="prezime" v-model="korisnik.prezime">
  
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="korisnik.mail">

        <label for="korisnickoIme">korisnickoIme:</label>
        <input type="korisnickoIme" id="korisnickoIme" v-model="korisnik.korisnickoIme">

        <label for="lozinka">lozinka:</label>
        <input type="lozinka" id="lozinka" v-model="korisnik.lozinka">

        <label for="opis">Opis:</label>
        <input type="opis" id="opis" v-model="korisnik.opis">
  
        <!-- Dodajte ostala polja za unos profila -->
  
        <button type="submit">Sačuvaj</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        korisnik: {
          ime: '',
          prezime: '',
          email: '',
          korisnickoIme: '',
          lozinka: '',
          opis: ''
          // Dodajte ostala polja za unos profila
        }
      };
    },
    methods: {
      submitForm() {
        // Slanje podataka i ažuriranje profila
        // Možete koristiti fetch ili neki drugi HTTP zahtev
        // Na primer:
        fetch('http://localhost:7070/api/korisnici/azuriraj-profil', {
          method: 'PUT',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')// Dodajte odgovarajući token za autentifikaciju
          },
          body: JSON.stringify(this.korisnik)
        })
          .then((res) => {
            if (res.ok) {
              return res.text();
            } else {
              throw new Error('Profile update failed');
            }
          })

          .then((data) => {
            console.log(data);
            alert('Profil uspešno azuriran!');
          })
          .catch((error) => {
            console.error(error);
            alert('Došlo je do greške prilikom ažuriranja profila!');
          });
      }
    }
  };
  </script>