<template>
  <div>
    <h1>Recenzije za knjigu: {{ naslov }}</h1>
    <ul>
      <li v-for="recenzija in recenzije" :key="recenzija.id">
        <h2>Ocena: {{ recenzija.ocena }}</h2>
        <p>Tekst: {{ recenzija.tekst }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'GetRecenzijaZaKnjigu',
  data() {
    return {
      naslov: '',
      recenzije: []
    };
  },
  mounted() {
    this.pregledajRecenzijeZaKnjigu();
  },
  methods: {
    pregledajRecenzijeZaKnjigu() {
      const naslov = this.$route.params.naslov;
      fetch(`http://localhost:7070/api/stavkapolice/recenzije/${encodeURIComponent(naslov)}`)
        .then(response => response.json())
        .then(data => {
          this.naslov = naslov;
          this.recenzije = data;
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>