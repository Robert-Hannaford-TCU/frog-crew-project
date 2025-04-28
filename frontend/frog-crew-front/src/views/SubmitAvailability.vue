<template>
    <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Submit Availability</h1>
  
      <div v-if="games.length === 0" class="text-gray-500">
        No games available for availability submission.
      </div>
  
      <form v-else @submit.prevent="handleSubmit">
        <ul>
          <li v-for="game in games" :key="game.id" class="border-b py-3">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="font-bold text-lg">{{ game.date }} - {{ game.time }}</h2>
                <p>{{ game.opponent }} at {{ game.venue }}</p>
              </div>
              <div>
                <label class="mr-4">
                  <input
                    type="checkbox"
                    v-model="selectedGames"
                    :value="game.id"
                  />
                  Available
                </label>
                <input
                  type="text"
                  v-model="comments[game.id]"
                  placeholder="Add a comment (optional)"
                  class="border px-3 py-1 rounded-xl"
                />
              </div>
            </div>
          </li>
        </ul>
  
        <button
          type="submit"
          class="bg-blue-600 text-white px-4 py-2 rounded-xl mt-4 hover:bg-blue-700"
        >
          Submit
        </button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const games = ref([])
  const selectedGames = ref([])
  const comments = ref({})
  const successMessage = ref('')
  const errors = ref([])
  
  // Fetch games from the backend
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:8080/gameSchedule/notSubmitted') // Adjust endpoint as needed
      games.value = response.data.data
    } catch (error) {
      console.error('Error fetching games:', error)
      errors.value.push('Failed to fetch games. Please try again later.')
    }
  })
  
  // Handle form submission
  async function handleSubmit() {
    const availability = selectedGames.value.map(gameId => ({
      gameId,
      available: true,
      comment: comments.value[gameId] || '',
    }))
  
    try {
      const response = await axios.post('http://localhost:8080/availability', { availability })
      if (response.status === 200) {
        successMessage.value = 'Availability submitted successfully!'
        selectedGames.value = [] // Clear selections
        comments.value = {} // Clear comments
      } else {
        errors.value.push('Failed to submit availability. Please try again.')
      }
    } catch (error) {
      console.error('Error submitting availability:', error)
      errors.value.push('Error while submitting. Please try again.')
    }
  }
  </script>
  