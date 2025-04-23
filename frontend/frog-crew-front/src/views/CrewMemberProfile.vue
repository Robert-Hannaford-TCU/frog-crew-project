<template>
  <div class="max-w-xl mx-auto mt-10 p-6 bg-white rounded-2xl shadow-md">
    <h1 class="text-2xl font-bold mb-4">Crew Member Profile</h1>

    <div v-if="loading" class="text-gray-600">Loading...</div>
    <div v-else-if="error" class="text-red-500">Error: {{ error }}</div>
    <div v-else>
      <div class="space-y-4">
        <ProfileItem label="First Name" :value="profile.firstName" />
        <ProfileItem label="Last Name" :value="profile.lastName" />
        <ProfileItem label="Email" :value="profile.email" />
        <ProfileItem label="Phone Number" :value="profile.phone" />
        <ProfileItem label="Role" :value="profile.role" />
        <ProfileItem label="Qualified Position" :value="profile.qualifiedPosition" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue'

const props = defineProps({
  crewMemberId: {
    type: String,
    required: true
  }
})

const profile = ref({})
const loading = ref(true)
const error = ref(null)

async function fetchCrewMemberProfile(id) {
  await new Promise(resolve => setTimeout(resolve, 500))

  if (id === '1') {
    return {
      firstName: 'Jane',
      lastName: 'Doe',
      email: 'jane.doe@example.com',
      phone: '123-456-7890',
      role: 'Medic',
      qualifiedPosition: 'Lead Medic'
    }
  } else {
    throw new Error('Profile not found')
  }
}

onMounted(async () => {
  try {
    profile.value = await fetchCrewMemberProfile(props.crewMemberId)
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
})
</script>

<script>
export const ProfileItem = {
  props: ['label', 'value'],
  template: `
    <div>
      <label class="block text-sm font-semibold text-gray-700">{{ label }}</label>
      <p class="text-gray-900">{{ value }}</p>
    </div>
  `
}
</script>
