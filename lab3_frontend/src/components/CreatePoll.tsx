import axios from "axios";
import { useState, useEffect } from "react";
import { Options, Poll } from "../types";

interface CreatePollProps {
  username: string;
  onCreatePoll: (newPoll: Poll) => Promise<void>;
}

export default function CreatePoll({
  username,
  onCreatePoll,
}: CreatePollProps) {
  const [pollId, setPollId] = useState<number>();
  const [pollQuestion, setPollQuestion] = useState<string>();
  const [pollOptions, setPollOptions] = useState<Options[]>([
    { caption: "", presentationOrder: 1, voteUsernames: [] },
    { caption: "", presentationOrder: 2, voteUsernames: [] },
  ]);

  const handleAddOptionClick = () => {
    setPollOptions((prev) => [
      ...prev,
      { caption: "", presentationOrder: prev.length + 1, voteUsernames: [] },
    ]);
  };

  const handleCreatePollClick = () => {
    if (pollId) {
      let sendPoll: Poll = {
        id: pollId,
        question: pollQuestion,
        options: pollOptions,
        username: username,
        publishedAt: new Date().toISOString(),
        validUntil: new Date().toISOString(),
      };
      onCreatePoll(sendPoll);
    }
  };

  return (
    <div>
      <h2>Create Poll</h2>
      <p>Poll id</p>
      <input
        type="number"
        onChange={(event) => setPollId(Number(event.target.value))}
      />
      <p>Poll question</p>
      <input
        type="text"
        onChange={(event) => setPollQuestion(event.target.value)}
      />
      <ul id="pollOptions">
        {pollOptions.map((option, index) => (
          <li key={option.presentationOrder}>
            <p>Option caption {index + 1}</p>
            <input
              type="text"
              value={option.caption}
              onChange={(event) =>
                setPollOptions((prev) =>
                  prev.map((opt, i) =>
                    i === index ? { ...opt, caption: event.target.value } : opt
                  )
                )
              }
            />
          </li>
        ))}
      </ul>
      <button onClick={handleAddOptionClick}>Add option</button>
      <button onClick={handleCreatePollClick}>Create poll</button>
    </div>
  );
}
