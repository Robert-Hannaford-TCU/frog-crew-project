<template>
  <div class="max-w-4xl mx-auto p-6 bg-white rounded-xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Manage Game Schedule</h1>

    <!-- Display Added Games -->
    <div class="mt-6">
      <h2 class="text-xl font-medium mb-2">Games Created in Create Game Schedule</h2>
      <div v-if="addedGames.length > 0">
        <ul class="list-disc ml-5">
          <li v-for="game in addedGames" :key="game.id">
            {{ game.sportType }} - {{ game.date }} at {{ game.venue }}
          </li>
        </ul>
      </div>
      <div v-else class="text-gray-500">
        No games have been created yet.
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const addedGames = ref([]);

// Fetch Created Games
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:80/gameSchedule/games', {
      auth: {
        username: localStorage.getItem('email'),
        password: localStorage.getItem('password'),
      },
    });
    addedGames.value = response.data.data; // Assuming backend responds with all added games
    console.log('Fetched games:', addedGames.value);
  } catch (error) {
    console.error('Error fetching added games:', error.response?.data || error.message);
  }
});
</script>

<style scoped>
/* Optional Styling */
</style>
