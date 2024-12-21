import { BrowserRouter, Route, Routes } from "react-router-dom";
import About from "./pages/About";
import Home from "./pages/Home";
import Context from "./pages/Context";
import Login from "./pages/LoginPage/Login";
import { AppBar, Box, Link, Toolbar, Typography } from "@mui/material";

function App() {
  return (
    <div>
      <AppBar position="fixed" sx={{ width: "100vw" }}>
        <Toolbar sx={{ justifyContent: "space-around", padding: 0 }}>
          <Typography variant="h6" component="div">
            <Link
              href="/"
              color="inherit"
              underline="none"
              sx={{ fontWeight: "bold" }}
            >
              Home
            </Link>
          </Typography>
          <Typography variant="h6" component="div">
            <Link
              href="/about"
              color="inherit"
              underline="none"
              sx={{ fontWeight: "bold" }}
            >
              About
            </Link>
          </Typography>
          <Typography variant="h6" component="div">
            <Link
              href="/contact"
              color="inherit"
              underline="none"
              sx={{ fontWeight: "bold" }}
            >
              Contact
            </Link>
          </Typography>
          <Typography variant="h6" component="div">
            <Link
              href="/login"
              color="inherit"
              underline="none"
              sx={{ fontWeight: "bold" }}
            >
              Login
            </Link>
          </Typography>
        </Toolbar>
      </AppBar>
      <BrowserRouter>
        <Box>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="/contact" element={<Context />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </Box>
      </BrowserRouter>
    </div>
  );
}

export default App;
