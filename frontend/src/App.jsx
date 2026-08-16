import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Events from "./pages/Events";
import CreateBooking from "./pages/CreateBooking";
import MyBookings from "./pages/MyBookings";
import Tickets from "./pages/Tickets";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/events" element={<Events />} />
        <Route path="/book" element={<CreateBooking />} />
        <Route path="/my-bookings" element={<MyBookings />} />
        <Route path="/tickets" element={<Tickets />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;