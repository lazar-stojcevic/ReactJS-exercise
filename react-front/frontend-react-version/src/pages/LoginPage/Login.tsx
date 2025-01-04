import { useState } from 'react';
import './Login.css'
import axios from 'axios';

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
  
    const handleSubmit = async (event: any) => {
        event.preventDefault();
        setError(""); // Clear previous error message
    
        try {
          const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        };
          const response = await axios.post("http://localhost:8080/auth/login", {
            email,
            password,
          }, {headers});
          console.log("Login successful:", response.data);
          // Save token or handle success response
          localStorage.setItem("token", response.data.accessToken);
          localStorage.setItem("user-id", response.data.id);
    
        } catch (err) {
          console.error("Error:", err);
          setError(err.response?.data?.message || "Login failed. Please try again.");
        }
      };
  
    return (
      <div style={{ maxWidth: "400px", margin: "0 auto", padding: "20px" }}>
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: "10px" }}>
            <label htmlFor="email" style={{ display: "block", marginBottom: "5px" }}>Email</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label htmlFor="password" style={{ display: "block", marginBottom: "5px" }}>Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
              required
            />
          </div>
          {error && <p style={{ color: "red" }}>{error}</p>}
          <button type="submit" style={{ padding: "10px 20px", backgroundColor: "blue", color: "white", border: "none", cursor: "pointer" }}>
            Login
          </button>
        </form>
      </div>
    );
  };
  
  export default Login;