import React from "react";
import "./App.css";
import "@vscode/codicons/dist/codicon.css";
import { TreeList } from "./tree/TreeList";
import type { WorkItemInfo } from "./types";
import { useMessenger } from "./messenger";

function App() {
  const [items, setItems] = React.useState<WorkItemInfo[]>([]);
  const [isDone, setIsDone] = React.useState(false);

  const messageHandler = React.useCallback((event: MessageEvent) => {
    console.log("Received message:", event.data);
  }, []);

  const { post } = useMessenger(messageHandler);

  React.useEffect(() => {
    if (!isDone) {
      post("request", {});
    }
  }, [isDone, post]);

  return (
    <>
      <TreeList items={items} title="Assigned jira work items" />
    </>
  );
}

export default App;
