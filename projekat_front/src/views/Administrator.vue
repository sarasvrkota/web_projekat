<template>
  <div>
    <LogoutButton />
    <form @submit.prevent="dodajZanr">
      <label for="naziv">Naziv:</label>
      <input type="text" id="naziv" v-model="noviZanr.naziv" required>
      <button type="submit">Dodaj žanr</button>
    </form>
    <button @click="preusmeriNaNovuKnjigu">Dodaj novu knjigu</button>
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
      }
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
    }
  }
};
</script>
