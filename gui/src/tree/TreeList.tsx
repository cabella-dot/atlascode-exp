import React from "react";
import { TreeItem } from "./tree-item/TreeItem";
import type { WorkItemInfo } from "../../types";

export const TreeList: React.FC<{ items: WorkItemInfo[]; title: string }> = ({
  items,
  title,
}) => {
  const [isOpen, setIsOpen] = React.useState(true);
  return (
    <div className="tree-list">
      <div
        onClick={() => setIsOpen((prev) => !prev)}
        className="tree-list-header"
      >
        <i
          className={`codicon ${
            isOpen ? "codicon-chevron-down" : "codicon-chevron-right"
          }`}
        />
        <span className="tree-list-title">{title.toLocaleUpperCase()}</span>
      </div>
      <div className="tree-list-items">
        {isOpen &&
          items.map((item) => <TreeItem key={item.key} details={item} />)}
      </div>
    </div>
  );
};
