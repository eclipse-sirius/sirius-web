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

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { SelectionDialogActionsProps } from './SelectionDialogActions.types';

export const SelectionDialogActions = ({
  onClose,
  onConfirm,
  selectedTreeItemIds,
  selectionDescription,
  selectionDialogOption,
}: SelectionDialogActionsProps) => {
  const { t } = useTranslation('sirius-components-selection', { keyPrefix: 'selectionDialogActions' });
  const {
    dialog: { confirmButtonLabels, statusMessages },
    optional,
  } = selectionDescription;

  let statusMessage = '';
  let confirmButtonLabel = '';
  if (optional) {
    if (selectionDialogOption === 'WITH_SELECTION') {
      if (selectedTreeItemIds.length === 1 && selectedTreeItemIds[0]) {
        statusMessage = `The tool execution will continue with ${selectedTreeItemIds[0]}`;
        confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
      } else if (selectedTreeItemIds.length > 1) {
        statusMessage = `The tool execution will continue with ${selectedTreeItemIds.length} elements`;
        confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
      } else {
        statusMessage = statusMessages.selectionRequiredWithoutSelectionStatusMessage;
        confirmButtonLabel = confirmButtonLabels.selectionRequiredWithoutSelectionConfirmButtonLabel;
      }
    } else if (selectionDialogOption === 'NO_SELECTION') {
      statusMessage = statusMessages.noSelectionActionStatusMessage;
      confirmButtonLabel = confirmButtonLabels.noSelectionConfirmButtonLabel;
    } else {
      statusMessage = t('defaultStatusMessage');
      confirmButtonLabel = t('defaultConfirmLabel');
    }
  } else {
    if (selectedTreeItemIds.length === 1 && selectedTreeItemIds[0]) {
      statusMessage = `The tool execution will continue with ${selectedTreeItemIds[0]}`;
      confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
    } else if (selectedTreeItemIds.length > 1) {
      statusMessage = `The tool execution will continue with ${selectedTreeItemIds.length} elements`;
      confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
    } else {
      statusMessage = statusMessages.selectionRequiredWithoutSelectionStatusMessage;
      confirmButtonLabel = confirmButtonLabels.selectionRequiredWithoutSelectionConfirmButtonLabel;
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
          disabled={selectedTreeItemIds.length == 0 && selectionDialogOption !== 'NO_SELECTION'}
          data-testid="confirm-action"
          color="primary"
          onClick={onConfirm}>
          {confirmButtonLabel}
        </Button>
      </Box>
    </DialogActions>
  );
};
