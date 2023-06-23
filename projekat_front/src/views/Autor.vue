<template>
  <div>
    <LogoutButton />
    <form @submit.prevent="posaljiZahtev">
      <label for="email">Email:</label>
      <input type="text" id="email" v-model="zahtev.email" required>
      
      <label for="telefon">Telefon:</label>
      <input type="text" id="telefon" v-model="zahtev.telefon" required>
      
      <label for="poruka">Poruka:</label>
      <textarea id="poruka" v-model="zahtev.poruka" required></textarea>
      
      <button type="submit">Pošalji zahtev</button>
    </form>
    <button @click="preusmeriNaNovuKnjiguA">Dodaj novu knjigu</button>
  </div>
</template>

<script>
import LogoutButton from '../components/Logout.vue';

export default {
  components: {
    LogoutButton
  },
  data() {
    return {
      zahtev: {
        email: '',
        telefon: '',
        poruka: ''
      }
    };
  },
  methods: {
    posaljiZahtev() {
      const prijavljeniKorisnik = JSON.parse(sessionStorage.getItem('korisnik'));

      // Proverite da li je korisnik prijavljen
      if (prijavljeniKorisnik) {
        // Dodajte ID korisnika u objekat zahteva
        this.zahtev.korisnikId = prijavljeniKorisnik.id;

        fetch('http://localhost:7070/api/zahtevi/posalji-zahtev', {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.zahtev)
        })
          .then((res) => {
            if (res.ok) {
              return res.json();
            } else {
              throw new Error('Failed to send request');
            }
          })
          .then((data) => {
            console.log(data);
            alert('Zahtev uspešno poslan!');

            this.zahtev.email = '';
            this.zahtev.telefon = '';
            this.zahtev.poruka = '';
          })
          .catch((error) => {
            console.error(error);
            alert('Došlo je do greške prilikom slanja zahteva!');
          });
      } else {
        alert('Niste prijavljeni.');
      }
    },
    preusmeriNaNovuKnjiguA() {
      this.$router.push('/dodavanje-knjige-autor');
    }
  }
};
</script>
