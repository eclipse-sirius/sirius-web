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

import { useContext } from 'react';
import { NodeChange, NodeDimensionChange, NodePositionChange } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { UseFilterReadOnlyChangesValue } from './useFilterReadOnlyChanges.types';

const isMove = (change: NodeChange): change is NodePositionChange => change.type === 'position';
const isResize = (change: NodeChange): change is NodeDimensionChange => change.type === 'dimensions';

const isChangingDiagram = (change: NodeChange): boolean => isMove(change) || isResize(change);

export const useFilterReadOnlyChanges = (): UseFilterReadOnlyChangesValue => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const filterReadOnlyChanges = (changes: NodeChange[]): NodeChange[] => {
    const filteredNodeChange: NodeChange[] = [];

    changes.forEach((change) => {
      if (!isChangingDiagram(change) || !readOnly) {
        filteredNodeChange.push(change);
      }
    });

    return filteredNodeChange;
  };

  return { filterReadOnlyChanges };
};
