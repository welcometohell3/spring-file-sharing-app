import { useState, useEffect } from "react";
import axios from "axios";

const useCurrentUser = () => {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    const fetchCurrentUser = async () => {
      const authHeader = localStorage.getItem("authHeader");
      const response = await axios.get(
        "http://localhost:8080/user/current-user",
        {
          headers: {
            Authorization: authHeader,
          },
        }
      );
      setCurrentUser(response.data);
    };

    fetchCurrentUser();
  }, []);

  return { currentUser };
};

export default useCurrentUser;
