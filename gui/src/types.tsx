export interface WorkItemInfo {
  key: string;
  summary: string;
  description?: string;
  status: string;
  type?: { name: string; iconUrl?: string };
  priority?: { name: string; iconUrl?: string };
}

declare global {
  interface Window {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    postMessageToIntellij?: (messageType: string, data: any) => void;
  }
}

export {};
