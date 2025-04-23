<template>
    <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Crew List</h1>
  
      <div v-if="crewMembers.length === 0" class="text-gray-500">
        No crew members have been assigned yet.
      </div>
  
      <ul v-else>
        <li v-for="member in crewMembers" :key="member.id" class="border-b py-3">
          <h2 class="font-bold text-lg">{{ member.name }} - {{ member.position }}</h2>
          <p>Report Time: {{ member.reportTime }}</p>
          <p>Report Location: {{ member.reportLocation }}</p>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const crewMembers = ref([])
  
  // Fetch crew list for a specific game
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:8080/crewList') // Adjust endpoint as needed
      crewMembers.value = response.data.data
    } catch (error) {
      console.error('Error fetching crew list:', error)
    }
  })
  </script>
  