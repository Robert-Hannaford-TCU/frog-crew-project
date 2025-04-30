<template>
  <div class="max-w-4xl mx-auto p-6 bg-white rounded-xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Schedule Crew Members</h1>

    <!-- Select Game -->
    <div v-if="games.length > 0" class="mb-6">
      <label for="game" class="block font-medium mb-2">Select a Game</label>
      <select
        id="game"
        v-model="selectedGame"
        @change="fetchCrewList"
        class="w-full px-3 py-2 border rounded-xl"
      >
        <option value="" disabled>Select a game</option>
        <option v-for="game in games" :key="game.id" :value="game.id">
          {{ game.sportType }} - {{ game.date }} at {{ game.venue }}
        </option>
      </select>
    </div>

    <!-- Available Crew List -->
    <div v-if="crewList.positions.length > 0" class="mb-6">
      <h2 class="text-xl font-medium mb-4">Available Crew Positions</h2>
      <form @submit.prevent="finalizeCrewAssignments">
        <div v-for="position in crewList.positions" :key="position.id" class="mb-4">
          <label :for="position.name" class="block font-medium mb-1">
            {{ position.name }}
          </label>
          <select
            :id="position.id"
            v-model="assignedCrew[position.id]"
            class="w-full px-3 py-2 border rounded-xl"
          >
            <option value="" disabled>Select crew member</option>
            <option v-for="crewMember in crewList.crewMembers" :key="crewMember.id" :value="crewMember.id">
              {{ crewMember.name }} ({{ crewMember.qualification }})
            </option>
          </select>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-between mt-6">
          <button
            type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
          >
            Finalize Assignments
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
import { ref, onMounted } from 'vue';
import axios from 'axios';

const games = ref([]);
const crewList = ref({ positions: [], crewMembers: [] });
const assignedCrew = ref({}); // Stores assigned crew members for each position
const selectedGame = ref(null);
const successMessage = ref('');

// Fetch Scheduled Games
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:80/gameSchedule/games', {
      auth: {
        username: localStorage.getItem('email'), // Retrieve username from localStorage
        password: localStorage.getItem('password'), // Retrieve password from localStorage
      },
    });
    games.value = response.data;
  } catch (error) {
    console.error('Error fetching games:', error);
  }
});

// Fetch Crew List for Selected Game
async function fetchCrewList() {
  if (!selectedGame.value) return;

  try {
    const response = await axios.get(`http://localhost:80/crewList/${selectedGame.value}`, {
      auth: {
        username: localStorage.getItem('email'), // Retrieve username from localStorage
        password: localStorage.getItem('password'), // Retrieve password from localStorage
      },
    });
    crewList.value = response.data.data; // Backend returns `Result`, so `data` is nested
  } catch (error) {
    console.error('Error fetching crew list:', error);
  }
}

// Finalize Crew Assignments
async function finalizeCrewAssignments() {
  successMessage.value = '';

  try {
    const assignments = Object.entries(assignedCrew.value).map(([positionId, crewMemberId]) => ({
      positionId,
      crewMemberId,
    }));

    const response = await axios.post(
      `http://localhost:80/crewSchedule/${selectedGame.value}`,
      assignments,
      {
        auth: {
          username: localStorage.getItem('email'), // Retrieve username from localStorage
          password: localStorage.getItem('password'), // Retrieve password from localStorage
        },
      }
    );

    if (response.status === 200) {
      successMessage.value = 'Crew assignments finalized successfully!';
      // Clear assignments
      assignedCrew.value = {};
    }
  } catch (error) {
    console.error('Error finalizing crew assignments:', error);
  }
}
</script>

<style scoped>
/* Optional Styling */
</style>
