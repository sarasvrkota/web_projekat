<template>
  <div>
    <h1>Registracija</h1>
    <form @submit="registrujKorisnika">
      <label for="ime">Ime:</label>
      <input type="text" id="ime" v-model="korisnik.ime" required>
      
      <label for="prezime">Prezime:</label>
      <input type="text" id="prezime" v-model="korisnik.prezime" required>
      
      <label for="korisnickoIme">Korisničko ime:</label>
      <input type="text" id="korisnickoIme" v-model="korisnik.korisnickoIme" required>
      
      <label for="mail">Email:</label>
      <input type="email" id="mail" v-model="korisnik.mail" required>
      
      <label for="lozinka">Lozinka:</label>
      <input type="password" id="lozinka" v-model="korisnik.lozinka" required>
      
      <label for="potvrdaLozinke">Potvrda lozinke:</label>
      <input type="password" id="potvrdaLozinke" v-model="korisnik.potvrdaLozinke" required>
      
      <button type="submit">Registruj se</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Registracija',
  data() {
    return {
      korisnik: {
        ime: '',
        prezime: '',
        korisnickoIme: '',
        mail: '',
        lozinka: '',
        potvrdaLozinke: ''
      }
    };
  },
  methods: {
    registrujKorisnika(event) {
      event.preventDefault();

      fetch('http://localhost:7070/api/korisnici/registracija', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.korisnik)
      })
        .then(response => {
          if (response.ok) {
            alert('Uspešno ste se registrovali!');
            this.$router.push('/login');
          } else {
            throw new Error('Neuspešna registracija');
          }
        })
        .catch(error => {
          console.error(error);
          alert('Došlo je do greške prilikom registracije.');
        });
    }
  }
};
</script>