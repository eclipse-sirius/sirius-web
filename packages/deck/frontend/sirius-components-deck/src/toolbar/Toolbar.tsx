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
import { ShareRepresentationModal } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import ShareIcon from '@material-ui/icons/Share';
import Visibility from '@material-ui/icons/Visibility';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { useState } from 'react';
import { ToolbarProps, ToolbarState } from './Toolbar.types';

const useToolbarStyles = makeStyles((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    borderBottomColor: theme.palette.divider,
  },
}));

export const Toolbar = ({ editingContextId, representationId }: ToolbarProps) => {
  const classes = useToolbarStyles();
  const [state, setState] = useState<ToolbarState>({ modal: null });

  const onShare = () => setState((prevState) => ({ ...prevState, modal: 'share' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  let modalElement: React.ReactElement | null = null;
  if (state.modal === 'share') {
    modalElement = (
      <ShareRepresentationModal
        editingContextId={editingContextId}
        representationId={representationId}
        onClose={closeModal}
      />
    );
  }

  return (
    <>
      <div className={classes.toolbar}>
        <IconButton size="small">
          <FullscreenIcon />
        </IconButton>
        <IconButton size="small" data-testid="fit-to-screen">
          <AspectRatioIcon />
        </IconButton>
        <IconButton size="small">
          <ZoomInIcon />
        </IconButton>
        <IconButton size="small">
          <ZoomOutIcon />
        </IconButton>
        <IconButton
          size="small"
          aria-label="reveal hidden elements"
          title="Reveal hidden elements"
          data-testid="reveal-hidden-elements">
          <Visibility />
        </IconButton>
        <IconButton size="small" color="inherit" aria-label="share" title="Share" onClick={onShare} data-testid="share">
          <ShareIcon />
        </IconButton>
      </div>
      {modalElement}
    </>
  );
};
