import { useState } from "react";
import {
  getUserBookings,
  cancelBooking,
} from "../services/bookingApi";

function MyBookings() {
  const [email, setEmail] = useState("");
  const [bookings, setBookings] = useState([]);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  async function handleSearch(event) {
    event.preventDefault();

    setError("");
    setMessage("");
    setBookings([]);

    try {
      setLoading(true);

      const data = await getUserBookings(email);

      setBookings(data);

      if (data.length === 0) {
        setMessage("No bookings found for this email address.");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function handleCancel(bookingId) {
    const confirmed = window.confirm(
      "Are you sure you want to cancel this booking?"
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");
      setMessage("");

      const updatedBooking = await cancelBooking(bookingId);

      setBookings((currentBookings) =>
        currentBookings.map((booking) =>
          booking.id === bookingId
            ? updatedBooking
            : booking
        )
      );

      setMessage("Booking cancelled successfully.");
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <main className="page-container">

      <div className="section-heading">
        <span>Manage Reservations</span>

        <h1>My Bookings</h1>

        <p>
          Enter your email address to find and manage
          your EventSphere bookings.
        </p>
      </div>

      <section className="booking-search-card">

        <form
          className="booking-search-form"
          onSubmit={handleSearch}
        >
          <div className="form-group">
            <label htmlFor="bookingEmail">
              Email Address
            </label>

            <input
              id="bookingEmail"
              type="email"
              placeholder="Enter your booking email"
              value={email}
              onChange={(event) =>
                setEmail(event.target.value)
              }
              required
            />
          </div>

          <button
            type="submit"
            className="primary-btn search-booking-btn"
            disabled={loading}
          >
            {loading
              ? "Searching..."
              : "Find My Bookings"}
          </button>
        </form>

      </section>

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

      {bookings.length > 0 && (
        <section className="bookings-grid">

          {bookings.map((booking) => (
            <article
              className="booking-card"
              key={booking.id}
            >

              <div className="booking-card-top">

                <div>
                  <span className="booking-id-label">
                    Booking ID
                  </span>

                  <strong className="booking-id">
                    {booking.id}
                  </strong>
                </div>

                <span
                  className={
                    booking.bookingStatus === "CANCELLED"
                      ? "booking-status cancelled-status"
                      : "booking-status confirmed-status"
                  }
                >
                  {booking.bookingStatus}
                </span>

              </div>

              <div className="booking-details-grid">

                <div>
                  <span>Event ID</span>
                  <strong>{booking.eventId}</strong>
                </div>

                <div>
                  <span>Customer</span>
                  <strong>{booking.customerName}</strong>
                </div>

                <div>
                  <span>Email</span>
                  <strong>{booking.customerEmail}</strong>
                </div>

                <div>
                  <span>Tickets</span>
                  <strong>{booking.ticketCount}</strong>
                </div>

                <div>
                  <span>Ticket Price</span>
                  <strong>
                    LKR{" "}
                    {Number(
                      booking.ticketPrice
                    ).toLocaleString()}
                  </strong>
                </div>

                <div>
                  <span>Total Amount</span>
                  <strong>
                    LKR{" "}
                    {Number(
                      booking.totalAmount
                    ).toLocaleString()}
                  </strong>
                </div>

              </div>

              <div className="booking-card-footer">

                <span>
                  {booking.bookingDate
                    ? new Date(
                        booking.bookingDate
                      ).toLocaleString()
                    : "Booking date unavailable"}
                </span>

                {booking.bookingStatus !== "CANCELLED" && (
                  <button
                    type="button"
                    className="cancel-booking-btn"
                    onClick={() =>
                      handleCancel(booking.id)
                    }
                  >
                    Cancel Booking
                  </button>
                )}

              </div>

            </article>
          ))}

        </section>
      )}

    </main>
  );
}

export default MyBookings;