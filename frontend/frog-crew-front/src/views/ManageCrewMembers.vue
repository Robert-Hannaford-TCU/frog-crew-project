<template>
    <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Manage Crew Members</h1>
  
      <div v-if="crewMembers.length === 0" class="text-gray-500">
        No Crew Members registered in the system.
      </div>
  
      <div v-else>
        <!-- Filtering Options -->
        <div class="flex justify-between items-center mb-4">
          <input
            v-model="search"
            type="text"
            placeholder="Search by name..."
            class="w-1/3 px-3 py-2 border rounded-xl"
          />
          <select v-model="sortOption" @change="sortCrewMembers" class="px-3 py-2 border rounded-xl">
            <option value="role">Sort by Role</option>
            <option value="firstName">Sort by First Name</option>
            <option value="lastName">Sort by Last Name</option>
            <option value="availability">Sort by Availability</option>
          </select>
        </div>
  
        <!-- Crew Members List -->
        <ul>
          <li v-for="member in filteredCrewMembers" :key="member.id" class="border-b py-3">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="font-bold text-lg">
                  {{ member.firstName }} {{ member.lastName }} - {{ member.role }}
                </h2>
                <p>Availability: {{ member.availabilityStatus }}</p>
                <p>Experience: {{ member.experienceLevel }}</p>
              </div>
              <button
                class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
                @click="viewProfile(member.id)"
              >
                View Profile
              </button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import axios from 'axios'
  
  const crewMembers = ref([])
  const search = ref('')
  const sortOption = ref('role')
  
  // Fetch crew members from backend
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:80/crewMember') // Adjust endpoint as needed
      crewMembers.value = response.data.data
    } catch (error) {
      console.error('Error fetching crew members:', error)
    }
  })
  
  // Filter and sort crew members
  const filteredCrewMembers = computed(() =>
    crewMembers.value
      .filter(member =>
        `${member.firstName} ${member.lastName}`.toLowerCase().includes(search.value.toLowerCase())
      )
      .sort((a, b) => {
        if (sortOption.value === 'role') return a.role.localeCompare(b.role)
        if (sortOption.value === 'firstName') return a.firstName.localeCompare(b.firstName)
        if (sortOption.value === 'lastName') return a.lastName.localeCompare(b.lastName)
        if (sortOption.value === 'availability') return a.availabilityStatus.localeCompare(b.availabilityStatus)
      })
  )
  
  // Navigate to profile view
  function viewProfile(memberId) {
    console.log('Viewing profile for Crew Member ID:', memberId)
    // Implement further logic, e.g., redirect to /crew-member-profile/:id
  }
  </script>
  