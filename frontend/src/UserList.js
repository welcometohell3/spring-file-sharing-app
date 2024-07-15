import React, { useState, useEffect } from "react";
import axios from "axios";

function UserList() {
  const [users, setUsers] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const authHeader = localStorage.getItem("authHeader");
        const response = await axios.get("http://localhost:8080/api/users", {
          headers: {
            Authorization: authHeader,
          },
        });
        setUsers(response.data);
      } catch (error) {
        setMessage("Failed to fetch users");
        console.error("Error fetching users", error);
      }
    };

    fetchUsers();
  }, []); // Пустой массив зависимостей

  return (
    <div>
      <h1>User List</h1>
      {message && <p>{message}</p>}
      <ul>
        {users.map((user) => (
          <li key={user.id}>{user.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default UserList;
