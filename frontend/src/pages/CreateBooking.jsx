import { useEffect, useMemo, useState } from "react";
import { createBooking } from "../services/bookingApi";
import { getAllEvents } from "../services/eventApi";

function CreateBooking() {
  const [events, setEvents] = useState([]);
  const [selectedEventId, setSelectedEventId] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [customerEmail, setCustomerEmail] = useState("");
  const [ticketCount, setTicketCount] = useState(1);

  const [loadingEvents, setLoadingEvents] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadEvents() {
      try {
        setLoadingEvents(true);
        setError("");

        const accessToken = localStorage.getItem("accessToken");
        const eventApiKey = localStorage.getItem("eventApiKey");

        if (!accessToken || !eventApiKey) {
          setError("Event authentication details are missing.");
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
        setLoadingEvents(false);
      }
    }

    loadEvents();
  }, []);

  const selectedEvent = useMemo(
    () =>
      events.find(
        (event) => event.id === selectedEventId
      ),
    [events, selectedEventId]
  );

  const estimatedTotal = selectedEvent
    ? Number(selectedEvent.ticketPrice) * Number(ticketCount || 0)
    : 0;

  async function handleSubmit(event) {
    event.preventDefault();

    setMessage("");
    setError("");

    if (!selectedEvent) {
      setError("Please select an event.");
      return;
    }

    try {
      setSubmitting(true);

      const bookingData = {
        eventId: selectedEvent.id,
        customerName,
        customerEmail,
        ticketCount: Number(ticketCount),
        ticketPrice: Number(selectedEvent.ticketPrice),
      };

      const createdBooking = await createBooking(
        bookingData
      );

      setMessage(
        `Booking confirmed. Booking ID: ${createdBooking.id}`
      );

      setCustomerName("");
      setCustomerEmail("");
      setTicketCount(1);
      setSelectedEventId("");
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="page-container">
      <div className="booking-layout">

        <section className="booking-form-card">
          <div className="section-heading">
            <span>Reserve Your Experience</span>
            <h1>Create Booking</h1>
            <p>
              Choose an event, enter your details, and reserve
              your tickets through EventSphere.
            </p>
          </div>

          {loadingEvents && (
            <div className="message-card">
              Loading available events...
            </div>
          )}

          {!loadingEvents && (
            <form
              className="booking-form"
              onSubmit={handleSubmit}
            >
              <div className="form-group">
                <label htmlFor="event">
                  Select Event
                </label>

                <select
                  id="event"
                  value={selectedEventId}
                  onChange={(event) =>
                    setSelectedEventId(event.target.value)
                  }
                  required
                >
                  <option value="">
                    Choose an event
                  </option>

                  {events.map((event) => (
                    <option
                      value={event.id}
                      key={event.id}
                    >
                      {event.eventName} - LKR{" "}
                      {Number(
                        event.ticketPrice
                      ).toLocaleString()}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label htmlFor="customerName">
                  Customer Name
                </label>

                <input
                  id="customerName"
                  type="text"
                  placeholder="Enter your full name"
                  value={customerName}
                  onChange={(event) =>
                    setCustomerName(event.target.value)
                  }
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="customerEmail">
                  Email Address
                </label>

                <input
                  id="customerEmail"
                  type="email"
                  placeholder="Enter your email"
                  value={customerEmail}
                  onChange={(event) =>
                    setCustomerEmail(event.target.value)
                  }
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="ticketCount">
                  Number of Tickets
                </label>

                <input
                  id="ticketCount"
                  type="number"
                  min="1"
                  value={ticketCount}
                  onChange={(event) =>
                    setTicketCount(event.target.value)
                  }
                  required
                />
              </div>

              {error && (
                <div className="form-alert error-alert">
                  {error}
                </div>
              )}

              {message && (
                <div className="form-alert success-alert">
                  {message}
                </div>
              )}

              <button
                className="primary-btn booking-submit-btn"
                type="submit"
                disabled={submitting}
              >
                {submitting
                  ? "Creating Booking..."
                  : "Confirm Booking"}
              </button>
            </form>
          )}
        </section>

        <aside className="booking-summary-card">
          <span className="summary-label">
            Booking Summary
          </span>

          {selectedEvent ? (
            <>
              <h2>{selectedEvent.eventName}</h2>

              <div className="summary-list">
                <div>
                  <span>Venue</span>
                  <strong>{selectedEvent.venue}</strong>
                </div>

                <div>
                  <span>Ticket Price</span>
                  <strong>
                    LKR{" "}
                    {Number(
                      selectedEvent.ticketPrice
                    ).toLocaleString()}
                  </strong>
                </div>

                <div>
                  <span>Tickets</span>
                  <strong>{ticketCount}</strong>
                </div>

                <div className="summary-total">
                  <span>Estimated Total</span>
                  <strong>
                    LKR{" "}
                    {estimatedTotal.toLocaleString()}
                  </strong>
                </div>
              </div>

              <p className="summary-note">
                Final total amount is calculated again by the
                Booking Service when the booking is created.
              </p>
            </>
          ) : (
            <div className="empty-summary">
              <h2>Select an Event</h2>
              <p>
                Your event and booking price summary will appear
                here.
              </p>
            </div>
          )}
        </aside>

      </div>
    </main>
  );
}

export default CreateBooking;