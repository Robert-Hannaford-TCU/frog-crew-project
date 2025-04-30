<template>
    <div class="max-w-4xl mx-auto p-6 bg-white rounded-xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Manage Game Schedule</h1>
  
      <!-- Select Game Schedule -->
      <div v-if="gameSchedules.length > 0" class="mb-4">
        <label for="schedule" class="block font-medium mb-1">Select Game Schedule</label>
        <select
          id="schedule"
          v-model="selectedSchedule"
          @change="fetchScheduledGames"
          class="w-full px-3 py-2 border rounded-xl"
        >
          <option value="" disabled>Select a schedule</option>
          <option v-for="schedule in gameSchedules" :key="schedule.id" :value="schedule.id">
            {{ schedule.name }} ({{ schedule.season }})
          </option>
        </select>
      </div>
  
      <!-- Display Current Games -->
      <!-- Display Current Games or No Games Message -->
      <div class="mt-6">
      <h2 class="text-xl font-medium mb-2">Current Scheduled Games</h2>
      <div v-if="scheduledGames.length > 0">
        <ul class="list-disc ml-5">
        <li v-for="game in scheduledGames" :key="game.id">
            {{ game.sportType }} - {{ game.date }} at {{ game.venue }}
        </li>
        </ul>
      </div>
      <div v-else class="text-gray-500">
        No games are currently scheduled for this game schedule.
    </div>
    </div>

  
      <!-- Add New Game Form -->
      <div class="mt-6">
        <h2 class="text-xl font-medium mb-4">Add New Game</h2>
        <form @submit.prevent="handleAddGame">
          <div v-for="field in fields" :key="field.name" class="mb-4">
            <label :for="field.name" class="block font-medium mb-1">{{ field.label }}</label>
            <input
              :id="field.name"
              v-model="newGame[field.name]"
              :type="field.type"
              :placeholder="field.label"
              class="w-full px-3 py-2 border rounded-xl"
            />
          </div>
  
          <!-- Action Buttons -->
          <div class="flex justify-between mt-6">
            <button
              type="submit"
              class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
            >
              Add Game
            </button>
            <button
              type="button"
              class="bg-gray-400 text-white px-4 py-2 rounded-xl hover:bg-gray-500"
              @click="saveDraft"
            >
              Save Draft
            </button>
          </div>
        </form>
      </div>
  
      <!-- Success Message -->
      <div v-if="successMessage" class="text-green-600 mt-4">
        {{ successMessage }}
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const gameSchedules = ref([])
  const scheduledGames = ref([])
  const selectedSchedule = ref(null)
  const successMessage = ref('')
  const newGame = ref({
    sportType: '',
    date: '',
    time: '',
    venue: '',
    opponent: '',
    crewPositions: '',
  })
  
  // Field Definitions for the Form
  const fields = [
    { name: 'sportType', label: 'Sport Type', type: 'text' },
    { name: 'date', label: 'Game Date', type: 'date' },
    { name: 'time', label: 'Game Time', type: 'time' },
    { name: 'venue', label: 'Venue', type: 'text' },
    { name: 'opponent', label: 'Opponent', type: 'text' },
    { name: 'crewPositions', label: 'Required Crew Positions', type: 'text' },
  ]
  
  // Fetch Existing Game Schedules
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:80/gameSchedules') // Adjust backend URL
      gameSchedules.value = response.data
    } catch (error) {
      console.error('Error fetching game schedules:', error)
    }
  })
  
  // Fetch Games for Selected Schedule
  async function fetchScheduledGames() {
    if (!selectedSchedule.value) return
  
    try {
      const response = await axios.get(
        `http://localhost:80/gameSchedules/${selectedSchedule.value}/games`
      ) // Adjust backend URL
      scheduledGames.value = response.data
    } catch (error) {
      console.error('Error fetching scheduled games:', error)
    }
  }
  
  // Add New Game
  async function handleAddGame() {
    successMessage.value = ''
  
    try {
      const response = await axios.post(
        `http://localhost:80/gameSchedules/${selectedSchedule.value}/games`,
        newGame.value
      ) // Adjust backend URL
  
      if (response.status === 201) {
        successMessage.value = 'Game added successfully!'
        scheduledGames.value.push(response.data)
        // Reset newGame fields
        Object.keys(newGame.value).forEach(key => (newGame.value[key] = ''))
      }
    } catch (error) {
      console.error('Error adding new game:', error)
    }
  }
  
  // Save Draft
  async function saveDraft() {
    successMessage.value = ''
  
    try {
      const response = await axios.post(
        `http://localhost:80/gameSchedules/${selectedSchedule.value}/games/draft`,
        newGame.value
      ) // Adjust backend URL
  
      if (response.status === 201) {
        successMessage.value = 'Draft saved successfully!'
      }
    } catch (error) {
      console.error('Error saving draft:', error)
    }
  }
  </script>
  
  <style scoped>
  /* Optional Styling */
  </style>
  