<template>
    <div>
      <h2>Dodavanje knjige na policu</h2>
  
      <select v-model="policaId">
        <option v-for="polica in police" :value="polica.id">{{ polica.naziv }}</option>
      </select>
  
      <input type="text" v-model="knjigaNaslov" placeholder="Naslov knjige">
  
      <button @click="dodajKnjiguNaPolicu">Dodaj knjigu</button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        policaId: null,
        knjigaNaslov: '',
      };
    },
  
    methods: {
      dodajKnjiguNaPolicu() {
        fetch('/api/dodaj-knjigu-na-policu?policaId=' + this.policaId + '&knjigaNaslov=' + this.knjigaNaslov, { method: 'POST' })
          .then(response => {
            if (response.ok) {
              // Osveži prikaz polica u roditeljskoj komponenti
              this.$emit('osvezi-police');
              this.policaId = null;
              this.knjigaNaslov = '';
            } else {
              console.error('Greška prilikom dodavanja knjige na policu:', response.status);
            }
          })
          .catch(error => {
            console.error('Greška prilikom dodavanja knjige na policu:', error);
          });
      },
    },
  };
  </script>