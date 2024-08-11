import axios from "axios";
import { config } from "../Constants";

export const fileApi = {
  authenticate,
  signup,
  getUsers,
  deleteUser,
  uploadFile,
  downloadFile,
  getUserFiles,
  shareFile,
  deleteFile,
};

function authenticate(username, password) {
  return instance.post(
    "/auth/authenticate",
    { username, password },
    {
      headers: { "Content-type": "application/json" },
    }
  );
}

function signup(user) {
  return instance.post("/auth/signup", user, {
    headers: { "Content-type": "application/json" },
  });
}

function getUsers(user) {
  return instance.get("/api/users", getAuthHeaders(user));
}

function deleteUser(user, username) {
  return instance.delete(`/api/users/${username}`, {
    headers: { Authorization: basicAuth(user) },
  });
}
function deleteFile(user, fileId) {
  return instance.delete(`/api/files/${fileId}`, getAuthHeaders(user));
}

function uploadFile(user, file) {
  const formData = new FormData();
  formData.append("file", file);

  return instance.post("/api/files/upload", formData, {
    headers: {
      Authorization: basicAuth(user),
      "Content-Type": "multipart/form-data",
    },
  });
}

function shareFile(user, fileId, shareUsername) {
  return instance.post(
    `/api/files/${fileId}/share`,
    { username: shareUsername },
    getAuthHeaders(user)
  );
}

function getUserFiles(user) {
  return instance.get("/api/files", getAuthHeaders(user));
}

function downloadFile(user, fileId) {
  return instance.get(`/api/files/${fileId}`, {
    headers: { Authorization: basicAuth(user) },
    responseType: "blob",
  });
}

function getAuthHeaders(user) {
  return {
    headers: { Authorization: basicAuth(user) },
  };
}

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

function basicAuth(user) {
  return `Basic ${user.authdata}`;
}
