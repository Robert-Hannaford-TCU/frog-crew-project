import { createRouter, createWebHistory } from 'vue-router'
import CrewMemberRegistration from './CrewMemberRegistration.vue'
import CrewMemberLogin from './CrewMemberLogin.vue'
import CrewMemberProfile from './CrewMemberProfile.vue'

const routes = [
  { path: '/', component: CrewMemberRegistration }, // Now part of routing
  { path: '/crew-member-login', component: CrewMemberLogin },
  { path: '/crew-member-profile', component: CrewMemberProfile }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

