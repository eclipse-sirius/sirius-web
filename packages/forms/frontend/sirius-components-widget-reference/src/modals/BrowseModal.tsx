/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useMemo, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ModelBrowserTreeView } from '../components/ModelBrowserTreeView';
import { BrowseModalProps } from './BrowseModal.types';

const useBrowserModalStyles = makeStyles()((_) => ({
  content: {
    height: 400,
  },
}));

export const BrowseModal = ({ editingContextId, widget, onClose }: BrowseModalProps) => {
  const { classes: styles } = useBrowserModalStyles();
  const [browserSelection, setBrowserSelection] = useState<Selection>({ entries: widget.referenceValues });

  const markedItemIds = useMemo(() => [], []);

  return (
    <SelectionContext.Provider
      value={{
        selection: browserSelection,
        setSelection: setBrowserSelection,
      }}>
      <Dialog
        open={true}
        onClose={() => onClose(null)}
        aria-labelledby="dialog-title"
        fullWidth
        data-testid="browse-modal">
        <DialogTitle id="dialog-title">Select an object</DialogTitle>
        <DialogContent className={styles.content}>
          <ModelBrowserTreeView
            editingContextId={editingContextId}
            widget={widget}
            markedItemIds={markedItemIds}
            enableMultiSelection={widget.reference.manyValued}
            title={'Choices'}
            leafType={'reference'}
            ownerKind={widget.reference.ownerKind}
          />
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            color="primary"
            type="button"
            data-testid="select-value"
            onClick={() => {
              const selectedElement = browserSelection?.entries.length > 0 ? browserSelection.entries[0] : null;
              if (selectedElement) {
                onClose(selectedElement.id);
              }
            }}>
            Select
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};
