import CreatePoll from "./components/CreatePoll";
import ViewPolls from "./components/ViewPolls";
import { useState, useEffect } from "react";
import { Poll } from "./types";
import axios from "axios";

export default function MyApp() {
  // const url: String = "http://localhost:8080"; for testing
  const url: String = ""; // for local relative links
  const [polls, setPolls] = useState<Poll[]>([]);
  const [username, setUsername] = useState<string>("");

  const fetchPolls = async () => {
    try {
      const promise = axios.get(`${url}/getPolls`);
      promise.then((response) => {
        console.log(response.data);
        setPolls(response.data);
      });
    } catch (error) {
      console.error("Error fetching polls", error);
    }
  };

  const createPoll = async (poll: Poll) => {
    try {
      const promise = await axios.post(`${url}/createPoll/${username}`, poll);
      await fetchPolls();
    } catch (error) {}
  };

  const createVote = async (pollId: number, presentationOrder: number) => {
    try {
      const promise = await axios.post(
        `${url}/createVote/${pollId}/${presentationOrder}/${username}`
      );
      await fetchPolls();
    } catch (error) {}
  };

  useEffect(() => {
    fetchPolls();
  }, []);

  return (
    <div>
      <h1>Polling App</h1>
      <p>Available users: Admin, Sam, Tom</p>
      <p>Username</p>
      <input
        type="text"
        onChange={(event) => setUsername(event.target.value)}
      />
      <CreatePoll username={username} onCreatePoll={createPoll} />
      <ViewPolls polls={polls} onCreateVote={createVote} />
    </div>
  );
}
