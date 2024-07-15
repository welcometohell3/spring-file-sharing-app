import React, { useState, useEffect } from "react";
import Login from "./Login";
import UserList from "./UserList";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const authHeader = localStorage.getItem("authHeader");
    if (authHeader) {
      setIsAuthenticated(true);
    }
  }, []);

  return (
    <div>
      <h1>File Sharing App</h1>
      {isAuthenticated ? (
        <UserList />
      ) : (
        <Login setIsAuthenticated={setIsAuthenticated} />
      )}
    </div>
  );
}

export default App;
