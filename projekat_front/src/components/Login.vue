<template>
  <div>
    <h2>Login</h2>
    <div>
      <input type="text" v-model="email" placeholder="Email" />
      <input type="password" v-model="password" placeholder="Password" />
      <button @click="login">Login</button>
    </div>
    <div v-if="loginError">
      <p>{{ loginError }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      loginError: ''
    };
  },
  methods: {
    login() {
      const loginDto = {
        mail: this.email,
        lozinka: this.password
      };

      fetch('http://localhost:7070/api/korisnici/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginDto),
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to login');
          }
        })
        .then(data => {
          localStorage.setItem('sessionToken', data.token);
          this.redirectToProfile();
        })
        .catch(error => {
          this.loginError = 'Failed to login. Please try again.';
          console.error(error);
        });
    },
    redirectToProfile() {
      // Get the logged-in user's role from the server
      fetch('http://localhost:7070/api/korisnici/getUserRole', {
        method: 'GET',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            return response.text(); // Read the response as text
          } else {
            throw new Error('Failed to get user role');
          }
        })
        .then(data => {
          const role = data.trim(); // Remove leading/trailing whitespace from the response
          if (role === 'CITALAC') {
            this.$router.push(  '/citalac' );
          } else if (role === 'AUTOR') {
            this.$router.push( '/autor' );
          } else if (role === 'ADMINISTRATOR') {
            this.$router.push(  '/administrator' );
          } else {
            throw new Error('Unknown user role');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>
<style>
.login-button {
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.login-button:hover {
  background-color: #3ba572;
}

.login-button:active {
  background-color: #359e67;
}

.login-button:focus {
  outline: none;
}
</style>