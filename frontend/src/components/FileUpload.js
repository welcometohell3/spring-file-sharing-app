import React, { useState } from "react";
import axios from "axios";
import useCurrentUser from "../hooks/useCurrentUser";

function FileUpload() {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const { currentUser } = useCurrentUser();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", currentUser.id);

    try {
      const authHeader = localStorage.getItem("authHeader");
      const response = await axios.post(
        "http://localhost:8080/files/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: authHeader,
          },
        }
      );
      setMessage(response.data);
    } catch (error) {
      setMessage("File upload failed");
    }
  };

  return (
    <div>
      <h2>Upload File </h2>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={(e) => setFile(e.target.files[0])} />

        <button type="submit">Upload</button>
      </form>
      <p>{message}</p>
    </div>
  );
}

export default FileUpload;
