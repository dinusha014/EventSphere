import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-container">

        <NavLink to="/" className="navbar-logo">
          <span className="logo-icon">E</span>
          <span>EventSphere</span>
        </NavLink>

        <div className="navbar-links">
          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Home
          </NavLink>

          <NavLink
            to="/events"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Events
          </NavLink>

          <NavLink
            to="/book"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Book Now
          </NavLink>

          <NavLink
            to="/my-bookings"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            My Bookings
          </NavLink>

          <NavLink
            to="/tickets"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Tickets
          </NavLink>
        </div>

      </div>
    </nav>
  );
}

export default Navbar;