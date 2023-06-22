<template>
  <div>
    <button @click="logout">Logout</button>
    <p v-if="showLogoutMessage">Izlogovali ste se.</p>
  </div>
</template>

<script>
export default {
  name: 'LogoutButton',
  data() {
    return {
      showLogoutMessage: false
    };
  },
  methods: {
    logout() {
      fetch('http://localhost:7070/api/korisnici/logout', {
        method: 'POST',
        credentials: 'include'
      })
        .then((res) => {
          if (res.ok) {
            localStorage.removeItem('user');
            this.showLogoutMessage = true;
            setTimeout(() => {
              this.showLogoutMessage = false;
              this.$router.push('/');
            }, 2000); // Prikazuje poruku 2 sekunde pre preusmeravanja
          } else {
            throw new Error('Logout failed');
          }
        })
        .catch((err) => {
          console.log(err);
          alert('Logout failed!');
        });
    }
  }
};
</script>