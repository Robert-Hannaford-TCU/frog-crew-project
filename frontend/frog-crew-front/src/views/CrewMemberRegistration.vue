<template>
  <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Create Crew Member Profile</h1>

    <form @submit.prevent="handleSubmit">
      <div v-for="field in fields" :key="field.name" class="mb-4">
        <label :for="field.name" class="block font-medium mb-1">{{ field.label }}</label>

        <!-- Separate Phone Number Fields -->
        <div v-if="field.name === 'phone'" class="flex gap-2">
          <input 
            id="phone1"
            v-model="phoneParts[0]"
            type="text"
            maxlength="3"
            class="px-3 py-2 border rounded-xl text-center"
            style="width: 40px;"
            @input="autoMove(0)"
          />
          <input 
            id="phone2"
            v-model="phoneParts[1]"
            type="text"
            maxlength="3"
            class="px-3 py-2 border rounded-xl text-center"
            style="width: 40px;"
            @input="autoMove(1)"
          />
          <input 
            id="phone3"
            v-model="phoneParts[2]"
            type="text"
            maxlength="4"
            class="px-3 py-2 border rounded-xl text-center"
            style="width: 50px;"
          />
        </div>

        <!-- Other Fields -->
        <input
          v-else
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
import { ref } from 'vue';

const form = ref({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  role: '',
  qualifiedPosition: ''
});

const phoneParts = ref(['', '', '']); // Stores phone number in three parts
const errors = ref({});
const success = ref(false);

// Field Definitions
const fields = [
  { name: 'firstName', label: 'First Name', type: 'text' },
  { name: 'lastName', label: 'Last Name', type: 'text' },
  { name: 'email', label: 'Email', type: 'email' },
  { name: 'phone', label: 'Phone Number', type: 'text' }, 
  { name: 'password', label: 'Password', type: 'password' },
  { name: 'role', label: 'Role', type: 'text' },
  { name: 'qualifiedPosition', label: 'Qualified Position', type: 'text' }
];

// Validation Helper Functions
function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validatePhone(phone) {
  return /^\d{10}$/.test(phone);
}

// Automatically move to the next input field after 3 digits
function autoMove(index) {
  if (phoneParts.value[index].length === (index === 2 ? 4 : 3)) {
    const nextInput = document.getElementById(`phone${index + 2}`);
    if (nextInput) nextInput.focus();
  }
}

import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

async function handleSubmit() {
  errors.value = {};

  // Merge phone input values
  const phoneNumber = phoneParts.value.join('');
  form.value.phone = phoneNumber; // Store as a single value

  // Validation Rules
  if (!form.value.firstName) errors.value.firstName = 'First name is required.';
  if (!form.value.lastName) errors.value.lastName = 'Last name is required.';
  if (!form.value.email || !validateEmail(form.value.email)) {
    errors.value.email = 'Valid email is required.';
  }
  if (!validatePhone(phoneNumber)) {
    errors.value.phone = 'Phone number must be 10 digits.';
  }
  if (!form.value.password) errors.value.password = 'Password is required.';
  if (!form.value.role) {
    errors.value.role = 'Role is required.';
  }
  if (!form.value.qualifiedPosition) {
    errors.value.qualifiedPosition = 'Qualified Position is required.';
  }

  // Stop submission if there are any errors
  if (Object.keys(errors.value).length === 0) {
    try {
      const response = await axios.post('http://localhost:80/crewMember', form.value, {
        auth: {
          username: localStorage.getItem('email'), // Retrieve admin email from localStorage
          password: localStorage.getItem('password') // Retrieve admin password from localStorage
        }
      });

      console.log('Response from server:', response.data);

      if (response.status === 201) {
        success.value = true;
        setTimeout(() => {
          router.push('/crew-member-login'); // Redirect after successful registration
        }, 2000);
      } else {
        errors.value.general = response.data.message || 'Failed to create crew member.';
      }
    } catch (error) {
      console.error('Error adding crew member:', error.response?.data || error.message);
      errors.value.general = 'Something went wrong, please try again later.';
    }
  } else {
    console.log('Validation errors:', errors.value);
  }
}
</script>

<style scoped>
/* Optional Styling */
</style>
