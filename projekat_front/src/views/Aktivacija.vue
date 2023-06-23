<template>
    <div>
      <div>
        <h2>Podnošenje zahteva</h2>
        <div v-if="!prijavljeniKorisnik">
          <input type="email" v-model="zahtev.email" placeholder="Unesite svoj email" />
          <input type="text" v-model="zahtev.telefon" placeholder="Unesite svoj telefon" />
          <textarea v-model="zahtev.poruka" placeholder="Unesite svoju poruku"></textarea>
          <button @click="posaljiZahtev">Podnesi zahtev</button>
        </div>
        <div v-else>
          <p>Već ste ulogovani kao {{ prijavljeniKorisnik.ime }} {{ prijavljeniKorisnik.prezime }}.</p>
        </div>
        <div v-if="!prijavljeniKorisnik">Nema korisnika</div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        zahtev: {
          email: '',
          telefon: '',
          poruka: '',
        },
        prijavljeniKorisnik: null,
      };
    },
    methods: {
      posaljiZahtev() {
        if (this.zahtev.email) {
          const zahtev = {
            email: this.zahtev.email,
            telefon: this.zahtev.telefon,
            poruka: this.zahtev.poruka,
          };
  
          fetch('http://localhost:7070/api/zahtevi/posalji-zahtev', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(zahtev),
          })
            .then((response) => {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error('Failed to submit request');
              }
            })
            .then((data) => {
              console.log(data);
              alert('Zahtev je uspešno poslat!');
              this.zahtev.email = ''; 
              this.zahtev.telefon = ''; 
              this.zahtev.poruka = ''; 
            })
            .catch((error) => {
              console.error(error.message);
              alert('Došlo je do greške prilikom slanja zahteva.');
            });
        } else {
          alert('Unesite svoj email pre slanja zahteva.');
        }
      },
    },
  };
  </script>
  