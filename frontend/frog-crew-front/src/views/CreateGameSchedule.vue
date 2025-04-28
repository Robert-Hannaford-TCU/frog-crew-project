<template>
    <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Create Game Schedule</h1>
  
      <form @submit.prevent="handleSubmit">
        <div v-for="field in fields" :key="field.name" class="mb-4">
          <label :for="field.name" class="block font-medium mb-1">{{ field.label }}</label>
  
          <input
            :id="field.name"
            v-model="form[field.name]"
            :type="field.type"
            :placeholder="field.label"
            class="w-full px-3 py-2 border rounded-xl"
          />
          <p v-if="errors[field.name]" class="text-red-500 text-sm mt-1">{{ errors[field.name] }}</p>
        </div>
  
        <!-- Required Crew Positions Field -->
        <div class="mb-4">
            <label
                for="requiredPositions"
                class="block font-medium mb-1"
                style="display: inline-block; vertical-align: top;"
            >
            Required Crew Positions
            </label>
            <textarea
            id="requiredPositions"
            v-model="form.requiredPositions"
            placeholder="Enter crew positions, separated by commas (e.g., Producer, Director, Camera Operator)"
            class="w-full px-3 py-2 border rounded-xl resize-none"
            style="height: 150px; resize: none;" 
            ></textarea>
            <p v-if="errors.requiredPositions" class="text-red-500 text-sm mt-1">{{ errors.requiredPositions }}</p>
        </div>


  
        <div class="flex justify-between">
            <button
                type="submit"
                class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
            >
                Save Schedule
            </button>
        </div>
  
        <div v-if="successMessage" class="text-green-600 mt-4">
            {{ successMessage }}
        </div>
        <div v-if="errorMessage" class="text-red-500 mt-4">
            {{ errorMessage }}
        </div>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  
  // Form object to store data
  const form = ref({
    sportType: '',
    gameDate: '',
    venue: '',
    opponent: '',
    requiredPositions: ''
  })
  
  const fields = [
    { name: 'sportType', label: 'Sport Type', type: 'text' },
    { name: 'gameDate', label: 'Game Date and Time', type: 'datetime-local' },
    { name: 'venue', label: 'Venue', type: 'text' },
    { name: 'opponent', label: 'Opponent', type: 'text' }
  ]
  
  const errors = ref({})
  const successMessage = ref('')
  const errorMessage = ref('')
  
  // Form validation function
  function validateForm() {
    errors.value = {}
    if (!form.value.sportType) errors.value.sportType = 'Sport type is required.'
    if (!form.value.gameDate) errors.value.gameDate = 'Game date and time are required.'
    if (!form.value.venue) errors.value.venue = 'Venue is required.'
    
    // Validate Required Positions and ensure comma-separated/not empty
    if (!form.value.requiredPositions) {
      errors.value.requiredPositions = 'Required crew positions are required.'
    } else if (!form.value.requiredPositions.includes(',')) {
      errors.value.requiredPositions = 'Separate crew positions with commas (e.g., Producer, Director).'
    }
  }
  
  // Handle form submission
  async function handleSubmit() {
    validateForm()
  
    if (Object.keys(errors.value).length === 0) {
      // Clean up required positions: remove extra spaces
      form.value.requiredPositions = form.value.requiredPositions
        .split(',')
        .map(pos => pos.trim())
        .join(', ')
  
      try {
        const response = await axios.post('http://localhost:8080/gameSchedule', form.value)
        if (response.status === 200) {
          successMessage.value = 'Game schedule saved successfully as a draft!'
          form.value.sportType = ''
          form.value.gameDate = ''
          form.value.venue = ''
          form.value.opponent = ''
          form.value.requiredPositions = ''
        } else {
          errorMessage.value = 'Failed to save the game schedule. Please try again.'
        }
      } catch (error) {
        console.error('Error saving game schedule:', error)
        errorMessage.value = 'An error occurred while saving the game schedule.'
      }
    }
  }
  </script>
  
  <style scoped>
  textarea {
    font-size: 1rem;
  }
  </style>
  