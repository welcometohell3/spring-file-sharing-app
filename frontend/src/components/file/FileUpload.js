import React, { useState } from "react";
import { Button, Form, Message, Progress, Segment } from "semantic-ui-react";
import { fileApi } from "./FileApi";
import { useAuth } from "../context/AuthContext";
import { handleLogError } from "../misc/Helpers";
import "./FileUpload.css";

function FileUpload() {
  const [file, setFile] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);
  const Auth = useAuth();
  const user = Auth.getUser();

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      return;
    }

    setIsLoading(true);
    setError(null);
    setSuccess(false);
    setProgress(0);

    try {
      const config = {
        onUploadProgress: (progressEvent) => {
          const percentCompleted = Math.round(
            (progressEvent.loaded * 100) / progressEvent.total
          );
          setProgress(percentCompleted);
        },
      };
      await fileApi.uploadFile(user, file, config);
      setSuccess(true);
    } catch (error) {
      handleLogError(error);
      setError("Failed to upload file");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Segment padded="very" className="file-upload-container">
      <Form loading={isLoading} success={success} error={!!error}>
        <Form.Input
          type="file"
          label="Select File"
          onChange={handleFileChange}
        />
        <div className="button-group">
          <Button
            type="button"
            onClick={handleUpload}
            primary
            disabled={isLoading}
          >
            Upload
          </Button>
          {isLoading && (
            <Progress
              percent={progress}
              indicating
              progress
              style={{ width: "100%" }}
            />
          )}
        </div>
        {/* <Message
          success
          header="File Uploaded"
          content="File uploaded successfully"
          hidden={!success}
        />
        <Message error header="Error" content={error} hidden={!error} /> */}
      </Form>
    </Segment>
  );
}

export default FileUpload;
