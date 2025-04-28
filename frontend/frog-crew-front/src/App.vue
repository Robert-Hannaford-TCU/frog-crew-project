<template>
  <div class="app-container">
    <header class="p-4 bg-blue-600 text-white text-center text-xl font-bold">
      Frog Crew Management
    </header>

    <!-- Navigation Links -->
    <nav class="p-4 bg-gray-100 flex justify-center space-x-4">
      <router-link to="/" class="text-blue-600 hover:underline" active-class="font-bold underline">
        Register
      </router-link>
      <router-link to="/crew-member-login" class="text-blue-600 hover:underline" active-class="font-bold underline">
        Login
      </router-link>
      <router-link to="/crew-member-profile" class="text-blue-600 hover:underline" active-class="font-bold underline">
        Profile
      </router-link>
      <router-link to="/game-schedule" class="text-blue-600 hover:underline" active-class="font-bold underline">
        Game Schedule
      </router-link>
      <router-link to="/crew-list" class="text-blue-600 hover:underline" active-class="font-bold underline">
        Crew List
      </router-link>
      <router-link to="/submit-availability" class="text-blue-600 hover:underline">
        Submit Availability
      </router-link>
      <router-link to="/invite-crew-member" class="text-blue-600 hover:underline">
        Invite Crew Member
      </router-link>
      <router-link to="/delete-crew-member" class="text-blue-600 hover:underline">
        Delete Crew Member
      </router-link>
      <router-link to="/create-game-schedule" class="text-blue-600 hover:underline">
        Create Game Schedule
      </router-link>
      <router-link to="/add-games" class="hover:underline">Add Games</router-link>
      <router-link to="/schedule-crew" class="hover:underline">Schedule Crew</router-link>
    </nav>

    <main class="p-6">
      <router-view></router-view> <!-- Displays the active route's component -->
    </main>
  </div>
</template>

<script setup>
</script>

<style scoped>
.app-container {
  font-family: Arial, sans-serif;
}
nav {
  background-color: #f3f4f6; /* Light gray background */
  padding: 1rem;
  display: flex;
  justify-content: center;
  gap: 1rem;
}
nav a {
  color: #2563eb; /* Blue links */
  text-decoration: none;
}
nav a:hover {
  text-decoration: underline;
}
nav a.active {
  font-weight: bold;
  text-decoration: underline;
}
</style>
