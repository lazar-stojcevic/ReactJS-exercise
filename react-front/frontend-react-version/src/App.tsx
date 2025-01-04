import { BrowserRouter, Route, Routes } from "react-router-dom";
import About from "./pages/About";
import Home from "./pages/Home";
import Context from "./pages/Context";
import Login from "./pages/LoginPage/Login";
import { Box } from "@mui/material";
import NavBar from "./shared/nav-bar/NavBar";
import Profile from "./pages/profile/Profile";

function App() {
  return (
    <div>
      <NavBar></NavBar>
      <BrowserRouter>
        <Box>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="/contact" element={<Context />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
          </Routes>
        </Box>
      </BrowserRouter>
    </div>
  );
}

export default App;
