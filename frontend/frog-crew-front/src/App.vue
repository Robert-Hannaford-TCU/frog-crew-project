<template>
    <div class="app-container">
      <header class="p-4 bg-blue-600 text-white text-center text-xl font-bold">
        Frog Crew Management
      </header>
      
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
  </style>
  