const API_BASE_URL = "http://localhost:8080";

function getHeaders() {
  const accessToken = localStorage.getItem("ticketAccessToken");
  const ticketApiKey = localStorage.getItem("ticketApiKey");

  return {
    Authorization: `Bearer ${accessToken}`,
    "X-API-KEY": ticketApiKey,
  };
}

// Get all tickets
export async function getAllTickets() {
  const response = await fetch(`${API_BASE_URL}/api/tickets`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    throw new Error("Failed to load tickets");
  }

  return response.json();
}

// Get ticket by ID
export async function getTicketById(ticketId) {
  const response = await fetch(
    `${API_BASE_URL}/api/tickets/${ticketId}`,
    {
      method: "GET",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    throw new Error("Ticket not found");
  }

  return response.json();
}

// Get ticket by booking ID
export async function getTicketByBookingId(bookingId) {
  const response = await fetch(
    `${API_BASE_URL}/api/tickets/booking/${bookingId}`,
    {
      method: "GET",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    throw new Error("Ticket not found for this booking");
  }

  return response.json();
}

// Get tickets by customer email
export async function getTicketsByCustomerEmail(email) {
  const response = await fetch(
    `${API_BASE_URL}/api/tickets/customer?email=${encodeURIComponent(email)}`,
    {
      method: "GET",
      headers: getHeaders(),
    }
  );

  if (!response.ok) {
    throw new Error("Failed to load customer tickets");
  }

  return response.json();
}