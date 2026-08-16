import { Link } from "react-router-dom";

function Home() {
  return (
    <main className="home-page">
      <section className="hero-section">
        <div className="hero-content">
          <span className="hero-badge">EventSphere Platform</span>

          <h1>
            Discover, Book and Experience
            <span> Amazing Events</span>
          </h1>

          <p>
            Explore upcoming events, make bookings, manage your reservations,
            and access your digital tickets from one modern platform.
          </p>

          <div className="hero-actions">
            <Link to="/events" className="primary-btn">
              Explore Events
            </Link>

            <Link to="/book" className="secondary-btn">
              Book Now
            </Link>
          </div>
        </div>

        <div className="hero-card">
          <div className="hero-card-top">
            <span>Featured Experience</span>
            <span className="status-badge">LIVE</span>
          </div>

          <h3>EventSphere 2026</h3>

          <p>
            Your complete digital event experience from discovery to booking
            and ticket access.
          </p>

          <div className="hero-stats">
            <div>
              <strong>01</strong>
              <span>Discover</span>
            </div>

            <div>
              <strong>02</strong>
              <span>Book</span>
            </div>

            <div>
              <strong>03</strong>
              <span>Attend</span>
            </div>
          </div>
        </div>
      </section>

      <section className="features-section">
        <div className="section-heading">
          <span>Everything in One Place</span>

          <h2>Your Event Journey Made Simple</h2>

          <p>
            EventSphere connects event discovery, bookings, and digital
            tickets through one seamless platform.
          </p>
        </div>

        <div className="feature-grid">
          <article className="feature-card">
            <div className="feature-number">01</div>

            <h3>Discover Events</h3>

            <p>
              Browse available events, check venues, prices, and ticket
              availability before making your decision.
            </p>

            <Link to="/events">Explore Events</Link>
          </article>

          <article className="feature-card">
            <div className="feature-number">02</div>

            <h3>Easy Booking</h3>

            <p>
              Reserve your tickets using a simple booking experience with
              automatic total amount calculation.
            </p>

            <Link to="/book">Create Booking</Link>
          </article>

          <article className="feature-card">
            <div className="feature-number">03</div>

            <h3>Manage Bookings</h3>

            <p>
              Find your bookings, check booking status, and cancel
              reservations whenever required.
            </p>

            <Link to="/my-bookings">My Bookings</Link>
          </article>

          <article className="feature-card">
            <div className="feature-number">04</div>

            <h3>Digital Tickets</h3>

            <p>
              Access generated tickets and QR information for a convenient
              event entry experience.
            </p>

            <Link to="/tickets">View Tickets</Link>
          </article>
        </div>
      </section>

      <section className="cta-section">
        <div>
          <span>Ready for your next experience?</span>

          <h2>Find an event and reserve your place today.</h2>
        </div>

        <Link to="/events" className="primary-btn">
          Browse Events
        </Link>
      </section>
    </main>
  );
}

export default Home;