<template>
  <div>
    <h2>Moje Police</h2>
    <LogoutButton />
 
    <router-link to="/azuriraj-profil">
      <button>Ažuriraj profil</button>
    </router-link>

    <table class="button-table">
      <tr>
        <td>
          <button class="btn" @click="dodajNovuPolicu">Dodaj novu policu</button>
        </td>
        <td>
          <button class="btn" @click="dodajKnjiguNaPolicu">Dodaj knjigu na policu</button>
        </td>
        <td>
          <button class="btn" @click="obrisiKnjiguSaPolice">Ukloni knjigu sa police</button>
        </td>
     
  
        
      </tr>
    </table>

    <table>
      <thead>
        <tr>
          <th>Naziv</th>
          <th>Primarna</th>
          <th>Stavke Police</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
  <tr v-for="polica in police" :key="polica.naziv">
    <td>{{ polica.naziv }}</td>
    <td>{{ polica.primarna }}</td>
    <td>
      <ul>
        <li v-for="stavka in polica.stavkaPolice" :key="stavka.recenzija ? stavka.recenzija.id : null">
          <div v-if="stavka.recenzija">
            <strong>Recenzija:</strong>
            <br />
            Ocena: {{ stavka.recenzija.ocena }}
            <br />
            Tekst: {{ stavka.recenzija.tekst }}
            <br />
            Datum recenzije: {{ stavka.recenzija.datumRecenzije }}
            <br />
            <button @click="azurirajRecenziju(stavka.recenzija.id)">Ažuriraj recenziju</button>
          </div>
          <div>
            <strong>Knjiga:</strong>
            <br />
            Naslov: {{ stavka.knjiga.naslov }}
            <br />
            Naslovna fotografija: {{ stavka.knjiga.naslovnaFotografija }}
            <br />
            Datum objavljivanja: {{ stavka.knjiga.datumObjavljivanja }}
            <br />
            Broj strana: {{ stavka.knjiga.brojStrana }}
            <br />
            Opis: {{ stavka.knjiga.opis }}
            <br />
            Ocena: {{ stavka.knjiga.ocena }}
            <br />
            Žanr: {{ stavka.knjiga.zanr.naziv }}
          </div>
        </li>
      </ul>
    </td>
    <td>
      {{ polica.primarna ? 'Da' : 'Ne' }}
    </td>
  </tr>
</tbody>
    </table>
  </div>
</template>
<script>
import axios from 'axios';
import LogoutButton from "@/components/Logout.vue";
export default {
  components: {
    LogoutButton
  },
  data() {
    return {
   
      ime: '',
      prezime: '',
      korisnickoIme: '',
      mail: '',
      lozinka: '',
      datumRodjenja: '',
      profilnaSlika: '',
      opis: '',
      police:[]
    };
  },


  mounted() {
    this.ucitajPrimarnePolice();
  },
  methods: {
    azurirajProfil() {
      this.$router.push('/azuriraj-profil');
    },
    ucitajPrimarnePolice() {
  const korisnikId = localStorage.getItem('korisnikId');
  this.ucitajPodatkeKorisnika();

  if (!korisnikId) {
    // Vrednost korisnikId nije dostupna, prekinuti izvršavanje metode
    return;
  }

  axios.get(`http://localhost:7070/api/korisnici/vrati-police/${korisnikId}`, {
    headers: {
      'Content-Type': 'application/json'
    },
    withCredentials: true
  })
    .then(response => {
      this.primarnePolice = response.data;
    })
    .catch(error => {
      console.error(error);
    });
},
    dodajNovuPolicu() {
      this.$router.push('/dodaj-novu-policu');
    },
    dodajKnjiguNaPolicu() {
      this.$router.push('/dodaj-knjigu-na-policu');
    },
    obrisiKnjiguSaPolice() {
      this.$router.push({ name: 'obrisi-knjigu-sa-police' });
    },
   
    azurirajRecenziju(recenzijaId) {
      this.$router.push({ name: 'azuriraj-recenziju', params: { id: recenzijaId } });
    },
    obrisiPolicu(naziv) {
  const korisnikId = localStorage.getItem('korisnikId');

  if (!korisnikId) {
    // Vrednost korisnikId nije dostupna, prekinuti izvršavanje metode
    return;
  }

  axios.delete(`http://localhost:7070/api/korisnici/obrisi-policu/${korisnikId}/${naziv}`, {
    withCredentials: true
  })
    .then(response => {
      if (response.status === 200) {
        this.ucitajPrimarnePolice();
      } else {
        throw new Error('Failed to delete policu');
      }
    })
    .catch(error => {
      console.error(error);
    });
},
 
    ucitajPodatkeKorisnika() {
      const korisnikId = localStorage.getItem('korisnikId');

      if (!korisnikId) {
        // Vrednost korisnikId nije dostupna, prekinuti izvršavanje metode
        return;
      }

      axios.get(`http://localhost:7070/api/korisnici/${korisnikId}`, {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true
      })
        .then(response => {
          const korisnik = response.data;
          this.ime = korisnik.ime;
          this.prezime = korisnik.prezime;
          this.korisnickoIme = korisnik.korisnickoIme;
          this.mail = korisnik.mail;
          this.datumRodjenja = korisnik.datumRodjenja;
          this.profilnaSlika = korisnik.profilnaSlika;
          this.opis = korisnik.opis;
        })
        .catch(error => {
          console.error('Greška prilikom učitavanja podataka korisnika', error);
        });
    },
    azurirajProfil() {
      // Create an object with the updated user profile data
      const payload = {
        ime: '',
        prezime: '',
        // Add other profile fields as needed
      };

      // Make a fetch request to update the user profile
      fetch("/api/azuriraj-profil", {
        method: "PUT",
        credentials: 'include',
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      })
        .then((res) => {
          if (res.ok) {
            return res.text();
          } else {
            throw new Error("Profile update failed");
          }
        })
        .then((data) => {
          // Handle the response if the profile update is successful
          console.log(data);
          alert("Profil uspešno ažuriran!");
        })
        .catch((error) => {
          // Handle errors if the profile update fails
          console.error(error);
          alert("Došlo je do greške prilikom ažuriranja profila!");
        });
    }
  
  }
};
</script>

<style>
.table-container {
  margin-top: 20px;
}

.button-table {
  margin-bottom: 10px;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th,
.data-table td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.data-table th {
  background-color: #f2f2f2;
}
</style>
