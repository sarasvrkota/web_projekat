<template>
  <div class="pre-reg">
    <div class="container4">
      <section class="register-section">
        <form>
          <div class="row7">
            <h2>Registracija</h2>
          </div>
          <div class="row8">
            <input type="text" placeholder="name" v-model="ime">
          </div>
          <div class="row9">
            <input type="text" placeholder="surname" v-model="prezime">
          </div>
          <div class="row10">
            <input type="text" placeholder="username" v-model="korisnickoIme">
          </div>
          <div class="row11">
            <input type="email" placeholder="email" v-model="mail">
          </div>
          <div class="row12">
            <input type="password" placeholder="password" v-model="lozinka">
          </div>
          <div class="row13">
            <input type="password" placeholder="repeat password" v-model="potrvdaLozinke">
          </div>
          <div class="row14">
          <button @click="submit">Registruj se</button>
        </div>
      </form>
    </section>
  </div>
</div>
</template>

<script>
import axios from 'axios';

export default {
name: 'RegisterSection',
data() {
  return {
    ime: '',
    prezime: '',
    korisnickoIme: '',
    mail: '',
    lozinka: '',
    potrvdaLozinke: ''
  };
},
methods: {
  register() {
    const payload = {
      ime: this.ime,
      prezime: this.prezime,
      korisnickoIme: this.korisnickoIme,
      mail: this.mail,
      lozinka: this.lozinka,
      potrvdaLozinke: this.potrvdaLozinke
    };

    axios
      .post("http://localhost:7070/api/korisnici/registracija", payload)
      .then((res) => {
        console.log(res);
        // Emit the 'register' event upon successful registration
        this.$emit('/login');
      })
      .catch((err) => {
        console.log(err);
        alert("Something went wrong!");
      });
  }
}
}
</script>