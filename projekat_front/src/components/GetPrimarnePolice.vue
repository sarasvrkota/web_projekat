<template>
    <div>
      <h2>Primarne police</h2>
      <table>
        <thead>
          <tr>
            <th>Naziv</th>
          
          </tr>
        </thead>
        <tbody>
          <tr v-for="polica in primarnePolice" :key="polica.id">
            <td>{{ polica.naziv }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        primarnePolice: [], // Inicijalno prazan niz polica
      };
    },
    mounted() {
      this.getPrimarnePolice();
    },
    methods: {
      getPrimarnePolice() {
        // Poziv API-ja za dohvatanje primarnih polica
        // Možete koristiti Axios ili neku drugu biblioteku za HTTP zahteva
        axios.get('/primarne-police')
          .then(response => {
            this.primarnePolice = response.data;
          })
          .catch(error => {
            console.log(error);
          });
      },
    },
  };
  </script>