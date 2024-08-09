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
import { TreeView } from '@eclipse-sirius/sirius-components-trees';
import { SelectionDialogTreeViewProps } from './SelectionDialogTreeView.types';

export const SelectionDialogTreeView = ({
  editingContextId,
  targetObjectId,
  descriptionId,
}: SelectionDialogTreeViewProps) => {
  return (
    <TreeView
      editingContextId={editingContextId}
      readOnly={true}
      treeId={`selectionDialog://?targetObjectId=${encodeURIComponent(
        targetObjectId
      )}&descriptionId=${encodeURIComponent(descriptionId)}`}
      enableMultiSelection={true}
      synchronizedWithSelection={true}
      activeFilterIds={[]}
      textToFilter={null}
      textToHighlight={null}
    />
  );
};
