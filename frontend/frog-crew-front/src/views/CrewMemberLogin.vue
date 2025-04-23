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
  
        <div v-if="loginSuccess" class="text-green-600 mt-4">
          Login successful. Redirecting...
        </div>
      </form>
    </div>
</template>
  
    <script setup>
    import { ref } from 'vue'

    // Stores email & password input values
    const credentials = ref({
    email: '',
    password: ''
    })

    const errors = ref({})
    const loginSuccess = ref(false)

    // Basic validation function
    function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
    }

    // Login logic
    import axios from 'axios'
    import { useRouter } from 'vue-router'

    const router = useRouter()

    async function handleLogin() {
    errors.value = {}

    // Validation Rules
    if (!credentials.value.email || !validateEmail(credentials.value.email)) {
        errors.value.email = 'Valid email is required.'
    }
    if (!credentials.value.password) {
        errors.value.password = 'Password is required.'
    }

    if (Object.keys(errors.value).length === 0) {
        try {
        // Request all crew members from backend
        const response = await axios.get('http://localhost:8080/crewMember')

        if (response.status === 200 && response.data.success) {
            const crewMembers = response.data.data

            // Find the matching crew member by email
            const foundMember = crewMembers.find(member => member.email === credentials.value.email)

            if (foundMember && foundMember.password === credentials.value.password) {
            loginSuccess.value = true

            // Store user session
            localStorage.setItem('crewMember', JSON.stringify(foundMember))

            setTimeout(() => {
                router.push('/dashboard') // Redirect to dashboard
            }, 2000)
            } else {
            errors.value.general = 'Invalid email or password.'
            }
        } else {
            errors.value.general = 'No crew members found.'
        }
        } catch (error) {
        console.error('Login error:', error)
        errors.value.general = 'Something went wrong. Please try again.'
        }
    }
    }

  </script>
  