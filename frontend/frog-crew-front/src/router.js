import { createRouter, createWebHistory } from 'vue-router'
import CrewMemberRegistration from './views/CrewMemberRegistration.vue'
import CrewMemberLogin from './views/CrewMemberLogin.vue'
import CrewMemberProfile from './views/CrewMemberProfile.vue'
import GameSchedule from './views/GameSchedule.vue'
import CrewList from './views/CrewList.vue'

const routes = [
  { path: '/', component: CrewMemberRegistration },
  { path: '/crew-member-login', component: CrewMemberLogin },
  { path: '/crew-member-profile', component: CrewMemberProfile },
  { path: '/game-schedule', component: GameSchedule },
  { path: '/crew-list', component: CrewList }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
