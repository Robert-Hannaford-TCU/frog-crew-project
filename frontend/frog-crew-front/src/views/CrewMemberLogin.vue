<template>
  <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Crew Member Login</h1>

    <form @submit.prevent="handleLogin">
      <div class="mb-4">
        <label for="email" class="block font-medium mb-1">Email</label>
        <input 
          id="email"
          v-model="credentials.email"
          type="email"
          class="w-full px-3 py-2 border rounded-xl"
          placeholder="Enter your email"
        />
        <p v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</p>
      </div>

      <div class="mb-4">
        <label for="password" class="block font-medium mb-1">Password</label>
        <input 
          id="password"
          v-model="credentials.password"
          type="password"
          class="w-full px-3 py-2 border rounded-xl"
          placeholder="Enter your password"
        />
        <p v-if="errors.password" class="text-red-500 text-sm mt-1">{{ errors.password }}</p>
      </div>

      <div class="flex justify-between">
        <button 
          type="submit"
          class="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700"
        >
          Login
        </button>
      </div>

      <div v-if="errors.general" class="text-red-500 text-sm mt-4">
        {{ errors.general }}
      </div>

      <div v-if="loginSuccess" class="text-green-600 mt-4">
        Login successful. Redirecting...
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const credentials = ref({
  email: '',
  password: ''
})

const errors = ref({})
const loginSuccess = ref(false)
const router = useRouter()

function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

async function handleLogin() {
  errors.value = {}

  // Validation Rules
  if (!credentials.value.email || !validateEmail(credentials.value.email)) {
    errors.value.email = 'A valid email is required.'
  }
  if (!credentials.value.password) {
    errors.value.password = 'Password is required.'
  }

  // Stop if there are validation errors
  if (Object.keys(errors.value).length > 0) {
    return
  }

  try {
    // Send login request to backend
    const response = await axios.post('http://localhost:8080/auth/login', {}, {
      auth: {
        username: credentials.value.email,
        password: credentials.value.password
      }
    })

    if (response.status === 200 && response.data.success) {
      loginSuccess.value = true

      // Save user information in localStorage
      const userData = response.data.data
      localStorage.setItem('userId', userData.userId)
      localStorage.setItem('role', userData.role)

      // Redirect to dashboard after successful login
      setTimeout(() => {
        router.push('/dashboard')
      }, 2000)
    } else {
      errors.value.general = response.data.message || 'Login failed. Please try again.'
    }
  } catch (error) {
    console.error('Login error:', error)
    errors.value.general = 'Invalid email or password.'
  }
}
</script>
