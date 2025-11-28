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
  SelectionEntry,
  useSelection,
  WorkbenchMainRepresentationHandle,
} from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react';
import { TreeView } from '../views/TreeView';
import { GQLTree, GQLTreeItem } from '../views/TreeView.types';
import { TreeRepresentationState } from './TreeRepresentation.types';
import { useTreeSelection } from './useTreeSelection';
import { useTreeSubscription } from './useTreeSubscription';

export const TreeRepresentation = forwardRef<WorkbenchMainRepresentationHandle, RepresentationComponentProps>(
  (
    { editingContextId, representationId, readOnly }: RepresentationComponentProps,
    ref: ForwardedRef<WorkbenchMainRepresentationHandle>
  ) => {
    const [state, setState] = useState<TreeRepresentationState>({
      expanded: [],
      maxDepth: 1,
    });
    const { tree, loading } = useTreeSubscription(editingContextId, representationId, state.expanded, state.maxDepth);

    const { selection, setSelection } = useSelection();
    const { treeItemClick } = useTreeSelection();

    useImperativeHandle(
      ref,
      () => {
        return {
          id: representationId,
          applySelection: null,
        };
      },
      []
    );

    const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
      setState((prevState) => ({
        ...prevState,
        expanded: newExpandedIds,
        maxDepth: Math.max(newMaxDepth, prevState.maxDepth),
      }));
    };

    const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem, tree: GQLTree) => {
      var newSelection = treeItemClick(
        event,
        item,
        tree,
        selection.entries.map((entry) => entry.id),
        false
      );
      setSelection({ entries: newSelection.selectedTreeItemIds.map<SelectionEntry>((id) => ({ id })) });
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
            onExpandedElementChange={onExpandedElementChange}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={selection.entries.map((entry) => entry.id)}
            selectTreeItems={(treeItemIds) =>
              setSelection({
                entries: treeItemIds.map((id) => ({
                  id,
                })),
              })
            }
            expanded={state.expanded}
            maxDepth={state.maxDepth}
          />
        </div>
      );
    }
  }
);
