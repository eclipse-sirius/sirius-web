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

import { RepresentationComponentProps } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { TreeView } from '../views/TreeView';
import { TreeRepresentationState } from './TreeRepresentation.types';
import { useTreeSubscription } from './useTreeSubscription';

export const TreeRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
  const [state, setState] = useState<TreeRepresentationState>({
    expanded: [],
    maxDepth: 1,
  });
  const { tree } = useTreeSubscription(editingContextId, representationId, state.expanded, state.maxDepth);

  const onExpandedElementChange = (expanded: string[], maxDepth: number) => {
    setState((prevState) => ({ ...prevState, expanded, maxDepth }));
  };

  return (
    <div>
      {tree !== null ? (
        <TreeView
          editingContextId={editingContextId}
          readOnly={readOnly}
          treeId={representationId}
          tree={tree}
          enableMultiSelection={true}
          synchronizedWithSelection={true}
          textToFilter={''}
          textToHighlight={''}
          onExpandedElementChange={onExpandedElementChange}
        />
      ) : null}
    </div>
  );
};
