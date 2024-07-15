import React, { useState } from "react";
import axios from "axios";

function ShareFile() {
  const [fileId, setFileId] = useState("");
  const [recipientUsername, setRecipientUsername] = useState("");
  const [message, setMessage] = useState("");

  const handleFileShare = async () => {
    try {
      const response = await axios.post("http://localhost:8080/app/share", {
        fileId,
        recipientUsername,
      });
      setMessage(response.data);
    } catch (error) {
      setMessage("File share failed");
    }
  };

  return (
    <div>
      <h1>Share File</h1>
      <input
        type="text"
        placeholder="File ID"
        value={fileId}
        onChange={(e) => setFileId(e.target.value)}
      />
      <input
        type="text"
        placeholder="Recipient Username"
        value={recipientUsername}
        onChange={(e) => setRecipientUsername(e.target.value)}
      />
      <button onClick={handleFileShare}>Share</button>
      <p>{message}</p>
    </div>
  );
}

export default ShareFile;
