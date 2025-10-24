/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import EditIcon from '@mui/icons-material/Edit';
import FullscreenIcon from '@mui/icons-material/Fullscreen';
import FullscreenExitIcon from '@mui/icons-material/FullscreenExit';
import PanToolIcon from '@mui/icons-material/PanTool';
import ShareIcon from '@mui/icons-material/Share';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Tooltip from '@mui/material/Tooltip';
import { useState } from 'react';
import { useFullscreen } from '../hooks/useFullScreen';
import { PortalToolbarProps, PortalToolbarState } from './PortalToolbar.types';

export const PortalToolbar = ({ representationId, fullscreenNode, portalMode, setPortalMode }: PortalToolbarProps) => {
  const { fullscreen, setFullscreen } = useFullscreen(fullscreenNode);
  const [state, setState] = useState<PortalToolbarState>({ modal: null });

  const onShare = () => setState((prevState) => ({ ...prevState, modal: 'share' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  let modalElement: React.ReactElement | null = null;
  if (state.modal === 'share') {
    modalElement = <ShareRepresentationModal representationId={representationId} onClose={closeModal} />;
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
          <span>
            <IconButton
              size="small"
              aria-label="edit portal configuration"
              disabled={portalMode === 'edit' || portalMode === 'read-only'}
              onClick={() => setPortalMode('edit')}
              data-testid="portal-edit-portal-mode">
              <EditIcon />
            </IconButton>
          </span>
        </Tooltip>
        <Tooltip title="Edit representations">
          <span>
            <IconButton
              size="small"
              aria-label="edit representations"
              disabled={portalMode === 'direct' || portalMode === 'read-only'}
              onClick={() => setPortalMode('direct')}
              data-testid="portal-edit-representations-mode">
              <PanToolIcon />
            </IconButton>
          </span>
        </Tooltip>
      </Paper>
      {modalElement}
    </>
  );
};
