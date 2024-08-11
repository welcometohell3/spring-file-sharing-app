import * as React from "react";
import * as ReactDOM from "react-dom";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./components/home/Login";
import Signup from "./components/home/Signup";
import UserPage from "./components/user/UserPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <UserPage />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/signup",
    element: <Signup />,
  },
  {
    path: "*",
    element: <UserPage />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <RouterProvider router={router} />
);
