/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { GQLToolVariable } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLTree, GQLTreeItem, useTreeSelection } from '@eclipse-sirius/sirius-components-trees';
import Dialog from '@mui/material/Dialog';
import { InternalSelectionDialogProps, SelectionDialogProps } from './SelectionDialog.types';
import { SelectionDialogActions } from './SelectionDialogActions';
import { SelectionDialogContent } from './SelectionDialogContent';
import { SelectionDialogDescriptionContextProvider } from './SelectionDialogDescriptionContext';
import { SelectionDialogTitle } from './SelectionDialogTitle';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';
import { useSelectionDescription } from './useSelectionDescription';
import { useSelectionDialog } from './useSelectionDialog';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  variables,
  onClose,
  onFinish,
}: SelectionDialogProps) => {
  const { selectionDescription } = useSelectionDescription({
    editingContextId,
    selectionDescriptionId: dialogDescriptionId,
    variables,
  });

  if (!selectionDescription) {
    return null;
  }

  return (
    <SelectionDialogDescriptionContextProvider selectionDescription={selectionDescription}>
      <InternalSelectionDialog
        editingContextId={editingContextId}
        variables={variables}
        onClose={onClose}
        onFinish={onFinish}
      />
    </SelectionDialogDescriptionContextProvider>
  );
};

const InternalSelectionDialog = ({ editingContextId, variables, onClose, onFinish }: InternalSelectionDialogProps) => {
  const { selection, setSelection } = useSelection();
  const { treeDescriptionId, multiple, optional, noSelectionOptionSelected, selectionOptionSelected } =
    useSelectionDialog();

  const { treeItemClick } = useTreeSelection();
  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
    var newSelection = treeItemClick(
      event,
      tree,
      item,
      selection.entries.map((entry) => entry.id),
      multiple
    );
    setSelection({ entries: newSelection.selectedTreeItemIds.map((id) => ({ id })) });
  };

  const handleCardinality = (selectedObjectIds: string[]): GQLToolVariable[] => {
    let variables: GQLToolVariable[] = [];
    if (multiple) {
      variables = [
        {
          name: 'selectedObjects',
          value: selectedObjectIds.length > 0 ? selectedObjectIds.join(',') : '',
          type: 'OBJECT_ID_ARRAY',
        },
      ];
    } else {
      const selectedObjectId = selectedObjectIds[0] ? selectedObjectIds[0] : '';
      variables = [{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }];
    }
    return variables;
  };

  const handleConfirmDialog = () => {
    let variables: GQLToolVariable[] = [];
    if (optional && noSelectionOptionSelected) {
      variables = handleCardinality([]);
    } else if (optional && selectionOptionSelected) {
      variables = handleCardinality(selection.entries.map((entry) => entry.id));
    } else if (!optional) {
      variables = handleCardinality(selection.entries.map((entry) => entry.id));
    }
    onFinish(variables);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
    if (event.key === 'Enter' && !(selection.entries.length == 0 && !noSelectionOptionSelected)) {
      const target = event.target as HTMLElement;
      if (target.tagName !== 'BUTTON') {
        // To prevent triggering the confirm action when pressing 'Enter' while focusing on the cancel button
        handleConfirmDialog();
      }
    }
  };

  return (
    <Dialog
      open
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="md"
      fullWidth
      data-testid="selection-dialog"
      slotProps={{
        root: () => ({
          onKeyDown: handleKeyDown,
        }),
        paper: () => ({ sx: () => ({ minHeight: '400px' }) }),
      }}>
      <SelectionDialogTitle onClose={onClose} />
      <SelectionDialogContent>
        {treeDescriptionId ? (
          <SelectionDialogTreeView
            editingContextId={editingContextId}
            variables={variables}
            treeDescriptionId={treeDescriptionId}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={selection.entries.map((entry) => entry.id)}
          />
        ) : null}
      </SelectionDialogContent>
      <SelectionDialogActions onClose={onClose} handleConfirmDialog={handleConfirmDialog} />
    </Dialog>
  );
};
