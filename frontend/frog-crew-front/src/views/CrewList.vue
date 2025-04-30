<template>
  <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Crew List</h1>

    <!-- No Crew Members Found -->
    <div v-if="crewMembers.length === 0" class="text-gray-500">
      No crew members have been registered in the system.
    </div>

    <!-- Crew Member List -->
    <ul v-else>
      <li v-for="member in crewMembers" :key="member.userId" class="border-b py-3">
        <h2 class="font-bold text-lg">{{ member.name }} - {{ member.role }}</h2>
        <p>Email: {{ member.email }}</p>
        <p>Qualified Position: {{ member.qualifiedPosition }}</p>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Holds the list of crew members
const crewMembers = ref([])

// Fetch all crew members when the component is mounted
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:80/crewMember') // Adjust backend URL if needed
    if (response.data.success) {
      crewMembers.value = response.data.data // Populate crew member list
    } else {
      console.warn('No crew members found:', response.data.message)
    }
  } catch (error) {
    console.error('Error fetching crew member list:', error)
  }
})
</script>
