<template>
    <div class="max-w-4xl mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Game Schedule</h1>
  
      <div v-if="games.length === 0" class="text-gray-500">
        No upcoming games available at this time.
      </div>
  
      <div v-else>
        <div class="flex justify-between items-center mb-4">
          <input
            v-model="search"
            type="text"
            placeholder="Search games..."
            class="w-1/3 px-3 py-2 border rounded-xl"
          />
          <select v-model="sortOption" @change="sortGames" class="px-3 py-2 border rounded-xl">
            <option value="date">Sort by Date</option>
            <option value="opponent">Sort by Opponent</option>
            <option value="venue">Sort by Venue</option>
          </select>
        </div>
  
        <ul>
          <li v-for="game in filteredGames" :key="game.id" class="border-b py-3">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="font-bold text-lg">{{ game.date }} - {{ game.time }}</h2>
                <p>{{ game.opponent }} at {{ game.venue }}</p>
                <p>Required Crew: {{ game.requiredPositions.join(', ') }}</p>
              </div>
              <button
                class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
                @click="viewDetails(game.id)"
              >
                View Details
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
  
  const games = ref([])
  const search = ref('')
  const sortOption = ref('date')
  
  // Fetch game schedule
  onMounted(async () => {
    try {
      const response = await axios.get('http://localhost:80/gameSchedule') // Adjust endpoint as needed
      games.value = response.data.data
    } catch (error) {
      console.error('Error fetching game schedule:', error)
    }
  })
  
  // Filter and sort games
  const filteredGames = computed(() =>
    games.value
      .filter(game => game.opponent.toLowerCase().includes(search.value.toLowerCase()))
      .sort((a, b) => {
        if (sortOption.value === 'date') return new Date(a.date) - new Date(b.date)
        if (sortOption.value === 'opponent') return a.opponent.localeCompare(b.opponent)
        if (sortOption.value === 'venue') return a.venue.localeCompare(b.venue)
      })
  )
  
  // Navigate to game details
  function viewDetails(gameId) {
    console.log('Navigating to game details for game ID:', gameId)
    // Implement further logic, e.g., redirect to /game-details/:id
  }
  </script>
  