import React from "react";
import useCurrentUser from "../hooks/useCurrentUser";
import FileDownload from "./FileDownload";
import FileUpload from "./FileUpload";
import FileShare from "./FileShare";

function Profile() {
  const { currentUser } = useCurrentUser();

  if (!currentUser) return null;

  return (
    <div>
      <h2>Welcome, {currentUser.name}!</h2>
      <FileUpload />
      <FileDownload />
      <FileShare />
    </div>
  );
}

export default Profile;
