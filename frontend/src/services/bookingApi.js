const API_BASE_URL = "http://localhost:8080";

function getHeaders() {
  const accessToken = localStorage.getItem("bookingAccessToken");
  const bookingApiKey = localStorage.getItem("bookingApiKey");

  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${accessToken}`,
    "X-API-KEY": bookingApiKey,
  };
}

// Create a new booking
export async function createBooking(bookingData) {
  const response = await fetch(`${API_BASE_URL}/api/bookings`, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(bookingData),
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => null);

    throw new Error(
      errorData?.message || "Failed to create booking"
    );
  }

  return response.json();
}

// Get a booking using booking ID
export async function getBookingById(bookingId) {
  const response = await fetch(
    `${API_BASE_URL}/api/bookings/${bookingId}`,
    {
      method: "GET",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    throw new Error("Booking not found");
  }

  return response.json();
}

// Get all bookings for a user
export async function getUserBookings(email) {
  const response = await fetch(
    `${API_BASE_URL}/api/bookings/user?email=${encodeURIComponent(email)}`,
    {
      method: "GET",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    throw new Error("Failed to load user bookings");
  }

  return response.json();
}

// Cancel an existing booking
export async function cancelBooking(bookingId) {
  const response = await fetch(
    `${API_BASE_URL}/api/bookings/${bookingId}/cancel`,
    {
      method: "PUT",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    const errorData = await response.json().catch(() => null);

    throw new Error(
      errorData?.message || "Failed to cancel booking"
    );
  }

  return response.json();
}