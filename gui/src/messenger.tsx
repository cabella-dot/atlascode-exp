/* eslint-disable @typescript-eslint/no-explicit-any */
import React from "react";

export const useMessenger = (onMessageHandler: (e: MessageEvent) => void) => {
  const [attemps, setAttempts] = React.useState(0);

  function post(messageType: string, data: any) {
    if (attemps >= 10) {
      console.error("Max attempts reached, giving up on postMessageToIntellij");
      return;
    }

    if (!window.postMessageToIntellij) {
      console.warn("postMessageToIntellij is not defined");
      setAttempts((prev) => prev + 1);
      setTimeout(() => {
        post(messageType, data);
      }, Math.pow(2, attemps) * 1000);
    } else {
      window.postMessageToIntellij(messageType, data);
      setAttempts(0); // Reset attempts on successful post
    }
  }

  React.useEffect(() => {
    window.addEventListener("message", onMessageHandler);

    return () => {
      window.removeEventListener("message", onMessageHandler);
    };
  });

  return { post };
};
