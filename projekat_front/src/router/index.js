import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import GetSveKnjige from '../views/GetSveKnjige.vue';
import GetSveRecenzije from '../views/GetSveRecenzije.vue';
import GetSviZanrovi from '../views/GetSviZanrovi.vue';
import GetSviProfiliKorisnika from '../views/GetSviProfiliKorisnika.vue';
import GetPretragaKnjigePoNaslovu from '../views/GetPretragaKnjigePoNaslovu.vue';
import Registracija from '../components/Registracija.vue';
import Login from '../components/Login.vue';
import Logout from '../components/Logout.vue';
import Citalac from '../views/Citalac.vue';
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
{ //radi
  path: '/sveknjige',
  name: 'GetSveKnjige',
  component: GetSveKnjige
},
{ 
  path: '/recenzije/:naslov',
  name: 'GetSveRecenzije',
  component: GetSveRecenzije
},
{ //radi
  path: '/svi-zanrovi',
  name: 'GetSviZanrovi',
  component: GetSviZanrovi
},
{
  path: '/prikaz-profila/:id',
  name: 'GetSviProfiliKorisnika',
  component: GetSviProfiliKorisnika
},
{
  path: '/pretrazi-knjige',
  name: 'PretraziKnjige',
  component: GetPretragaKnjigePoNaslovu
},
{ //radi
  path: '/registracija',
  name: 'Registracija',
  component: Registracija
},
{ //radi
  path: '/login',
  name: 'Login',
  component: Login
},
{ //radi
  path: '/logout',
  name: 'Logout',
  component: Logout
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/citalac',
  name: 'Citalac',
  component: Citalac
}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


export default router
