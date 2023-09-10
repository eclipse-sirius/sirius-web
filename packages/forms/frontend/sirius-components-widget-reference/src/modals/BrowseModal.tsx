/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { ModelBrowserTreeView } from '../components/ModelBrowserTreeView';
import { BrowseModalProps } from './BrowseModal.types';

const useBrowserModalStyles = makeStyles((_) => ({
  content: {
    height: 400,
  },
}));

export const BrowseModal = ({ editingContextId, widget, onClose }: BrowseModalProps) => {
  const styles = useBrowserModalStyles();
  const [browserSelection, setBrowserSelection] = useState<Selection>({ entries: widget.referenceValues });

  return (
    <>
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
            selection={browserSelection}
            setSelection={setBrowserSelection}
            widget={widget}
            markedItemIds={[]}
            enableMultiSelection={widget.reference.manyValued}
            title={'Choices'}
            leafType={'reference'}
            typeName={widget.reference.typeName}
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
    </>
  );
};
