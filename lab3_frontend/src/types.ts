  export type Options = {
    caption: string | undefined;
    presentationOrder: number;
    voteUsernames: string[];
  };

  export type Poll = {
    id: number;
    question: string | undefined;
    options: Options[] | undefined;
    username: string | undefined;
    publishedAt: string | undefined;
    validUntil: string | undefined;
  };
