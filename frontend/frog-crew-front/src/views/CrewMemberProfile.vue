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
import { ref, onMounted } from 'vue';
import axios from 'axios';

const profile = ref({});
const loading = ref(true);
const error = ref(null);

// Function to fetch the crew member profile from the backend
async function fetchCrewMemberProfile(id) {
  try {
    // Send a GET request to fetch the profile by userId
    const response = await axios.get(`http://localhost:80/crewMember/${id}`);
    if (response.data.success) {
      return response.data.data; // Return the profile data
    } else {
      throw new Error(response.data.message || 'Failed to fetch profile');
    }
  } catch (err) {
    throw new Error(err.response?.data?.message || 'Error fetching profile');
  }
}

// When the component is mounted, load the profile data
onMounted(async () => {
  try {
    // Retrieve userId from localStorage (saved during login)
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('No logged-in user ID found!');
    }

    // Fetch the profile using the userId
    profile.value = await fetchCrewMemberProfile(userId);
  } catch (err) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
});
</script>

<script>
export const ProfileItem = {
  props: ['label', 'value'],
  template: `
    <div>
      <label class="block text-sm font-semibold text-gray-700">{{ label }}</label>
      <p class="text-gray-900">{{ value }}</p>
    </div>
  `,
};
</script>
