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
  WorkbenchMainRepresentationHandle,
} from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useState } from 'react';
import { TreeView } from '../views/TreeView';
import { GQLTreeItem } from '../views/TreeView.types';
import { TreeRepresentationState } from './TreeRepresentation.types';
import { useTreeSubscription } from './useTreeSubscription';

export const TreeRepresentation = forwardRef<WorkbenchMainRepresentationHandle, RepresentationComponentProps>(
  (
    { editingContextId, representationId, readOnly }: RepresentationComponentProps,
    ref: ForwardedRef<WorkbenchMainRepresentationHandle>
  ) => {
    const [state, setState] = useState<TreeRepresentationState>({
      tree: null,
      expanded: [],
      maxDepth: 1,
    });
    const { tree, loading } = useTreeSubscription(editingContextId, representationId, state.expanded, state.maxDepth);
    useEffect(() => {
      if (tree && !loading) {
        setState((prevState) => ({ ...prevState, tree }));
      }
    }, [tree, loading]);

    const { selection, setSelection } = useSelection();

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
    if (!state.tree) {
      return <RepresentationLoadingIndicator />;
    } else {
      return (
        <div>
          <TreeView
            editingContextId={editingContextId}
            readOnly={readOnly}
            treeId={representationId}
            tree={state.tree}
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
