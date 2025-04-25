<template>
    <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Invite Crew Member</h1>
  
      <form @submit.prevent="handleSubmit">
        <div class="mb-4">
          <label for="name" class="block font-medium mb-1">Crew Member Name</label>
          <input
            id="name"
            v-model="form.name"
            type="text"
            placeholder="Enter crew member name"
            class="w-full px-3 py-2 border rounded-xl"
          />
          <p v-if="errors.name" class="text-red-500 text-sm mt-1">{{ errors.name }}</p>
        </div>
  
        <div class="mb-4">
          <label for="email" class="block font-medium mb-1">Email</label>
          <input
            id="email"
            v-model="form.email"
            type="email"
            placeholder="Enter crew member email"
            class="w-full px-3 py-2 border rounded-xl"
          />
          <p v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</p>
        </div>
  
        <div class="mb-4">
          <label for="role" class="block font-medium mb-1">Role</label>
          <input
            id="role"
            v-model="form.role"
            type="text"
            placeholder="Enter crew member role(s)"
            class="w-full px-3 py-2 border rounded-xl"
          />
          <p v-if="errors.role" class="text-red-500 text-sm mt-1">{{ errors.role }}</p>
        </div>
  
        <button
          type="submit"
          class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
        >
          Send Invitation
        </button>
  
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
  
  const form = ref({
    name: '',
    email: '',
    role: ''
  })
  
  const errors = ref({})
  const successMessage = ref('')
  const errorMessage = ref('')
  
  // Validate input fields
  function validateForm() {
    errors.value = {}
    if (!form.value.name || form.value.name.length > 50) {
      errors.value.name = 'Name is required and must not exceed 50 characters.'
    }
    if (!form.value.email || !validateEmail(form.value.email)) {
      errors.value.email = 'A valid email is required.'
    }
    if (!form.value.role) {
      errors.value.role = 'Role is required.'
    }
  }
  
  // Email validation
  function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }
  
  // Handle form submission
  async function handleSubmit() {
    validateForm()
  
    if (Object.keys(errors.value).length === 0) {
      try {
        const response = await axios.post('http://localhost:8080/invite', form.value)
        if (response.status === 200) {
          successMessage.value = 'Invitation email sent successfully!'
          form.value.name = ''
          form.value.email = ''
          form.value.role = ''
        } else {
          errorMessage.value = 'Failed to send the invitation. Please try again.'
        }
      } catch (error) {
        console.error('Error sending invitation:', error)
        errorMessage.value = 'An error occurred while sending the invitation.'
      }
    }
  }
  </script>
  