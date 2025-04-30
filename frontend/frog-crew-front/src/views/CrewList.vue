<template>
  <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Crew Members</h1>

    <!-- Loading Indicator -->
    <div v-if="loading" class="text-gray-500">Loading crew members...</div>

    <!-- Error Message -->
    <div v-if="error" class="text-red-500">{{ error }}</div>

    <!-- Crew Members List -->
    <ul v-if="crewMembers.length > 0 && !error">
      <li v-for="member in crewMembers" :key="member.userId" class="border-b py-3">
        <div class="flex justify-between items-center">
          <div>
            <h2 class="font-bold text-lg">{{ member.firstName }} {{ member.lastName }} - {{ member.role }}</h2>
            <p>Email: {{ member.email }}</p>
            <p>Phone Number: {{ member.phoneNumber }}</p>
            <p>Qualified Position: {{ member.qualifiedPosition.join(', ') }}</p>
          </div>
          <!-- Button to open confirmation modal (only for admin) -->
          <button
            v-if="isAdmin"
            class="bg-red-600 text-white px-4 py-2 rounded-xl hover:bg-red-700"
            @click="confirmDelete(member)"
          >
            Delete
          </button>
        </div>
      </li>
    </ul>

    <!-- No Crew Members Found -->
    <div v-else-if="!loading && crewMembers.length === 0" class="text-gray-500">
      No crew members found.
    </div>

    <!-- Confirmation modal for deletion -->
    <div v-if="showModal" class="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center">
      <div class="bg-white p-6 rounded-lg shadow-xl max-w-md">
        <h2 class="text-lg font-bold mb-4">Confirm Deletion</h2>
        <p>
          Are you sure you want to delete {{ selectedMember.firstName }} {{ selectedMember.lastName }} - {{ selectedMember.role }}?
        </p>
        <div class="flex justify-between mt-6">
          <button class="bg-gray-300 px-4 py-2 rounded-lg" @click="cancelDelete">Cancel</button>
          <button class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700" @click="deleteCrewMember">Confirm</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// Store crew member list
const crewMembers = ref([]);
const loading = ref(true);
const error = ref(null);

// State for deletion confirmation modal
const showModal = ref(false);
const selectedMember = ref({});

// Store admin status
const isAdmin = ref(false); // Default to false

// Check if the logged-in user is an admin
onMounted(() => {
  const role = localStorage.getItem('role'); // Retrieve user role from localStorage
  console.log('Stored Role:', role); // Log role for debugging
  isAdmin.value = role === 'ADMIN'; // Set isAdmin to true if role is "admin"
  console.log('Admin Status:', isAdmin.value); // Log admin status
});

// Fetch a specific crew member by ID
async function fetchCrewMemberById(id) {
  try {
    console.log(`Fetching profile for Crew Member ID: ${id}`); // Debug ID

    const response = await axios.get(`http://localhost:80/crewMember/${id}`, {
      auth: {
        username: localStorage.getItem('email'), // Saved email
        password: localStorage.getItem('password'), // Saved password
      },
    });

    console.log(`Profile Fetch Response for ID ${id}:`, response); // Log backend response

    if (response.data.flag) {
      console.log(`Profile Data for ID ${id}:`, response.data.data); // Log profile data
      return response.data.data; // Return profile data
    } else {
      throw new Error(response.data.message || `Failed to fetch profile for ID ${id}`);
    }
  } catch (err) {
    console.error(`Error in fetchCrewMemberById for ID ${id}:`, err.message); // Log error details
    return null; // Return null to handle missing members
  }
}

// Fetch multiple crew members
onMounted(async () => {
  try {
    // Predefined list of user IDs to fetch
    const userIds = [1, 2, 3, 4, 5];
    const members = [];

    for (const id of userIds) {
      const member = await fetchCrewMemberById(id);
      if (member) {
        members.push(member); // Add the fetched member to the list
      }
    }

    crewMembers.value = members; // Update the crew members list
  } catch (err) {
    error.value = 'Failed to fetch crew members. Please try again later.';
    console.error('Error fetching crew members:', err);
  } finally {
    loading.value = false; // Stop the loading indicator
  }
});

// Open the confirmation modal for deletion
function confirmDelete(member) {
  selectedMember.value = member; // Store the selected member for deletion
  showModal.value = true; // Display the confirmation modal
}

// Cancel the deletion and close the modal
function cancelDelete() {
  selectedMember.value = {}; // Clear the selected member
  showModal.value = false; // Close the modal
}

// Delete the selected crew member
async function deleteCrewMember() {
  try {
    const response = await axios.delete(`http://localhost:80/crewMember/${selectedMember.value.userId}`, {
      auth: {
        username: localStorage.getItem('email'), // Saved email
        password: localStorage.getItem('password'), // Saved password
      },
    });

    console.log(`Delete Response for ID ${selectedMember.value.userId}:`, response); // Log deletion response

    if (response.status === 200 && response.data.flag) {
      // Remove the deleted member from the list
      crewMembers.value = crewMembers.value.filter(member => member.userId !== selectedMember.value.userId);
      alert(`${selectedMember.value.firstName} ${selectedMember.value.lastName} has been deleted.`);
    } else {
      throw new Error(response.data.message || `Failed to delete Crew Member ID ${selectedMember.value.userId}`);
    }
  } catch (err) {
    console.error(`Error deleting Crew Member ID ${selectedMember.value.userId}:`, err.message);
    alert(`An error occurred while deleting ${selectedMember.value.firstName} ${selectedMember.value.lastName}.`);
  } finally {
    cancelDelete(); // Always close the modal after the operation
  }
}
</script>

<style scoped>
.fixed {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.bg-opacity-50 {
  background-color: rgba(0, 0, 0, 0.5);
}
</style>
