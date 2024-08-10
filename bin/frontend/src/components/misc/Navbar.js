import React from "react";
import { Link } from "react-router-dom";
import { Container, Menu } from "semantic-ui-react";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const { getUser, userIsAuthenticated, userLogout } = useAuth();

  const logout = () => {
    userLogout();
  };

  const enterMenuStyle = () => {
    return userIsAuthenticated() ? { display: "none" } : { display: "block" };
  };

  const logoutMenuStyle = () => {
    return userIsAuthenticated() ? { display: "block" } : { display: "none" };
  };

  const getUserName = () => {
    const user = getUser();
    return user ? user.name : "";
  };

  return (
    <Menu inverted stackable size="massive" style={{ borderRadius: 0 }}>
      <Container>
        <Menu.Item header>File Sharing App</Menu.Item>

        <Menu.Menu position="right">
          <Menu.Item as={Link} to="/login" style={enterMenuStyle()}>
            Sign in
          </Menu.Item>
          <Menu.Item as={Link} to="/signup" style={enterMenuStyle()}>
            Sign up
          </Menu.Item>
          <Menu.Item
            header
            style={logoutMenuStyle()}
          >{`Hey,  ${getUserName()}`}</Menu.Item>
          <Menu.Item
            as={Link}
            to="/"
            style={logoutMenuStyle()}
            onClick={logout}
          >
            Log out
          </Menu.Item>
        </Menu.Menu>
      </Container>
    </Menu>
  );
}

export default Navbar;
