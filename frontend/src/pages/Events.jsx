import { useEffect, useState } from "react";
import { getAllEvents } from "../services/eventApi";

function Events() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadEvents() {
      try {
        setLoading(true);
        setError("");

        const accessToken = localStorage.getItem("accessToken");
        const eventApiKey = localStorage.getItem("eventApiKey");

        if (!accessToken || !eventApiKey) {
          setError("Authentication details are missing.");
          setLoading(false);
          return;
        }

        const data = await getAllEvents(
          accessToken,
          eventApiKey
        );

        setEvents(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    loadEvents();
  }, []);

  return (
    <main className="page-container">
      <div className="section-heading">
        <span>Discover Experiences</span>
        <h1>Upcoming Events</h1>
        <p>
          Explore available events, venues, ticket prices,
          and booking availability.
        </p>
      </div>

      {loading && (
        <div className="message-card">
          Loading events...
        </div>
      )}

      {!loading && error && (
        <div className="message-card error-message">
          {error}
        </div>
      )}

      {!loading && !error && events.length === 0 && (
        <div className="message-card">
          No events are available at the moment.
        </div>
      )}

      {!loading && !error && events.length > 0 && (
        <div className="events-grid">
          {events.map((event) => (
            <article
              className="event-card"
              key={event.id}
            >
              <div className="event-card-header">
                <span className="event-status">
                  {event.eventStatus}
                </span>

                <span className="event-price">
                  LKR {Number(event.ticketPrice).toLocaleString()}
                </span>
              </div>

              <h2>{event.eventName}</h2>

              <p className="event-description">
                {event.description}
              </p>

              <div className="event-info-list">
                <div>
                  <span>Venue</span>
                  <strong>{event.venue}</strong>
                </div>

                <div>
                  <span>Date</span>
                  <strong>
                    {event.eventDate
                      ? new Date(event.eventDate).toLocaleString()
                      : "Not available"}
                  </strong>
                </div>

                <div>
                  <span>Available Tickets</span>
                  <strong>{event.availableTickets}</strong>
                </div>
              </div>

              <a
                className="primary-btn event-book-btn"
                href={`/book?eventId=${event.id}`}
              >
                Book This Event
              </a>
            </article>
          ))}
        </div>
      )}
    </main>
  );
}

export default Events;