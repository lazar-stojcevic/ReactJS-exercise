import { AppBar, Link, Toolbar, Typography } from "@mui/material";

function NavBar() {
  return (
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
        <Typography variant="h6" component="div">
          <Link
            href="/profile"
            color="inherit"
            underline="none"
            sx={{ fontWeight: "bold" }}
          >
            Profile
          </Link>
        </Typography>
      </Toolbar>
    </AppBar>
  );
}

export default NavBar;
