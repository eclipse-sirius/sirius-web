/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { SelectionDialogActionsProps } from './SelectionDialogActions.types';
import { useSelectionDialog } from './useSelectionDialog';

export const SelectionDialogActions = ({ onClose, handleConfirmDialog }: SelectionDialogActionsProps) => {
  const { t } = useTranslation('sirius-components-selection', { keyPrefix: 'selectionDialogActions' });
  const { selection } = useSelection();
  const {
    selectionDialogDescription: { statusMessages, confirmButtonLabels },
    noSelectionOptionSelected,
    selectionOptionSelected,
    optional,
  } = useSelectionDialog();

  let statusMessage = '';
  let confirmButtonLabel = '';
  if (optional) {
    if (selectionOptionSelected) {
      if (selection.entries.length === 1 && selection.entries[0]) {
        statusMessage = `The tool execution will continue with ${selection.entries[0].id}`;
        confirmButtonLabel = 'Confirm';
      } else if (selection.entries.length > 1) {
        statusMessage = `The tool execution will continue with ${selection.entries.length} elements`;
        confirmButtonLabel = 'Confirm';
      } else {
        statusMessage = statusMessages.selectionStatusMessageWithoutSelection;
        confirmButtonLabel = confirmButtonLabels.selectionConfirmButtonLabelWithoutSelection;
      }
    } else if (noSelectionOptionSelected) {
      statusMessage = statusMessages.noSelectionStatusMessage;
      confirmButtonLabel = confirmButtonLabels.noSelectionConfirmButtonLabel;
    } else {
      statusMessage = t('defaultStatusMessage'); // 'Select an option above'
      confirmButtonLabel = t('defaultConfirmLabel'); // 'Select an option'
    }
  } else {
    if (selection.entries.length === 1 && selection.entries[0]) {
      statusMessage = `The tool execution will continue with ${selection.entries[0].id}`;
      confirmButtonLabel = 'Confirm';
    } else if (selection.entries.length > 1) {
      statusMessage = `The tool execution will continue with ${selection.entries.length} elements`;
      confirmButtonLabel = 'Confirm';
    } else {
      statusMessage = statusMessages.selectionStatusMessageWithoutSelection;
      confirmButtonLabel = confirmButtonLabels.selectionConfirmButtonLabelWithoutSelection;
    }
  }

  return (
    <DialogActions sx={() => ({ display: 'flex', alignItems: 'center', justifyContent: 'space-between' })}>
      <Typography variant="body1" color="textSecondary" data-testid="status-message">
        {statusMessage}
      </Typography>
      <Box sx={(theme) => ({ display: 'flex', gap: theme.spacing(1) })}>
        <Button variant="outlined" onClick={onClose} data-testid="cancel-action">
          {t('cancel')}
        </Button>
        <Button
          variant="contained"
          disabled={selection.entries.length == 0 && !noSelectionOptionSelected}
          data-testid="confirm-action"
          color="primary"
          onClick={handleConfirmDialog}>
          {confirmButtonLabel}
        </Button>
      </Box>
    </DialogActions>
  );
};
