<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
  <div>
    <table class="button-table">
      <tr>
        <td>
          <button class="full-width-button" @click="pregledajSveKnjige">Pregledaj sve knjige</button>
        </td>
        <td>
          <button class="full-width-button" @click="pregledajSveZanrove">Pregledaj sve zanrove</button>
        </td>
        <td>
          <button class="full-width-button" @click="posaljiZahtev">Posalji zahtev</button>
        </td>
       
      </tr>
    </table>
  </div>

  <div>
    <h2>Prikaz profila</h2>
    <div>
      <input type="text" v-model="ime" placeholder="Unesite ime" />
      <input type="text" v-model="prezime" placeholder="Unesite prezime" />
      <button @click="prikaziProfil">Prikaz profila</button>
    </div>
    <div v-if="profil">
      <h3>Profil korisnika</h3>
      <p>Ime: {{ profil.ime }}</p>
      <p>Prezime: {{ profil.prezime }}</p>
      <p>Datum rođenja: {{ profil.datumRodjenja }}</p>
      <p>Opis: {{ profil.opis }}</p>
      <p>Uloga: {{ profil.uloga }}</p>
    </div>
    <div v-else>
      <p>Profil nije pronađen.</p>
    </div>
  </div>

  <div>
  
      <h3>Police korisnika:</h3>
      <ul>
        <li v-for="polica in police" :key="polica.policaId">
          <h4>{{ polica.naziv }}</h4>
        
        </li>
      </ul>
        <li v-for="polica in police" :key="polica.id">
          {{polica.primarna ? 'Da' : 'Ne'}}
          </li>
    </div> 


  <div>
    <h2>Pretraga knjiga</h2>
    <div>
      <input type="text" v-model="naslov" placeholder="Unesite naslov knjige" />
      <button @click="pretraziKnjige">Pretraga</button>
    </div>
    <div v-if="knjige.length === 0 && pretragaIzvrsena">
      <p>Nema rezultata pretrage.</p>
    </div>
    <div v-else>
      <h3>Rezultati pretrage:</h3>
      <ul>
        <li v-for="knjiga in knjige" :key="knjiga.ISBN">
          <img :src="knjiga.naslovnaFotografija" :alt="knjiga.naslov" />
          <h4>{{ knjiga.naslov }}</h4>
          <p>Broj strana: {{ knjiga.brojStrana }}</p>
          <p>Opis: {{ knjiga.opis }}</p>
          <p>Žanr: {{ knjiga.zanr }}</p>
          <p>Ocena: {{ knjiga.ocena }}</p>
          <router-link :to="'/recenzije/' + knjiga.naslov">Pregledaj recenzije za knjigu</router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue';
import Registracija from '@/components/Registracija.vue';
import Login from '@/components/Login.vue';
import fetch from 'node-fetch';

export default {
  name: 'HomeView',
  components: {
    HelloWorld,
    Registracija,
    Login,
  },
  data() {
    return {
      naslov: '',
      knjige: [],
      pretragaIzvrsena: false,
      ime: '',
      prezime: '',
      profil: null,
      police: [],
  
      recenzije: [], 
      email: '', 
    };
  },
  methods: {
    pretraziKnjige() {
      fetch(`http://localhost:7070/api/knjige/pretraga?naslov=${encodeURIComponent(this.naslov)}`)
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to search books');
          }
        })
        .then((data) => {
          this.knjige = data;
          this.pretragaIzvrsena = true;
        })
        .catch((error) => {
          console.error(error);
          this.knjige = [];
          this.pretragaIzvrsena = true;
        });
    },
    prikaziProfil() {
      const url = `http://localhost:7070/api/korisnici/pretraga-po-imenu?ime=${encodeURIComponent(
        this.ime
      )}&prezime=${encodeURIComponent(this.prezime)}`;

      fetch(url)
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch profile');
          }
        })
        .then((data) => {
          if (data.length > 0) {
            this.profil = data[0]; // Prikazujemo samo prvi pronađeni profil
            this.getPoliceKorisnika(this.profil.korisnikId);
          } else {
            this.profil = null;
            this.police = [];
            console.log('Profil nije pronađen');
          }
        })
        .catch((error) => {
          console.error(error);
          this.profil = null;
          this.police = [];
        });
    },
    pregledajSveKnjige() {
      window.location.href = '/sveknjige';
    },
    pregledajRecenzije() {
      this.$router.push('/sverecenzije');
    },
    pregledajSveZanrove() {
      this.$router.push('/svi-zanrovi');
    },
    posaljiZahtev() {
      this.$router.push('/zahtevi');
    },
    
    getPoliceKorisnika(korisnikId) {
      fetch(`http://localhost:7070/api/korisnici/vrati-police/1`)
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch user shelves');
          }
        })
        .then((data) => {
          this.police = data;
        })
        .catch((error) => {
          console.error(error);
          this.police = [];
        });
    },
  },
};
</script>

<style>
@import '@/assets/stil.css';
.button-table {
  margin-right: 50px;
}
</style>