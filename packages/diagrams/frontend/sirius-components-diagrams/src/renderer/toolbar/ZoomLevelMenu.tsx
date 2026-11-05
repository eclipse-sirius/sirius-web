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

import Button from '@mui/material/Button';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { Theme } from '@mui/material/styles';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, ReactFlowState, useReactFlow, useStore } from '@xyflow/react';
import { useId, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ZoomLevelMenuState } from './ZoomLevelMenu.types';

const ZOOM_LEVELS: number[] = [25, 50, 75, 100, 150, 200, 400];

/**
 * The zoom level is rounded to a percentage in the selector so that the component is only rendered again when the
 * displayed value changes and not for every intermediate zoom value.
 */
const zoomPercentageSelector = (state: ReactFlowState): number => Math.round(state.transform[2] * 100);
const minZoomSelector = (state: ReactFlowState): number => state.minZoom;
const maxZoomSelector = (state: ReactFlowState): number => state.maxZoom;
const domNodeSelector = (state: ReactFlowState): HTMLDivElement | null => state.domNode;

export const ZoomLevelMenu = (): React.JSX.Element => {
  const [state, setState] = useState<ZoomLevelMenuState>({ open: false });
  const anchorRef = useRef<HTMLButtonElement | null>(null);
  const buttonId: string = useId();
  const menuId: string = useId();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramToolbar' });

  const zoomPercentage: number = useStore(zoomPercentageSelector);
  const minZoom: number = useStore(minZoomSelector);
  const maxZoom: number = useStore(maxZoomSelector);
  // The menu is rendered in the diagram element instead of the body of the document to stay visible in full screen
  const domNode: HTMLDivElement | null = useStore(domNodeSelector);
  const { zoomTo } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const zoomLevels: number[] = ZOOM_LEVELS.filter(
    (zoomLevel) => zoomLevel / 100 >= minZoom && zoomLevel / 100 <= maxZoom
  );

  const onOpen = (): void => setState((prevState) => ({ ...prevState, open: true }));
  const onClose = (): void => setState((prevState) => ({ ...prevState, open: false }));
  const onZoomLevelSelected = (zoomLevel: number): void => {
    zoomTo(zoomLevel / 100, { duration: 200 });
    onClose();
  };

  return (
    <>
      <Tooltip title={t('zoomLevel')} describeChild>
        <Button
          id={buttonId}
          size="small"
          aria-haspopup="menu"
          aria-controls={state.open ? menuId : undefined}
          aria-expanded={state.open}
          onClick={onOpen}
          ref={anchorRef}
          data-testid="zoom-level"
          sx={(theme: Theme) => ({
            // The minimal width prevents the toolbar from changing its width while zooming
            minWidth: theme.spacing(7),
            paddingLeft: theme.spacing(0.5),
            paddingRight: theme.spacing(0.5),
            color: theme.palette.action.active,
            fontVariantNumeric: 'tabular-nums',
            textTransform: 'none',
          })}>
          {`${zoomPercentage}%`}
        </Button>
      </Tooltip>
      {state.open ? (
        <Menu
          id={menuId}
          open={state.open}
          anchorEl={anchorRef.current}
          container={domNode}
          onClose={onClose}
          slotProps={{ list: { 'aria-labelledby': buttonId } }}
          data-testid="zoom-level-menu">
          {zoomLevels.map((zoomLevel) => (
            <MenuItem
              key={zoomLevel}
              selected={zoomLevel === zoomPercentage}
              onClick={() => onZoomLevelSelected(zoomLevel)}
              data-testid={`zoom-level-${zoomLevel}`}>
              <ListItemText primary={`${zoomLevel}%`} />
            </MenuItem>
          ))}
        </Menu>
      ) : null}
    </>
  );
};
