const API_BASE_URL = "http://localhost:8080";

export async function getAllEvents(accessToken, apiKey) {
  const response = await fetch(`${API_BASE_URL}/api/events`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "X-API-KEY": apiKey,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to load events");
  }

  return response.json();
}

export async function getEventById(id, accessToken, apiKey) {
  const response = await fetch(`${API_BASE_URL}/api/events/${id}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "X-API-KEY": apiKey,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to load event details");
  }

  return response.json();
}

export async function checkEventAvailability(id, accessToken, apiKey) {
  const response = await fetch(
    `${API_BASE_URL}/api/events/${id}/availability`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "X-API-KEY": apiKey,
      },
    }
  );

  if (!response.ok) {
    throw new Error("Failed to check event availability");
  }

  return response.json();
}