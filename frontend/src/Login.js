import React, { useState } from "react";
import axios from "axios";

function Login({ setIsAuthenticated }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const authHeader = "Basic " + btoa(`${username}:${password}`);
      const response = await axios.get("http://localhost:8080/api/users", {
        headers: {
          Authorization: authHeader,
        },
      });
      if (response.status === 200) {
        setMessage("Login successful");
        localStorage.setItem("authHeader", authHeader);
        setIsAuthenticated(true);
      } else {
        setMessage("Login failed");
      }
    } catch (error) {
      setMessage("Login failed");
    }
  };

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
      <p>{message}</p>
    </div>
  );
}

export default Login;
