import React, { useState } from "react";
import axios from "axios";

function Login({ setIsAuthenticated, setUsername }) {
  const [username, setLocalUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Создаем заголовок авторизации
      const authHeader = "Basic " + btoa(`${username}:${password}`);

      // Отправляем запрос на получение списка пользователей для проверки логина
      const response = await axios.get("http://localhost:8080/user/all-users", {
        headers: {
          Authorization: authHeader,
        },
      });

      if (response.status === 200) {
        // Сохраняем заголовок авторизации в localStorage
        localStorage.setItem("authHeader", authHeader);

        // Отправляем запрос на получение текущего пользователя
        const userResponse = await axios.get(
          "http://localhost:8080/user/current-user",
          {
            headers: {
              Authorization: authHeader,
            },
          }
        );

        // Устанавливаем сообщение о успешном входе и обновляем состояние приложения
        setMessage(`Login successful. Welcome, ${userResponse.data.name}`);
        setUsername(userResponse.data.name);
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
          onChange={(e) => setLocalUsername(e.target.value)}
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
