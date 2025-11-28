import { GQLTree, GQLTreeItem } from '../views/TreeView.types';

export interface UseTreeSelectionState {
  selectionPivotTreeItemId: string | null;
}

export interface UseTreeSelectionValue {
  treeItemClick: (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>,
    item: GQLTreeItem,
    tree: GQLTree,
    selectedElements: string[]
  ) => TreeItemClickResult;
}

export interface TreeItemClickResult {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
}
