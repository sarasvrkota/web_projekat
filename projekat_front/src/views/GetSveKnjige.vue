<template>
    <div>
      <h1>Sve knjige</h1>
      <ul>
        <li v-for="knjiga in knjige" :key="knjiga.ISBN">
          <h2>Naslov: {{ knjiga.naslov }}</h2>
          <p>Broj strana: {{knjiga.brojStrana}}</p>
          <p>Datum objavljivanja: {{knjiga.datumObjavljivanja}}</p>
          <p>Opis: {{knjiga.opis}}</p>
          <p>Ocena: {{knjiga.ocena}}</p>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'GetSveKnjige',
    data() {
      return {
        knjige: []
      };
    },
    mounted() {
      this.pregledajSveKnjige();
    },
    methods: {
      pregledajSveKnjige() {
        fetch('http://localhost:7070/api/knjige/sveknjige', {
          method: 'GET',
          headers: {
            Accept: 'application/json',
            'Content-type': 'application/json',
          }
        })
          .then(response => response.json())
          .then(data => {
            this.knjige = data;
          })
          .catch(error => {
            console.error(error);
          });
      }
    }
  };
  </script>