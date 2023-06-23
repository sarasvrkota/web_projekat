<template>
  <div>
    <LogoutButton />
    <form @submit.prevent="dodajZanr">
      <label for="naziv">Naziv:</label>
      <input type="text" id="naziv" v-model="noviZanr.naziv" required>
      <button type="submit">Dodaj žanr</button>
    </form>
    <button @click="preusmeriNaNovuKnjigu">Dodaj novu knjigu</button>
    <button @click="preusmeri">Pregledaj knjige</button>
    <button @click="preusmeri2">Pregledaj zanrove</button>
    <button @click="pregledajZahteve">Pregledaj zahteve</button>
    <div v-if="prikaziZahteve">
      <h2>Zahtevi za aktivaciju naloga autora</h2>
      <ul>
        <li v-for="zahtev in zahtevi" :key="zahtev.email">
          <p>Email: {{ zahtev.email }}</p>
          <p>Telefon: {{ zahtev.telefon }}</p>
          <p>Poruka: {{ zahtev.poruka }}</p>
          <p>Autor ID: {{ zahtev.autorId }}</p>
          <p>Status: {{ zahtev.status }}</p>
          <button @click="prihvatiZahtev(zahtev)">Prihvati</button>
          <button @click="odbijZahtev(zahtev)">Odbij</button>
        </li>
      </ul>
    </div>
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
      noviZanr: {
        naziv: ''
      },
    prikaziZahteve: false,
    zahtevi: [],
    };
  
  },
  methods: {
    dodajZanr() {
      fetch('http://localhost:7070/api/zanrovi/dodavanje', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.noviZanr)
      })
        .then((res) => {
          if (res.ok) {
            return res.json();
          } else {
            throw new Error('Failed to add genre');
          }
        })
        .then((data) => {
          console.log(data);
          alert('Žanr uspešno dodat!');
          this.noviZanr.naziv = '';
        })
        .catch((error) => {
          console.error(error);
          alert('Došlo je do greške prilikom dodavanja žanra!');
        });
    },
    preusmeriNaNovuKnjigu() {
      this.$router.push('/dodavanje-knjige-admin');
    },
    preusmeri() {
      this.$router.push('/sveknjige');
    },
    preusmeri2() {
      this.$router.push('/svi-zanrovi');
    },

    pregledajZahteve() {
      fetch('http://localhost:7070/api/zahtevi/svi-zahtevi', {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Greška prilikom dobijanja zahteva.');
          }
        })
        .then((data) => {
          console.log(data);
          this.zahtevi = data;
          this.prikaziZahteve = true;
        })
        .catch((error) => {
          console.error(error);
          alert('Došlo je do greške prilikom dobijanja zahteva.');
        });
    },
    prihvatiZahtev(zahtev) {
      const id = zahtev.id; // Promenite prema strukturi vašeg objekta zahteva

      fetch(`http://localhost:7070/api/zahtevi/prihvati-zahtev/${id}`, {
        method: 'POST',
        credentials: 'include',
      })
        .then((response) => {
          if (response.ok) {
            return response.text();
          } else {
            throw new Error('Greška prilikom prihvatanja zahteva.');
          }
        })
        .then((data) => {
          console.log(data);
          alert('Zahtev je prihvaćen.');
          // Ažurirajte prikaz zahteva ili uradite novi poziv zahteva da dobijete ažurirane podatke
        })
        .catch((error) => {
          console.error(error);
          alert('Došlo je do greške prilikom prihvatanja zahteva.');
        });
    },
    odbijZahtev(zahtev) {
      const id = zahtev.id; // Promenite prema strukturi vašeg objekta zahteva

      fetch(`http://localhost:7070/api/zahtevi/odbij-zahtev/${id}`, {
        method: 'POST',
        credentials: 'include',
      })
        .then((response) => {
          if (response.ok) {
            return response.text();
          } else {
            throw new Error('Greška prilikom odbijanja zahteva.');
          }
        })
        .then((data) => {
          console.log(data);
          alert('Zahtev je odbijen.');
          // Ažurirajte prikaz zahteva ili uradite novi poziv zahteva da dobijete ažurirane podatke
        })
        .catch((error) => {
          console.error(error);
          alert('Došlo je do greške prilikom odbijanja zahteva.');
        });
    },
  
  }
};
</script>
