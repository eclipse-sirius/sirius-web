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
import { GQLToolVariable } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLTree, GQLTreeItem, useTreeSelection } from '@eclipse-sirius/sirius-components-trees';
import Dialog from '@mui/material/Dialog';
import { useState } from 'react';
import {
  GQLSelectionDescription,
  SelectionDialogOptions,
  SelectionDialogProps,
  SelectionDialogState,
} from './SelectionDialog.types';
import { SelectionDialogActions } from './SelectionDialogActions';
import { SelectionDialogContent } from './SelectionDialogContent';
import { SelectionDialogTitle } from './SelectionDialogTitle';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';
import { useSelectionDescription } from './useSelectionDescription';

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  variables,
  onClose,
  onFinish,
}: SelectionDialogProps) => {
  const [state, setState] = useState<SelectionDialogState>({
    selectedTreeItemIds: [],
    selectionDialogOption: 'INITIAL',
  });

  const { selectionDescription: rawSelectionDescription } = useSelectionDescription({
    editingContextId,
    selectionDescriptionId: dialogDescriptionId,
    variables,
  });

  const { treeItemClick } = useTreeSelection();
  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
    var newSelection = treeItemClick(event, tree, item, state.selectedTreeItemIds, multiple);
    setState((prevState) => ({
      ...prevState,
      selectedTreeItemIds: newSelection.selectedTreeItemIds,
    }));
  };

  if (!rawSelectionDescription) {
    return null;
  }

  const selectionDescription: GQLSelectionDescription = {
    dialog: {
      titles: {
        defaultTitle: 'Element Selection',
        noSelectionTitle: 'Element Selection',
        selectionTitle: 'Element Selection',
      },
      description: rawSelectionDescription.message,
      noSelectionAction: {
        label: rawSelectionDescription.noSelectionLabel,
        description: 'Proceed without selecting an existing element',
      },
      withSelectionAction: {
        label: 'Use an existing element',
        description: 'Select one or more elements',
      },
      statusMessages: {
        noSelectionActionStatusMessage: 'The tool execution will continue without any element selected',
        selectionRequiredWithoutSelectionStatusMessage: 'Select at least one element to continue the tool execution',
      },
      confirmButtonLabels: {
        noSelectionConfirmButtonLabel: 'Confirm',
        selectionRequiredWithoutSelectionConfirmButtonLabel: 'Select an element',
        selectionRequiredWithSelectionConfirmButtonLabel: 'Confirm',
      },
    },
    treeDescription: rawSelectionDescription.treeDescription,
    multiple: rawSelectionDescription.multiple,
    optional: rawSelectionDescription.optional,
  };

  const { multiple, optional } = selectionDescription;

  const createToolVariables = (selectedObjectIds: string[]): GQLToolVariable[] => {
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

  const onConfirm = () => {
    let variables: GQLToolVariable[] = [];
    if (optional && state.selectionDialogOption === 'NO_SELECTION') {
      variables = createToolVariables([]);
    } else if (optional && state.selectionDialogOption === 'WITH_SELECTION') {
      variables = createToolVariables(state.selectedTreeItemIds);
    } else if (!optional) {
      variables = createToolVariables(state.selectedTreeItemIds);
    }
    onFinish(variables);
  };

  const handleSelectionDialogOptionChange = (newSelectionDialogOption: SelectionDialogOptions) => {
    setState((prevState) => ({
      ...prevState,
      selectionDialogOption: newSelectionDialogOption,
    }));
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
        paper: () => ({ sx: () => ({ minHeight: '400px' }) }),
      }}>
      <SelectionDialogTitle
        selectionDescription={selectionDescription}
        selectionDialogOption={state.selectionDialogOption}
        onClose={onClose}
      />
      <SelectionDialogContent
        selectionDescription={selectionDescription}
        selectionDialogOption={state.selectionDialogOption}
        onSelectionDialogOptionChange={handleSelectionDialogOptionChange}>
        {selectionDescription.treeDescription.id ? (
          <SelectionDialogTreeView
            editingContextId={editingContextId}
            variables={variables}
            treeDescriptionId={selectionDescription.treeDescription.id}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={state.selectedTreeItemIds}
          />
        ) : null}
      </SelectionDialogContent>
      <SelectionDialogActions
        onClose={onClose}
        onConfirm={onConfirm}
        selectionDialogOption={state.selectionDialogOption}
        selectedTreeItemIds={state.selectedTreeItemIds}
        selectionDescription={selectionDescription}
      />
    </Dialog>
  );
};
