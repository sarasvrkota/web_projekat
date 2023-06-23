<template>
    <div>
      <LogoutButton />
      <form @submit.prevent="dodajKnjigu">
        <label for="naslov">Naslov:</label>
        <input type="text" id="naslov" v-model="novaKnjiga.naslov" required>
        <label for="fotografija">Naslovna fotografija:</label>
        <input type="text" id="fotografija" v-model="novaKnjiga.naslovnaFotografija" required>
        <label for="datum">Datum objavljivanja:</label>
        <input type="date" id="datum" v-model="novaKnjiga.datumObjavljivanja" required>
        <label for="strane">Broj strana:</label>
        <input type="number" id="strane" v-model="novaKnjiga.brojStrana" required>
        <label for="opis">Opis:</label>
        <textarea id="opis" v-model="novaKnjiga.opis" required></textarea>
        <label for="isbn">ISBN:</label>
        <input type="text" id="isbn" v-model="novaKnjiga.ISBN" required>
        <label for="zanr">Žanr:</label>
        <input type="text" id="zanr" v-model="novaKnjiga.zanr" required>
        <label for="ocena">Ocena:</label>
        <input type="number" id="ocena" v-model="novaKnjiga.ocena" required>
        <button type="submit">Dodaj knjigu</button>
      </form>

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
        novaKnjiga: {
          naslov: '',
          naslovnaFotografija: '',
          datumObjavljivanja: '',
          brojStrana: 0,
          opis: '',
          ISBN: '',
          zanr: {
            naziv: ''
          },
          ocena: 0
        }
      };
    },
    methods: {
      dodajKnjigu() {
        fetch('http://localhost:7070/api/knjige/dodavanje-knjiga-admin', {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.novaKnjiga)
        })
          .then((res) => {
            if (res.ok) {
              return res.json();
            } else {
              throw new Error('Failed to add book');
            }
          })
          .then((data) => {
            console.log(data);
            alert('Knjiga uspešno dodata!');
            this.novaKnjiga.naslov = '';
            this.novaKnjiga.naslovnaFotografija = '';
            this.novaKnjiga.datumObjavljivanja = '';
            this.novaKnjiga.brojStrana = 0;
            this.novaKnjiga.opis = '';
            this.novaKnjiga.ISBN = '';
            this.novaKnjiga.zanr = '';
            this.novaKnjiga.ocena = 0;
          })
          .catch((error) => {
            console.error(error);
            alert('Došlo je do greške prilikom dodavanja knjige!');
          });
      },
    
    }
  };
  </script>
  