/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from '@xyflow/react';
import { useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { UseFilterReadOnlyChangesValue } from './useFilterReadOnlyChanges.types';

const isMove = (change: NodeChange<Node<NodeData>>): change is NodePositionChange => change.type === 'position';
const isResize = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange => change.type === 'dimensions';

const isChangingDiagram = (change: NodeChange<Node<NodeData>>): boolean => isMove(change) || isResize(change);

export const useFilterReadOnlyChanges = (): UseFilterReadOnlyChangesValue => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const filterReadOnlyChanges = (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
    const filteredNodeChange: NodeChange<Node<NodeData>>[] = [];

    changes.forEach((change) => {
      if (!isChangingDiagram(change) || !readOnly) {
        filteredNodeChange.push(change);
      }
    });

    return filteredNodeChange;
  };

  return { filterReadOnlyChanges };
};
