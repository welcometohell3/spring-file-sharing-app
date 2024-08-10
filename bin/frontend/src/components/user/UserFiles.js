import React, { useState, useEffect } from "react";
import {
  Button,
  List,
  Message,
  Modal,
  Dropdown,
  Segment,
} from "semantic-ui-react";
import { fileApi } from "../file/FileApi";
import { useAuth } from "../context/AuthContext";
import { handleLogError } from "../misc/Helpers";
import "./UserFiles.css";

function UserFiles() {
  const [files, setFiles] = useState([]);
  const [error, setError] = useState(null);
  const [shareModalOpen, setShareModalOpen] = useState(false);
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [currentFile, setCurrentFile] = useState(null);
  const [shareUsername, setShareUsername] = useState("");
  const [users, setUsers] = useState([]);
  const [successMessage, setSuccessMessage] = useState("");
  const [deleteFileId, setDeleteFileId] = useState(null);
  const Auth = useAuth();
  const user = Auth.getUser();

  useEffect(() => {
    const fetchFiles = async () => {
      try {
        const response = await fileApi.getUserFiles(user);
        setFiles(response.data);
      } catch (error) {
        handleLogError(error);
        setError("Failed to load files");
      }
    };

    fetchFiles();
  }, [user]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fileApi.getUsers(user);
        setUsers(response.data);
      } catch (error) {
        handleLogError(error);
        setError("Failed to load users");
      }
    };

    fetchUsers();
  }, [user]);

  const handleDownload = async (fileId, fileName) => {
    try {
      const response = await fileApi.downloadFile(user, fileId);
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", fileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      handleLogError(error);
      setError("Failed to download file");
    }
  };

  const handleShareClick = (file) => {
    setCurrentFile(file);
    setShareModalOpen(true);
  };

  const handleShareSubmit = async () => {
    try {
      await fileApi.shareFile(user, currentFile.id, shareUsername);
      setShareModalOpen(false);
      setCurrentFile(null);
      setShareUsername("");
      setSuccessMessage("File shared successfully with " + shareUsername);
    } catch (error) {
      handleLogError(error);
      setError("Failed to share file");
    }
  };

  const handleDeleteClick = (file) => {
    setCurrentFile(file);
    setDeleteModalOpen(true);
  };

  const handleDeleteSubmit = async () => {
    try {
      await fileApi.deleteFile(user, currentFile.id);
      setDeleteModalOpen(false);
      setCurrentFile(null);
      setSuccessMessage("File deleted successfully");
      // Refresh file list after deletion
      const response = await fileApi.getUserFiles(user);
      setFiles(response.data);
    } catch (error) {
      handleLogError(error);
      setError("Failed to delete file");
    }
  };

  const userOptions = users.map((user) => ({
    key: user.id,
    text: user.username,
    value: user.username,
  }));

  return (
    <Segment padded="very" className="user-files-container">
      {error && <Message error header="Error" content={error} />}
      {successMessage && (
        <Message success header="Success" content={successMessage} />
      )}
      <List divided relaxed className="file-list">
        {files.map((file) => (
          <List.Item key={file.id} className="file-list-item">
            <List.Content className="file-actions" floated="right">
              <Button
                onClick={() => handleDownload(file.id, file.name)}
                color="blue"
                className="file-action-button"
              >
                Download
              </Button>

              <Button
                onClick={() => handleShareClick(file)}
                color="teal"
                className="file-action-button"
              >
                Share
              </Button>

              <Button
                onClick={() => handleDeleteClick(file)}
                color="red"
                className="file-action-button"
              >
                Delete
              </Button>
            </List.Content>
            <List.Content>{file.name}</List.Content>
          </List.Item>
        ))}
      </List>

      <Modal
        open={shareModalOpen}
        onClose={() => setShareModalOpen(false)}
        size="small"
      >
        <Modal.Header>Share File</Modal.Header>
        <Modal.Content>
          <p>Share this file with:</p>
          <Dropdown
            placeholder="Select User"
            fluid
            selection
            options={userOptions}
            value={shareUsername}
            onChange={(e, { value }) => setShareUsername(value)}
          />
        </Modal.Content>
        <Modal.Actions>
          <Button color="red" onClick={() => setShareModalOpen(false)}>
            Cancel
          </Button>
          <Button color="green" onClick={handleShareSubmit}>
            Share
          </Button>
        </Modal.Actions>
      </Modal>

      <Modal
        open={deleteModalOpen}
        onClose={() => setDeleteModalOpen(false)}
        size="small"
      >
        <Modal.Header>Delete File</Modal.Header>
        <Modal.Content>
          <p>Are you sure you want to delete the file "{currentFile?.name}"?</p>
        </Modal.Content>
        <Modal.Actions>
          <Button color="red" onClick={() => setDeleteModalOpen(false)}>
            Cancel
          </Button>
          <Button color="green" onClick={handleDeleteSubmit}>
            Delete
          </Button>
        </Modal.Actions>
      </Modal>
    </Segment>
  );
}

export default UserFiles;
