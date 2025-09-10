import { Options, Poll } from "../types";

interface ViewPollsProps {
  polls: Poll[];
  onCreateVote: (pollId: number, presentationOrder: number) => Promise<void>;
}

export default function ViewPolls({ polls, onCreateVote }: ViewPollsProps) {
  return (
    <div>
      <h2>Created polls</h2>
      <ul>
        {polls.map((poll) => (
          <li key={poll.id}>
            <h3>{poll.question}</h3>
            <p>
              id: {poll.id} by: {poll.username}
            </p>
            <p>
              created at: {poll.publishedAt} until: {poll.validUntil}
            </p>
            <ul>
              {poll.options &&
                poll.options.map((option) => (
                  <li key={option.presentationOrder}>
                    <p>{option.caption}</p>
                    <p>users agree: {option.voteUsernames.join(", ")}</p>
                    <button
                      onClick={() =>
                        onCreateVote(poll.id, option.presentationOrder)
                      }
                    >
                      agreed!
                    </button>
                  </li>
                ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
}
