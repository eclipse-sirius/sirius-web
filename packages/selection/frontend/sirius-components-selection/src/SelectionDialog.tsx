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
import { DiagramDialogComponentProps, GQLToolVariable } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLTree, GQLTreeItem, useTreeSelection } from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import ButtonBase from '@mui/material/ButtonBase';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Radio from '@mui/material/Radio';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SelectionDialogState } from './SelectionDialog.types';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';
import { useSelectionDescription } from './useSelectionDescription';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

const useSelectionDialogStyles = makeStyles()((theme) => ({
  noSelectionPrimaryText: {
    color: theme.palette.text.primary,
  },
  noSelectionSecondaryText: {
    color: theme.palette.text.secondary,
  },
}));

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  variables,
  onClose,
  onFinish,
}: DiagramDialogComponentProps) => {
  const { classes } = useSelectionDialogStyles();
  const [state, setState] = useState<SelectionDialogState>({
    selectedObjectIds: [],
    noSelectionOptionSelected: false,
  });

  const { selectionDescription } = useSelectionDescription({
    editingContextId,
    selectionDescriptionId: dialogDescriptionId,
    variables,
  });

  const { treeItemClick } = useTreeSelection();

  const message: string | undefined = selectionDescription?.message;
  const noSelectionLabel: string | undefined = selectionDescription?.noSelectionLabel;
  const treeDescriptionId: string | null = selectionDescription?.treeDescription.id ?? null;
  const multiple: boolean = selectionDescription?.multiple ?? false;
  const optional: boolean = selectionDescription?.optional ?? false;

  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
    var newSelection = treeItemClick(event, tree, item, state.selectedObjectIds, multiple);
    setState((prevState) => ({
      ...prevState,
      selectedObjectIds: newSelection.selectedTreeItemIds,
      noSelectionOptionSelected: false,
    }));
  };

  const handleClick = () => {
    let variables: GQLToolVariable[] = [];
    if (multiple) {
      variables = [
        {
          name: 'selectedObjects',
          value: state.selectedObjectIds.length > 0 ? state.selectedObjectIds.join(',') : '',
          type: 'OBJECT_ID_ARRAY',
        },
      ];
    } else {
      const selectedObjectId = state.selectedObjectIds[0] ? state.selectedObjectIds[0] : '';
      variables = [{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }];
    }
    onFinish(variables);
  };

  let content: JSX.Element | null = null;
  if (treeDescriptionId !== null) {
    content = (
      <>
        {optional ? (
          <div data-testid="no-selection-option">
            <Typography variant="subtitle2" component="h6" data-testid="no-selection-label">
              {noSelectionLabel}
            </Typography>
            <ButtonBase
              component="div"
              sx={(theme) => ({
                width: '100%',
                borderRadius: theme.spacing(0.5),
                padding: theme.spacing(1),
                justifyContent: 'flex-start',
                border: state.noSelectionOptionSelected
                  ? `1px solid ${theme.palette.primary.main}`
                  : `1px solid ${theme.palette.divider}`,
              })}
              onClick={() => {
                setState((prevState) => ({
                  ...prevState,
                  noSelectionOptionSelected: true,
                  selectedObjectIds: [],
                }));
              }}>
              <Radio
                disableFocusRipple={true}
                disableRipple={true}
                disableTouchRipple={true}
                checked={state.noSelectionOptionSelected}
              />
              <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                <Typography className={classes.noSelectionPrimaryText} data-testid="button-no-selection-label">
                  {noSelectionLabel}
                </Typography>
                <Typography className={classes.noSelectionSecondaryText}>
                  Proceed without selecting an existing element
                </Typography>
              </Box>
            </ButtonBase>
          </div>
        ) : null}
        <div data-testid="selection-section">
          <Typography variant="subtitle2" component="h6" data-testid="selection-message">
            {message}
          </Typography>
          <SelectionDialogTreeView
            editingContextId={editingContextId}
            variables={variables}
            treeDescriptionId={treeDescriptionId}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={state.selectedObjectIds}
          />
        </div>
      </>
    );
  }

  return (
    <Dialog
      open
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="md"
      fullWidth
      data-testid="selection-dialog">
      <DialogTitle id="selection-dialog-title">Element Selection</DialogTitle>
      <DialogContent
        dividers={true}
        sx={(theme) => ({ display: 'flex', flexDirection: 'column', gap: theme.spacing(1) })}>
        {content}
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.selectedObjectIds.length == 0 && !state.noSelectionOptionSelected}
          data-testid="finish-action"
          color="primary"
          onClick={handleClick}>
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};
