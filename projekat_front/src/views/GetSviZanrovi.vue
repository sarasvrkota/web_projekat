<template>
    <div>
      <h1>Svi žanrovi</h1>
      <ul>
        <li v-for="zanr in zanrovi" :key="zanr.naziv">
          <h2>{{ zanr.naziv }}</h2>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'GetSviZanrovi',
    data() {
      return {
        zanrovi: []
      };
    },
    mounted() {
      this.pregledajSveZanrove();
    },
    methods: {
      pregledajSveZanrove() {
        fetch('http://localhost:7070/api/zanrovi/svi-zanrovi', {
          method: 'GET',
          headers: {
            Accept: 'application/json',
            'Content-type': 'application/json',
          }
        })
          .then(response => response.json())
          .then(data => {
            this.zanrovi = data;
          })
          .catch(error => {
            console.error(error);
          });
      }
    }
  };
  </script>
  