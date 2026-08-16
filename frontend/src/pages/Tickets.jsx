import { useEffect, useState } from "react";
import {
  getAllTickets,
  getTicketsByCustomerEmail,
} from "../services/ticketApi";

function Tickets() {
  const [tickets, setTickets] = useState([]);
  const [email, setEmail] = useState("");

  const [loading, setLoading] = useState(true);
  const [searching, setSearching] = useState(false);

  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    async function loadTickets() {
      try {
        setLoading(true);
        setError("");

        const data = await getAllTickets();

        setTickets(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    loadTickets();
  }, []);

  async function handleSearch(event) {
    event.preventDefault();

    setError("");
    setMessage("");

    try {
      setSearching(true);

      const data = await getTicketsByCustomerEmail(email);

      setTickets(data);

      if (data.length === 0) {
        setMessage("No tickets found for this email address.");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setSearching(false);
    }
  }

  return (
    <main className="page-container">
      <div className="section-heading">
        <span>Digital Access</span>

        <h1>My Tickets</h1>

        <p>
          View your EventSphere digital tickets, booking references,
          ticket status and QR information.
        </p>
      </div>

      <section className="ticket-search-card">
        <form
          className="ticket-search-form"
          onSubmit={handleSearch}
        >
          <div className="form-group">
            <label htmlFor="ticketEmail">
              Search by Customer Email
            </label>

            <input
              id="ticketEmail"
              type="email"
              placeholder="Enter customer email"
              value={email}
              onChange={(event) =>
                setEmail(event.target.value)
              }
              required
            />
          </div>

          <button
            type="submit"
            className="primary-btn ticket-search-btn"
            disabled={searching}
          >
            {searching
              ? "Searching..."
              : "Find Tickets"}
          </button>
        </form>
      </section>

      {loading && (
        <div className="message-card">
          Loading tickets...
        </div>
      )}

      {error && (
        <div className="message-card error-message">
          {error}
        </div>
      )}

      {message && (
        <div className="message-card">
          {message}
        </div>
      )}

      {!loading && !error && tickets.length === 0 && !message && (
        <div className="message-card">
          No digital tickets are available at the moment.
        </div>
      )}

      {tickets.length > 0 && (
        <section className="tickets-grid">
          {tickets.map((ticket) => (
            <article
              className="ticket-card"
              key={ticket.id}
            >
              <div className="ticket-card-header">
                <div>
                  <span className="ticket-label">
                    EVENTSPHERE DIGITAL TICKET
                  </span>

                  <h2>
                    {ticket.customerName || "Event Ticket"}
                  </h2>
                </div>

                <span
                  className={
                    ticket.ticketStatus === "CANCELLED"
                      ? "ticket-status ticket-cancelled"
                      : "ticket-status ticket-active"
                  }
                >
                  {ticket.ticketStatus || "ACTIVE"}
                </span>
              </div>

              <div className="ticket-body">
                <div className="ticket-information">
                  <div>
                    <span>Ticket ID</span>
                    <strong>
                      {ticket.id || "Not available"}
                    </strong>
                  </div>

                  <div>
                    <span>Booking ID</span>
                    <strong>
                      {ticket.bookingId || "Not available"}
                    </strong>
                  </div>

                  <div>
                    <span>Event ID</span>
                    <strong>
                      {ticket.eventId || "Not available"}
                    </strong>
                  </div>

                  <div>
                    <span>Customer Email</span>
                    <strong>
                      {ticket.customerEmail || "Not available"}
                    </strong>
                  </div>

                  <div>
                    <span>Ticket Count</span>
                    <strong>
                      {ticket.ticketCount ?? "Not available"}
                    </strong>
                  </div>
                </div>

                <div className="ticket-qr-section">
                  <span>QR Reference</span>

                  {ticket.qrCode ? (
                    <div className="qr-placeholder">
                      <strong>QR</strong>

                      <span>
                        {ticket.qrCode}
                      </span>
                    </div>
                  ) : (
                    <div className="qr-placeholder">
                      <strong>QR</strong>

                      <span>
                        QR code unavailable
                      </span>
                    </div>
                  )}
                </div>
              </div>
            </article>
          ))}
        </section>
      )}
    </main>
  );
}

export default Tickets;