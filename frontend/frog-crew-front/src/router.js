import { createRouter, createWebHistory } from 'vue-router'
import CrewMemberRegistration from './views/CrewMemberRegistration.vue'
import CrewMemberLogin from './views/CrewMemberLogin.vue'
import CrewMemberProfile from './views/CrewMemberProfile.vue'
import GameSchedule from './views/GameSchedule.vue'
import CrewList from './views/CrewList.vue'
import ManageCrewMembers from './views/ManageCrewMembers.vue'
import InviteCrewMember from './views/InviteCrewMember.vue'
import CreateGameSchedule from './views/CreateGameSchedule.vue'
import AdminAddsGames from './views/GameScheduleManagement.vue'
import AdminSchedulesCrew from './views/AdminSchedulesCrew.vue'

const routes = [
  { path: '/', component: CrewMemberRegistration },
  { path: '/crew-member-login', component: CrewMemberLogin },
  { path: '/crew-member-profile', component: CrewMemberProfile },
  {
    path: '/crew-member-profile/:userId',
    name: 'CrewMemberProfile',
    component: CrewMemberProfile,
    props: true, // Pass userId as a prop
  },
  { path: '/game-schedule', component: GameSchedule },
  { path: '/crew-list', component: CrewList },
  { path: '/manage-crew-members', component: ManageCrewMembers },
  { path: '/invite-crew-member', component: InviteCrewMember },
  { path: '/create-game-schedule', component: CreateGameSchedule },
  { path: '/add-games', component: AdminAddsGames, name: 'AdminAddsGames' },
  { path: '/schedule-crew', component: AdminSchedulesCrew, name: 'AdminSchedulesCrew' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
