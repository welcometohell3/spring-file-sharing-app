import React from "react";
import { Container } from "semantic-ui-react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import FileUpload from "../file/FileUpload";
import UserFiles from "./UserFiles";

function UserPage() {
  const Auth = useAuth();
  const user = Auth.getUser();
  const isUser = user.role === "USER";

  if (!isUser) {
    return <Navigate to="/login" />;
  }

  return (
    <Container>
      <FileUpload />
      <UserFiles />
    </Container>
  );
}

export default UserPage;