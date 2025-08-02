import React from "react";

interface TreeItemProps {
  itemKey: string;
  itemSummary: string;
  itemStatus: string;
  iconUrl?: string;
}

export const TreeItem: React.FC<TreeItemProps> = ({
  itemKey,
  itemSummary,
  itemStatus,
  iconUrl,
}) => {};
