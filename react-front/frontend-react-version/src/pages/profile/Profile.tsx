import axios from "axios";
import { useEffect, useState } from "react";

const Profile = () => {
    const [profile, setProfile] = useState({} as any);

  const getCurrentUser = async () => {
    const currentUserId = localStorage.getItem("user-id");
    const token = localStorage.getItem("token");
    const headers = {
      Accept: "application/json",
      "Content-Type": "application/json",
      'Authorization': 'Bearer ' + token
    };
    await axios.get(
      `http://localhost:8080/customer/${currentUserId}`,
      { headers }
    ).then(res => {
        setProfile(res.data);
    });
  };

  useEffect(() => { getCurrentUser()}, []); 

  return <div>
    {profile?.firstname} {profile?.lastName}
    </div>;
};

export default Profile;

