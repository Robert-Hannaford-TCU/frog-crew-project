<template>
  <div class="max-w-md mx-auto p-6 bg-white rounded-2xl shadow-md mt-10">
    <h1 class="text-2xl font-bold mb-4">Invite Crew Member</h1>

    <form @submit.prevent="handleSubmit">
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
import { ref } from 'vue';
import axios from 'axios';

const form = ref({ email: '' });
const errors = ref({});
const successMessage = ref('');
const errorMessage = ref('');

// Validate input fields
function validateForm() {
  errors.value = {};
  if (!form.value.email || !validateEmail(form.value.email)) {
    errors.value.email = 'A valid email is required.';
  }
}

// Email validation
function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// Handle form submission
async function handleSubmit() {
  validateForm();

  if (Object.keys(errors.value).length === 0) {
    try {
      // Build the payload with a list of emails
      const payload = {
        emails: [form.value.email], // Backend expects a list of email addresses
      };

      console.log('Payload for Invitation:', payload);

      // Make the API call with Basic Auth
      const response = await axios.post('http://localhost:80/invite', payload, {
        auth: {
          username: localStorage.getItem('email'), // Saved email
          password: localStorage.getItem('password'), // Saved password
        },
      });

      // Handle success
      if (response.status === 200 && response.data.flag) {
        successMessage.value = 'Invitation email sent successfully!';
        form.value.email = ''; // Clear the email field after submission
      } else {
        // Handle failure based on backend response
        errorMessage.value = response.data.message || 'Failed to send the invitation. Please try again.';
      }
    } catch (error) {
      // Log and display error messages
      console.error('Error sending invitation:', error.response?.data || error.message);
      errorMessage.value = error.response?.data?.message || 'An error occurred while sending the invitation.';
    }
  }
}

</script>
