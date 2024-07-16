import React, { useState } from "react";
import axios from "axios";
import useUsers from "../hooks/useUsers";

function FileShare() {
  const [fileId, setFileId] = useState("");
  const [recipientUsername, setRecipientUsername] = useState("");
  const [message, setMessage] = useState("");

  const { users, loading } = useUsers();

  const handleShare = async () => {
    try {
      const authHeader = localStorage.getItem("authHeader");
      const response = await axios.post(
        "http://localhost:8080/files/share",
        {
          fileId,
          recipientUsername,
        },
        {
          headers: {
            Authorization: authHeader,
          },
        }
      );
      setMessage(response.data);
    } catch (error) {
      setMessage("File sharing failed");
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>Share File</h2>
      <input
        type="text"
        placeholder="File ID"
        value={fileId}
        onChange={(e) => setFileId(e.target.value)}
      />
      <select
        value={recipientUsername}
        onChange={(e) => setRecipientUsername(e.target.value)}
      >
        <option value="">Select recipient...</option>
        {users.map((user) => (
          <option key={user.id} value={user.name}>
            {user.name}
          </option>
        ))}
      </select>
      <button onClick={handleShare}>Share</button>
      <p>{message}</p>
    </div>
  );
}

export default FileShare;
