<template>
  <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Delete Crew Member</h1>

    <!-- Display a message if no crew members are registered -->
    <div v-if="crewMembers.length === 0" class="text-gray-500">
      No crew members registered in the system.
    </div>

    <!-- Display the list of crew members -->
    <div v-else>
      <ul>
        <li v-for="member in crewMembers" :key="member.id" class="border-b py-3">
          <div class="flex justify-between items-center">
            <div>
              <h2 class="font-bold text-lg">{{ member.firstName }} {{ member.lastName }}</h2>
              <p>Role: {{ member.role }}</p>
              <p>Availability: {{ member.availabilityStatus }}</p>
            </div>
            <!-- Button to open confirmation modal for deletion -->
            <button
              class="bg-red-600 text-white px-4 py-2 rounded-xl hover:bg-red-700"
              @click="confirmDelete(member)"
            >
              Delete
            </button>
          </div>
        </li>
      </ul>
    </div>

    <!-- Confirmation modal for deletion -->
    <div v-if="showModal" class="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center">
      <div class="bg-white p-6 rounded-lg shadow-xl max-w-md">
        <h2 class="text-lg font-bold mb-4">Confirm Deletion</h2>
        <p>
          Are you sure you want to delete {{ selectedMember.firstName }} {{ selectedMember.lastName }}?
        </p>
        <div class="flex justify-between mt-6">
          <!-- Button to cancel deletion -->
          <button class="bg-gray-300 px-4 py-2 rounded-lg" @click="cancelDelete">
            Cancel
          </button>
          <!-- Button to confirm deletion -->
          <button class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700" @click="deleteCrewMember">
            Confirm
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Reactive variables to store crew members, the selected member, and modal visibility
const crewMembers = ref([])
const selectedMember = ref({})
const showModal = ref(false)

// Fetch crew members from backend when the component is mounted
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:80/crewMember') // Adjust endpoint if needed
    crewMembers.value = response.data.data
  } catch (error) {
    console.error('Error fetching crew members:', error)
  }
})

// Open confirmation modal for selected crew member
function confirmDelete(member) {
  selectedMember.value = member
  showModal.value = true
}

// Cancel the deletion process and close the modal
function cancelDelete() {
  selectedMember.value = {}
  showModal.value = false
}

// Delete the selected crew member from the backend
async function deleteCrewMember() {
  try {
    const response = await axios.delete(`http://localhost:80/crewMember/${selectedMember.value.id}`)
    if (response.status === 200) {
      // Remove the deleted member from the list
      crewMembers.value = crewMembers.value.filter(member => member.id !== selectedMember.value.id)
      showModal.value = false
      alert('Crew member deleted successfully.')
    } else {
      alert('Failed to delete the crew member.')
    }
  } catch (error) {
    console.error('Error deleting crew member:', error)
    alert('An error occurred while deleting the crew member.')
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
