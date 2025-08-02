export interface WorkItemInfo {
  key: string;
  summary: string;
  description?: string;
  status: string;
  type?: { name: string; iconUrl?: string };
  priority?: { name: string; iconUrl?: string };
}
