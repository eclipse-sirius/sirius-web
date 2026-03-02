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
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { SelectionDialogActionsProps, SelectionDialogActionsState } from './SelectionDialogActions.types';
import { useGetSelectionDialogSelectionRequiredWithSelectionStatusMessage } from './useGetSelectionDialogStatusMessageWithSelection';

export const SelectionDialogActions = ({
  editingContextId,
  selectionDescriptionId,
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

  const [state, setState] = useState<SelectionDialogActionsState>({
    statusMessage: '',
    confirmButtonLabel: '',
  });

  const { loading, dialogSelectionRequiredWithSelectionStatusMessage, updateStatusMessage } =
    useGetSelectionDialogSelectionRequiredWithSelectionStatusMessage({
      editingContextId,
      selectionDescriptionId,
    });

  useEffect(() => {
    if (selectedTreeItemIds.length > 0 && (!optional || selectionDialogOption === 'WITH_SELECTION')) {
      updateStatusMessage(selectedTreeItemIds);
    }
  }, [optional, selectedTreeItemIds, selectionDialogOption]);

  useEffect(() => {
    if (!loading) {
      let statusMessage = '';
      let confirmButtonLabel = '';
      if (optional) {
        if (selectionDialogOption === 'WITH_SELECTION') {
          if (selectedTreeItemIds.length === 1 && selectedTreeItemIds[0]) {
            statusMessage =
              dialogSelectionRequiredWithSelectionStatusMessage ??
              `The tool execution will continue with ${selectedTreeItemIds[0]}`;
            confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
          } else if (selectedTreeItemIds.length > 1) {
            statusMessage =
              dialogSelectionRequiredWithSelectionStatusMessage ??
              `The tool execution will continue with ${selectedTreeItemIds.length} elements`;
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
          statusMessage =
            dialogSelectionRequiredWithSelectionStatusMessage ??
            `The tool execution will continue with ${selectedTreeItemIds[0]}`;
          confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
        } else if (selectedTreeItemIds.length > 1) {
          statusMessage =
            dialogSelectionRequiredWithSelectionStatusMessage ??
            `The tool execution will continue with ${selectedTreeItemIds.length} elements`;
          confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
        }
      }
      setState((prevState) => ({
        ...prevState,
        statusMessage,
        confirmButtonLabel,
      }));
    }
  }, [loading, selectedTreeItemIds, selectionDialogOption, dialogSelectionRequiredWithSelectionStatusMessage]);

  return (
    <DialogActions sx={() => ({ display: 'flex', alignItems: 'center', justifyContent: 'space-between' })}>
      <Typography variant="body1" color="textSecondary" data-testid="status-message">
        {state.statusMessage}
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
          {state.confirmButtonLabel}
        </Button>
      </Box>
    </DialogActions>
  );
};
