<template>
    <div>
      <h1>Pretraga knjiga po naslovu</h1>
      <input type="text" v-model="pretraga" placeholder="Unesite naslov knjige" />
      <button @click="pretraziKnjige">Pretraži knjige</button>
  
      <ul v-if="rezultati.length > 0">
        <li v-for="knjiga in rezultati" :key="knjiga.naslov">
          <h2>Naslov: {{ knjiga.naslov }}</h2>
          <p>Broj strana: {{ knjiga.brojStrana }}</p>
          <p>Datum objavljivanja: {{ knjiga.datumObjavljivanja }}</p>
          <p>Opis: {{ knjiga.opis }}</p>
        </li>
      </ul>
  
      <p v-else>Nema rezultata pretrage.</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'GetKnjigePoNaslovu',
    data() {
      return {
        pretraga: '',
        rezultati: [],
      };
    },
    methods: {
      pretraziKnjige() {
  axios
    .get('http://localhost:7070/pretraga', {
      params: {
        naslov: this.pretraga
      }
    })
    .then(response => {
      this.rezultati = response.data;
    })
    .catch(error => {
      console.error(error);
    });
},
    },
  };
  </script>