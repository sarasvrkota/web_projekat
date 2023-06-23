<template>
    <div>
      <input type="text" v-model="imePolice" />
      <button @click="dodajNovuPolicu">Dodaj policu</button>
      <p v-if="policaPostoji">Polica već postoji!</p>
    </div>
  </template>
  
  
  <script>
  export default {
    data() {
      return {
        imePolice: '',
        policaPostoji: false
      };
    },
    methods: {
        dodajNovuPolicu() {
           const naziv = this.imePolice;

    fetch(`http://localhost:7070/api/korisnici/dodavanje-polica?naziv=${naziv}`, {
      method: 'POST',
      credentials: 'include'
    })
    .then(response => {
      if (response.ok) {
        this.$router.push('citalac');
      } else {
        throw new Error('Neuspešno dodavanje nove police!');
      }
    })
    .catch(error => {
      console.error(error);
      this.policaPostoji = true;
    });
  }

    }
  };
  </script>
  
  <style>
  .error-message {
    color: red;
    margin-top: 10px;
  }
  </style>