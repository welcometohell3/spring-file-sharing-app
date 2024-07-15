import React, { useState, useEffect } from "react";
import axios from "axios";

function FileList() {
  const [files, setFiles] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchFiles = async () => {
      try {
        const response = await axios.get("http://localhost:8080/app/files");
        setFiles(response.data);
      } catch (error) {
        setMessage("Failed to fetch files");
      }
    };

    fetchFiles();
  }, []);

  const handleFileDownload = async (fileId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/app/download/${fileId}`,
        {
          responseType: "blob",
        }
      );

      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "file"); // Установите имя файла
      document.body.appendChild(link);
      link.click();
    } catch (error) {
      setMessage("File download failed");
    }
  };

  const handleFileDelete = async (fileId) => {
    try {
      await axios.delete(`http://localhost:8080/app/delete/${fileId}`);
      setFiles(files.filter((file) => file.id !== fileId));
      setMessage("File deleted successfully");
    } catch (error) {
      setMessage("File deletion failed");
    }
  };

  return (
    <div>
      <h1>File List</h1>
      {message && <p>{message}</p>}
      <ul>
        {files.map((file) => (
          <li key={file.id}>
            {file.name}
            <button onClick={() => handleFileDownload(file.id)}>
              Download
            </button>
            <button onClick={() => handleFileDelete(file.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default FileList;
