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
import Paper from '@material-ui/core/Paper';
import Tooltip from '@material-ui/core/Tooltip';
import EditIcon from '@material-ui/icons/Edit';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import PanToolIcon from '@material-ui/icons/PanTool';
import ShareIcon from '@material-ui/icons/Share';
import { useState } from 'react';
import { useFullscreen } from '../hooks/useFullScreen';
import { PortalToolbarProps, PortalToolbarState } from './PortalToolbar.types';

export const PortalToolbar = ({
  editingContextId,
  representationId,
  fullscreenNode,
  portalMode,
  setPortalMode,
}: PortalToolbarProps) => {
  const { fullscreen, setFullscreen } = useFullscreen(fullscreenNode);
  const [state, setState] = useState<PortalToolbarState>({ modal: null });

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
      <Paper data-testid="portal-toolbar">
        {fullscreen ? (
          <Tooltip title="Exit full screen mode">
            <IconButton size="small" aria-label="exit full screen mode" onClick={() => setFullscreen(false)}>
              <FullscreenExitIcon />
            </IconButton>
          </Tooltip>
        ) : (
          <Tooltip title="Toggle full screen mode">
            <IconButton size="small" aria-label="toggle full screen mode" onClick={() => setFullscreen(true)}>
              <FullscreenIcon />
            </IconButton>
          </Tooltip>
        )}
        <Tooltip title="Share portal">
          <IconButton size="small" aria-label="share portal" onClick={onShare} data-testid="share">
            <ShareIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="Edit portal configuration">
          <IconButton
            size="small"
            aria-label="edit portal configuration"
            disabled={portalMode === 'edit' || portalMode === 'read-only'}
            onClick={() => setPortalMode('edit')}
            data-testid="portal-edit-portal-mode">
            <EditIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="Edit representations">
          <IconButton
            size="small"
            aria-label="edit representations"
            disabled={portalMode === 'direct' || portalMode === 'read-only'}
            onClick={() => setPortalMode('direct')}
            data-testid="portal-edit-representations-mode">
            <PanToolIcon />
          </IconButton>
        </Tooltip>
      </Paper>
      {modalElement}
    </>
  );
};
