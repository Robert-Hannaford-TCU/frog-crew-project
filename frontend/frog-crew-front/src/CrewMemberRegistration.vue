<template>
    <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
      <h1 class="text-2xl font-bold mb-4">Create Crew Member Profile</h1>
      
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
  
        <div class="flex justify-between">
          <button
            type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
          >
            Register
          </button>
        </div>
  
        <div v-if="success" class="text-green-600 mt-4">
          Account successfully created. Redirecting to login...
        </div>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const form = ref({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    role: '',
    qualifiedPosition: ''
  })
  
  const errors = ref({})
  const success = ref(false)
  
  const fields = [
    { name: 'firstName', label: 'First Name', type: 'text' },
    { name: 'lastName', label: 'Last Name', type: 'text' },
    { name: 'email', label: 'Email', type: 'email' },
    { name: 'phone', label: 'Phone Number (999-999-9999)', type: 'text' },
    { name: 'password', label: 'Password', type: 'password' },
    { name: 'role', label: 'Role', type: 'text' },
    { name: 'qualifiedPosition', label: 'Qualified Position', type: 'text' }
  ]
  
  function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }
  
  function validatePhone(phone) {
    return /^\d{3}-\d{3}-\d{4}$/.test(phone)
  }
  
  function handleSubmit() {
    errors.value = {}
  
    // Validation Rules
    if (!form.value.firstName) errors.value.firstName = 'First name is required.'
    if (!form.value.lastName) errors.value.lastName = 'Last name is required.'
    if (!form.value.email || !validateEmail(form.value.email)) {
      errors.value.email = 'Valid email is required.'
    }
    if (!form.value.phone || !validatePhone(form.value.phone)) {
      errors.value.phone = 'Phone must be in format 999-999-9999.'
    }
    if (!form.value.password) errors.value.password = 'Password is required.'
    if (!form.value.role) errors.value.role = 'Role is required.'
    if (!form.value.qualifiedPosition) {
      errors.value.qualifiedPosition = 'Qualified Position is required.'
    }
  
    if (Object.keys(errors.value).length === 0) {
      // Simulate API call success
      success.value = true
      setTimeout(() => {
        window.location.href = '/login' // Redirect to login
      }, 2000)
    }
  }
  </script>
  
  <style scoped>
  
  </style>
  