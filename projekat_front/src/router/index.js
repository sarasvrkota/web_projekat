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
import DodajNovuPolicu from '../views/DodajNovuPolicu.vue';
import AzurirajProfil from  '../views/AzurirajProfil.vue';
import Administrator from  '../views/Administrator.vue';
import Autor from  '../views/Autor.vue';
import dodavanjeKnjige from  '../views/dodavanjeKnjige.vue';
import dodavanjeKnjigeAutor from  '../views/dodavanjeKnjigeAutor.vue';
import Aktivacija from  '../views/Aktivacija.vue';
import dodajPolicuAutor from  '../views/dodajPolicuAutor.vue';
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
  name: 'LogoutButton',
  component: Logout
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/citalac',
  name: 'Citalac',
  component: Citalac
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/autor',
  name: 'Autor',
  component: Autor,
  meta: {
    requiresAuth: true, // Dodajte ovu liniju kako biste obezbedili da samo prijavljeni korisnici mogu pristupiti ovoj ruti
    role: 'AUTOR' 
  }
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/administrator',
  name: 'Administrator',
  component: Administrator
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/zahtevi',
  name: 'Aktivacija',
  component: Aktivacija
},


{
  path: '/dodaj-novu-policu',
  name: 'dodaj-novu-policu',
  component: DodajNovuPolicu
},
{
  path: '/dodaj-novu-policu-autor',
  name: 'dodaj-novu-policu-autor',
  component: dodajPolicuAutor
},
{
  path: '/azuriraj-profil',
  name: 'azuriraj-profil',
  component: AzurirajProfil
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/dodavanje-knjige-admin',
  name: 'dodavanje-knjige-admin',
  component: dodavanjeKnjige
},
{ //kada se loginuje prebaci se na ovu putanju
  path: '/dodavanje-knjige-autor',
  name: 'dodavanje-knjige-autor',
  component: dodavanjeKnjigeAutor
},

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


export default router
