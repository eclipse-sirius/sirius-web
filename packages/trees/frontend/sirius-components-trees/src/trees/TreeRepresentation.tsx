/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  Selection,
  SelectionEntry,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { TreeView } from '../views/TreeView';
import { GQLTreeItem } from '../views/TreeView.types';
import { useExpandAllTreePath } from '../views/useExpandAllTreePath';
import { GQLGetExpandAllTreePathVariables } from '../views/useExpandAllTreePath.types';
import { TreeRepresentationState } from './TreeRepresentation.types';
import { useTreeSubscription } from './useTreeSubscription';

export const TreeRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
  const [state, setState] = useState<TreeRepresentationState>({
    expanded: [],
    maxDepth: 1,
  });
  const { tree, loading } = useTreeSubscription(editingContextId, representationId, state.expanded, state.maxDepth);

  const { selection, setSelection } = useSelection();

  const onExpand = (id: string, depth: number) => {
    const { expanded, maxDepth } = state;

    if (expanded.includes(id)) {
      const newExpanded = [...expanded];
      newExpanded.splice(newExpanded.indexOf(id), 1);

      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(maxDepth, depth),
      }));
    } else {
      setState((prevState) => ({ ...prevState, expanded: [...expanded, id], maxDepth: Math.max(maxDepth, depth) }));
    }
  };

  const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();

  useEffect(() => {
    if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
      setState((prevState) => {
        const { expanded, maxDepth } = prevState;
        const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
          expandAllTreePathData.viewer.editingContext.expandAllTreePath;
        const newExpanded: string[] = [...expanded];

        treeItemIdsToExpand?.forEach((itemToExpand) => {
          if (!expanded.includes(itemToExpand)) {
            newExpanded.push(itemToExpand);
          }
        });
        return {
          ...prevState,
          expanded: newExpanded,
          maxDepth: Math.max(expandedMaxDepth, maxDepth),
        };
      });
    }
  }, [expandAllTreePathData]);

  const onExpandAll = (treeItem: GQLTreeItem) => {
    if (tree?.id) {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        treeItemId: treeItem.id,
      };
      getExpandAllTreePath({ variables });
    }
  };

  const onTreeItemClick = (event, item: GQLTreeItem) => {
    if (event.ctrlKey || event.metaKey) {
      event.stopPropagation();
      const isItemInSelection = selection.entries.find((entry) => entry.id === item.id);
      if (isItemInSelection) {
        const newSelection: Selection = { entries: selection.entries.filter((entry) => entry.id !== item.id) };
        setSelection(newSelection);
      } else {
        const { id } = item;
        const newEntry: SelectionEntry = { id };
        const newSelection: Selection = { entries: [...selection.entries, newEntry] };
        setSelection(newSelection);
      }
    } else {
      const { id } = item;
      setSelection({ entries: [{ id }] });
    }
  };
  if (!tree || loading) {
    return <RepresentationLoadingIndicator />;
  } else {
    return (
      <div>
        <TreeView
          editingContextId={editingContextId}
          readOnly={readOnly}
          treeId={representationId}
          tree={tree}
          textToFilter={''}
          textToHighlight={''}
          onExpand={onExpand}
          onExpandAll={onExpandAll}
          onTreeItemClick={onTreeItemClick}
          selectedTreeItemIds={selection.entries.map((entry) => entry.id)}
        />
      </div>
    );
  }
};
