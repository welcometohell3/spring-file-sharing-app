import React, { useState } from "react";
import axios from "axios";

function FileUpload() {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleFileUpload = async () => {
    if (!file) {
      setMessage("Please select a file to upload");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", 1); // Предположим, что ID пользователя = 1

    try {
      const authHeader = localStorage.getItem("authHeader"); // Получите authHeader из localStorage
      const response = await axios.post(
        "http://localhost:8080/app/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: authHeader, // Передайте authHeader
          },
        }
      );

      if (response.status === 200) {
        setMessage("File uploaded successfully");
      } else {
        setMessage("File upload failed");
      }
    } catch (error) {
      setMessage("File upload failed");
    }
  };

  return (
    <div>
      <h1>Upload File</h1>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleFileUpload}>Upload</button>
      <p>{message}</p>
    </div>
  );
}

export default FileUpload;
