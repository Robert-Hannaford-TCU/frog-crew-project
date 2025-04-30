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

      <form @submit.prevent="handleSubmit">
        <ul>
          <li v-for="game in filteredGames" :key="game.gameId" class="border-b py-3">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="font-bold text-lg">{{ game.gameDate }} - {{ game.time }}</h2>
                <p>{{ game.opponent }} at {{ game.venue }}</p>
                <p>
                  Required Crew: {{ game.requiredPositions ? game.requiredPositions.join(', ') : 'None specified' }}
                </p>
              </div>
              <div>
                <label class="mr-4">
                  <input
                    type="checkbox"
                    v-model="selectedGames[game.gameId]"
                  />
                  Available
                </label>
                <input
                  type="text"
                  v-model="comments[game.gameId]"
                  placeholder="Add a comment (optional)"
                  class="border px-3 py-1 rounded-xl"
                  :disabled="!selectedGames[game.gameId]"
                />
              </div>
            </div>
          </li>
        </ul>

        <button
          type="submit"
          class="bg-blue-600 text-white px-4 py-2 rounded-xl mt-4 hover:bg-blue-700"
        >
          Submit Availability
        </button>
      </form>

      <!-- Success Message -->
      <div v-if="successMessage" class="text-green-500 mt-4">{{ successMessage }}</div>

      <!-- Error Message -->
      <div v-if="submitError" class="text-red-500 mt-4">{{ submitError }}</div>
    </div>
  </div>
</template>


<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const games = ref([]); // List of games
const search = ref(''); // Search input
const sortOption = ref('date'); // Sorting option
const selectedGames = ref({}); // Object for managing selected checkboxes
const comments = ref({}); // Comments for selected games
const successMessage = ref(''); // Success message for availability submission
const submitError = ref(null); // Error message for availability submission

// Fetch game schedule with authorization
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:80/gameSchedule/games', {
      auth: {
        username: localStorage.getItem('email'), // Retrieve email from localStorage
        password: localStorage.getItem('password'), // Retrieve password from localStorage
      },
    });
    
    console.log('Full Backend Response:', response.data);

    games.value = response.data.data;

    // Log game IDs or warnings for missing IDs
    games.value.forEach((game, index) => {
      if (!game.gameId) {
        console.warn(`Warning: Missing gameId for game at index ${index}`, game);
      } else {
        console.log(`Game ${index + 1} ID: ${game.gameId}`);
      }
    });

    // Initialize selectedGames and comments objects
    games.value.forEach(game => {
      selectedGames.value[game.gameId] = false; // Default all checkboxes to unchecked
      comments.value[game.gameId] = ''; // Default all comments to empty strings
    });
  } catch (error) {
    console.error('Error fetching game schedule:', error.message);
  }
});

// Filter and sort games
const filteredGames = computed(() => {
  return games.value
    .filter(game => game.opponent.toLowerCase().includes(search.value.toLowerCase()))
    .sort((a, b) => {
      // Correctly reference gameDate for sorting
      if (sortOption.value === 'date') return new Date(a.gameDate) - new Date(b.gameDate);
      if (sortOption.value === 'opponent') return a.opponent.localeCompare(b.opponent);
      if (sortOption.value === 'venue') return a.venue.localeCompare(b.venue);
    });
});

// Handle availability submission
async function handleSubmit() {
  // Retrieve the userId from localStorage
  const userId = localStorage.getItem('userId'); // Ensure userId is stored in localStorage

  // Prepare a single `AvailabilityDto` for submission (only one selected game)
  const gameId = Object.keys(selectedGames.value).find(gameId => selectedGames.value[gameId]); // Find the first selected game

  if (!gameId) {
    submitError.value = 'Please select at least one game to submit availability.';
    return;
  }

  const availabilityDto = {
    userId: parseInt(userId), // Add userId to the payload
    gameId: parseInt(gameId), // Ensure gameId is sent as a number
    available: true,
    comment: comments.value[gameId] || '', // Use provided comment or default to an empty string
  };

  console.log('Payload for Availability Submission:', availabilityDto);

  try {
  const response = await axios.post('http://localhost:80/availability', availabilityDto, {
    auth: {
      username: localStorage.getItem('email'),
      password: localStorage.getItem('password'),
    },
  });

  console.log('Backend Response:', response.data);

  if (response.status === 200 && response.data.flag) {
    successMessage.value = response.data.message || 'Availability submitted successfully!';
    selectedGames.value = {}; // Clear selected games
    comments.value = {}; // Clear comments
    submitError.value = null; // Clear error messages
  } else {
    throw new Error(response.data.message || 'Failed to submit availability.');
  }
  } catch (error) {
    console.error('Error submitting availability:', error.response?.data || error.message);
    submitError.value = error.response?.data?.message || 'Error while submitting availability. Please try again.';
  }
}


// Navigate to game details
function viewDetails(gameId) {
  console.log('Navigating to game details for game ID:', gameId);
 
}
</script>

