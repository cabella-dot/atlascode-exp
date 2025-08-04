import React from "react";
import type { WorkItemInfo } from "../../../types";

interface TreeItemProps {
  details: WorkItemInfo;
}

export const TreeItem: React.FC<TreeItemProps> = ({ details }) => {
  const [isOpen, setIsOpen] = React.useState(false);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className="tree-item-container">
      <div onClick={() => toggleOpen()} className="tree-item">
        <div className="tree-item-header" onClick={toggleOpen}>
          <span className="tree-item-key">{details.key}</span>
          <span className="tree-item-summary">{details.summary}</span>
        </div>
        <div className={`tree-item-details`}>
          <div className="tree-item-status in-progress">{details.status}</div>
        </div>
      </div>
      {isOpen && <div>Hi</div>}
    </div>
  );
};
