import React, { useState } from "react";
import axios from "axios";

function FileDownload() {
  const [fileId, setFileId] = useState("");
  const [message, setMessage] = useState("");

  const handleDownload = async () => {
    try {
      const authHeader = localStorage.getItem("authHeader");
      const response = await axios.get(
        `http://localhost:8080/files/download/${fileId}`,
        {
          responseType: "blob",
          headers: {
            Authorization: authHeader,
          },
        }
      );

      // Создаем ссылку для скачивания файла
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `file_${fileId}.txt`);
      document.body.appendChild(link);
      link.click();
      link.remove();
      setMessage(`File ${fileId} downloaded successfully`);
    } catch (error) {
      setMessage(`Failed to download file ${fileId}`);
    }
  };

  return (
    <div>
      <h2>Download File</h2>
      <input
        type="text"
        placeholder="File ID"
        value={fileId}
        onChange={(e) => setFileId(e.target.value)}
      />
      <button onClick={handleDownload}>Download</button>
      <p>{message}</p>
    </div>
  );
}

export default FileDownload;
