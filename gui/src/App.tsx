import "./App.css";
import { TreeItem } from "./jira/tree/tree-item/TreeItem";

function App() {
  return (
    <>
      <TreeItem
        details={{
          key: "JIRA-123",
          summary: "Fix the bug in the application",
          status: "In Progress",
          type: { name: "Task", iconUrl: "https://example.com/task-icon.png" },
          priority: { name: "High", iconUrl: "https://example.com/high" },
        }}
      />
    </>
  );
}

export default App;
