<template>
  <div>
    <h2>Moje Police</h2>
    <button @click="logout">Logout</button>

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
        <td>
          <button class="btn" @click="azurirajProfil">Ažuriraj profil</button>
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
            <button v-if="!polica.primarna" @click="obrisiPolicu(polica.naziv)">Obriši policu</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      police: []
    };
  },
  mounted() {
    this.getPolicePrijavljenogKorisnika();
  },
  methods: {
    getPolicePrijavljenogKorisnika() {
      fetch('http://localhost:7070/api/korisnici/primarne-police', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch data');
          }
        })
        .then(data => {
          this.police = data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    logout() {
      fetch('http://localhost:7070/api/korisnici/logout', {
        method: 'POST',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({ name: 'home' });
          } else {
            throw new Error('Failed to logout');
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    dodajNovuPolicu() {
      window.location.href = "/dodavanje-polica";
    },
    dodajKnjiguNaPolicu() {
      window.location.href = "/dodaj-knjigu-na-policu";
    },
    obrisiKnjiguSaPolice() {
      this.$router.push({ name: 'obrisi-knjigu-sa-police' });
    },
    azurirajProfil() {
      fetch('http://localhost:7070/api/korisnici/azuriraj-profil', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(this.korisnik) // Pretpostavljamo da imate podatke korisnika u data objektu sa imenom 'korisnik'
      })
        .then(response => {
          if (response.ok) {
            // Uspesno azuriranje profila
            console.log('Profil uspešno ažuriran!');
            // Možete dodati dodatne akcije koje želite izvršiti nakon uspešnog ažuriranja profila
          } else {
            throw new Error('Failed to update profile');
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
    azurirajRecenziju(recenzijaId) {
      this.$router.push({ name: 'azuriraj-recenziju', params: { id: recenzijaId } });
    },

    obrisiPolicu(naziv) {
      fetch(`http://localhost:7070/api/obrisi-policu-naziv?nazivPolice=${naziv}`, {
        method: 'DELETE',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            this.getPolicePrijavljenogKorisnika();
            //window.location.reload();
          } else {
            throw new Error('Failed to delete policu');
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

    azurirajRecenziju(recenzijaId) {
      this.$router.push({ name: 'azuriraj-recenziju', params: { recenzijaId } });
    }
  }
};
</script>