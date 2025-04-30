<template>
  <div class="max-w-xl mx-auto mt-10 p-6 bg-white rounded-2xl shadow-md">
    <h1 class="text-2xl font-bold mb-4">Crew Member Profile</h1>

    <div v-if="loading" class="text-gray-600">Loading...</div>
    <div v-else-if="error" class="text-red-500">Error: {{ error }}</div>
    <div v-else>
      <div class="space-y-4">
        <p class="text-gray-900"><strong>First Name:</strong> {{ profile.firstName }}</p>
        <p class="text-gray-900"><strong>Last Name:</strong> {{ profile.lastName }}</p>
        <p class="text-gray-900"><strong>Email:</strong> {{ profile.email }}</p>
        <p class="text-gray-900"><strong>Phone Number:</strong> {{ profile.phoneNumber }}</p>
        <p class="text-gray-900"><strong>Role:</strong> {{ profile.role }}</p>
        <p class="text-gray-900"><strong>Qualified Position:</strong> {{ profile.qualifiedPosition.join(', ') }}</p>
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

// Fetch crew member profile
async function fetchCrewMemberProfile(id) {
  try {
    console.log(`Fetching profile for Crew Member ID: ${id}`); // Debug ID

    const response = await axios.get(`http://localhost:80/crewMember/${id}`, {
      auth: {
        username: localStorage.getItem('email'), // Saved email
        password: localStorage.getItem('password'), // Saved password
      },
    });

    console.log('Profile Fetch Response:', response); // Log backend response

    // Check if the backend response indicates success
    if (response.data.flag) { // Use flag to verify success
      console.log('Profile Data:', response.data.data); // Log profile data
      return response.data.data; // Return profile data
    } else {
      throw new Error(response.data.message || 'Failed to fetch profile');
    }
  } catch (err) {
    console.error('Error in fetchCrewMemberProfile:', err.message); // Log error details
    throw new Error(err.message || 'Error fetching profile');
  }
}

// Load the user's profile on page mount
onMounted(async () => {
  try {
    const userId = localStorage.getItem('userId'); // Retrieve user ID from localStorage
    if (!userId) {
      throw new Error('No logged-in user ID found!');
    }

    // Fetch the profile using the userId
    console.log(`Fetching profile for userId: ${userId}`); // Debug userId
    profile.value = await fetchCrewMemberProfile(userId);
  } catch (err) {
    console.error('Error loading profile:', err.message); // Log error message
    error.value = err.message || 'Failed to load profile. Please try again.';
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
