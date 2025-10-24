/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { SelectionEntry, useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useOnSelectionChange, useStoreApi } from '@xyflow/react';
import { useCallback, useEffect, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

// Compute a deterministic key from a selection
const selectionKey = (entries: SelectionEntry[]) => {
  return JSON.stringify(
    entries.map((selectionEntry) => selectionEntry.id).sort((id1: string, id2: string) => id1.localeCompare(id2))
  );
};

/**
 * Configures React Flow so that when the selection changes on the diagram, the global selection is updated.
 */
export const useDiagramSelection = (onShiftSelection: boolean): void => {
  const { selection, setSelection } = useSelection();
  const [shiftSelection, setShiftSelection] = useState<SelectionEntry[]>([]);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const onChange = useCallback(
    ({ nodes, edges }) => {
      const entries: SelectionEntry[] = [...nodes, ...edges].map((diagramElement) => ({
        id: diagramElement.data.targetObjectId,
      }));
      if (selectionKey(entries) !== selectionKey(selection.entries)) {
        if (onShiftSelection) {
          setShiftSelection(entries);
        } else {
          setSelection({ entries });
        }
      }
    },
    [selection, onShiftSelection, store]
  );

  useOnSelectionChange({ onChange });

  useEffect(() => {
    if (shiftSelection.length > 0 && !onShiftSelection) {
      setSelection({ entries: shiftSelection });
      setShiftSelection([]);
    }
  }, [onShiftSelection]);
};
