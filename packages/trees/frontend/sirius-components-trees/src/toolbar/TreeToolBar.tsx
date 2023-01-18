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

import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import { Add as AddIcon, Publish as PublishIcon, SwapHoriz as SwapHorizIcon } from '@material-ui/icons';
import { useState } from 'react';
import { NewDocumentModal } from '../modals/new-document/NewDocumentModal';
import { UploadDocumentModal } from '../modals/upload-document/UploadDocumentModal';
import { TreeToolBarProps, TreeToolBarState } from './TreeToolBar.types';
const useTreeToolbarStyles = makeStyles((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    borderBottomColor: theme.palette.divider,
  },
}));

export const TreeToolBar = ({ editingContextId, onSynchronizedClick, synchronized, readOnly }: TreeToolBarProps) => {
  const classes = useTreeToolbarStyles();
  const [state, setState] = useState<TreeToolBarState>({ modalOpen: null });
  const openNewDocumentModal = () => setState((prevState) => ({ ...prevState, modalOpen: 'NewDocument' }));
  const openUploadDocumentModal = () => setState((prevState) => ({ ...prevState, modalOpen: 'UploadDocument' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modalOpen: null }));

  const preferenceButtonSynchroniseTitle = synchronized
    ? 'Disable synchronisation with representation'
    : 'Enable synchronisation with representation';
  return (
    <>
      <div className={classes.toolbar}>
        <IconButton
          disabled={readOnly}
          size="small"
          color="inherit"
          aria-label="New model"
          title="New model"
          onClick={() => openNewDocumentModal()}
          data-testid="new-model">
          <AddIcon />
        </IconButton>
        <IconButton
          disabled={readOnly}
          size="small"
          color="inherit"
          aria-label="Upload model"
          title="Upload model"
          onClick={() => openUploadDocumentModal()}
          data-testid="upload-document">
          <PublishIcon />
        </IconButton>
        <IconButton
          color="inherit"
          size="small"
          aria-label={preferenceButtonSynchroniseTitle}
          title={preferenceButtonSynchroniseTitle}
          onClick={() => onSynchronizedClick()}
          data-testid="tree-synchronise">
          <SwapHorizIcon color={synchronized ? 'inherit' : 'disabled'} />
        </IconButton>
      </div>
      {state.modalOpen === 'NewDocument' && (
        <NewDocumentModal editingContextId={editingContextId} onClose={closeModal} />
      )}
      {state.modalOpen === 'UploadDocument' && (
        <UploadDocumentModal editingContextId={editingContextId} onDocumentUploaded={closeModal} onClose={closeModal} />
      )}
    </>
  );
};
