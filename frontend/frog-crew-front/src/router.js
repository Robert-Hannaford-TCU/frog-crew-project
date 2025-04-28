import { createRouter, createWebHistory } from 'vue-router'
import CrewMemberRegistration from './views/CrewMemberRegistration.vue'
import CrewMemberLogin from './views/CrewMemberLogin.vue'
import CrewMemberProfile from './views/CrewMemberProfile.vue'
import GameSchedule from './views/GameSchedule.vue'
import CrewList from './views/CrewList.vue'
import SubmitAvailability from './views/SubmitAvailability.vue'
import ManageCrewMembers from './views/ManageCrewMembers.vue'
import InviteCrewMember from './views/InviteCrewMember.vue'
import DeleteCrewMember from './views/DeleteCrewMember.vue'

const routes = [
  { path: '/', component: CrewMemberRegistration },
  { path: '/crew-member-login', component: CrewMemberLogin },
  { path: '/crew-member-profile', component: CrewMemberProfile },
  { path: '/game-schedule', component: GameSchedule },
  { path: '/crew-list', component: CrewList },
  { path: '/submit-availability', component: SubmitAvailability },
  { path: '/manage-crew-members', component: ManageCrewMembers },
  { path: '/invite-crew-member', component: InviteCrewMember },
  { path: '/delete-crew-member', component: DeleteCrewMember }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
