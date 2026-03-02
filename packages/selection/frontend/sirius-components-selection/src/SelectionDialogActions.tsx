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
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { SelectionDialogActionsProps, SelectionDialogActionsState } from './SelectionDialogActions.types';
import { useGetSelectionDialogStatusMessageWithSelection } from './useGetSelectionDialogStatusMessageWithSelection';
import { useSelectionDialog } from './useSelectionDialog';

export const SelectionDialogActions = ({
  editingContextId,
  selectionDescriptionId,
  onClose,
  handleConfirmDialog,
}: SelectionDialogActionsProps) => {
  const { t } = useTranslation('sirius-components-selection', { keyPrefix: 'selectionDialogActions' });
  const { selection } = useSelection();
  const {
    dialog: { statusMessages, confirmButtonLabels },
    noSelectionOptionSelected,
    selectionOptionSelected,
    optional,
  } = useSelectionDialog();

  const [state, setState] = useState<SelectionDialogActionsState>({
    statusMessage: '',
    confirmButtonLabel: '',
  });

  const { loading, dialogSelectionStatusMessageWithSelection, updateStatusMessage } =
    useGetSelectionDialogStatusMessageWithSelection({
      editingContextId,
      selectionDescriptionId,
    });

  useEffect(() => {
    if (selection.entries.length > 0 && (!optional || selectionOptionSelected)) {
      updateStatusMessage(selection.entries.map((entry) => entry.id));
    }
  }, [optional, selectionOptionSelected, selection]);

  useEffect(() => {
    if (!loading) {
      let statusMessage = '';
      let confirmButtonLabel = '';
      if (optional) {
        if (selectionOptionSelected) {
          if (
            selection.entries.length === 1 &&
            selection.entries[0] &&
            !loading &&
            dialogSelectionStatusMessageWithSelection
          ) {
            statusMessage = dialogSelectionStatusMessageWithSelection;
            confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
          } else if (selection.entries.length > 1 && !loading && dialogSelectionStatusMessageWithSelection) {
            statusMessage = dialogSelectionStatusMessageWithSelection;
            confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
          } else {
            statusMessage = statusMessages.selectionRequiredWithoutSelectionStatusMessage;
            confirmButtonLabel = confirmButtonLabels.selectionRequiredWithoutSelectionConfirmButtonLabel;
          }
        } else if (noSelectionOptionSelected) {
          statusMessage = statusMessages.noSelectionActionStatusMessage;
          confirmButtonLabel = confirmButtonLabels.noSelectionConfirmButtonLabel;
        } else {
          statusMessage = t('defaultStatusMessage');
          confirmButtonLabel = t('defaultConfirmLabel');
        }
      } else {
        if (
          selection.entries.length === 1 &&
          selection.entries[0] &&
          !loading &&
          dialogSelectionStatusMessageWithSelection
        ) {
          statusMessage = dialogSelectionStatusMessageWithSelection;
          confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
        } else if (selection.entries.length > 1 && !loading && dialogSelectionStatusMessageWithSelection) {
          statusMessage = dialogSelectionStatusMessageWithSelection;
          confirmButtonLabel = confirmButtonLabels.selectionRequiredWithSelectionConfirmButtonLabel;
        } else {
          statusMessage = statusMessages.selectionRequiredWithoutSelectionStatusMessage;
          confirmButtonLabel = confirmButtonLabels.selectionRequiredWithoutSelectionConfirmButtonLabel;
        }
      }
      setState((prevState) => ({
        ...prevState,
        statusMessage,
        confirmButtonLabel,
      }));
    }
  }, [loading, selectionOptionSelected, noSelectionOptionSelected]);

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
          disabled={selection.entries.length == 0 && !noSelectionOptionSelected}
          data-testid="confirm-action"
          color="primary"
          onClick={handleConfirmDialog}>
          {state.confirmButtonLabel}
        </Button>
      </Box>
    </DialogActions>
  );
};
