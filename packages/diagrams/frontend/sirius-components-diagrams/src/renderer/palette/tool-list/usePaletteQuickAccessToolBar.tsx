/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import AdjustIcon from '@mui/icons-material/Adjust';
import TonalityIcon from '@mui/icons-material/Tonality';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { PinIcon } from '../../../icons/PinIcon';
import { UnpinIcon } from '../../../icons/UnpinIcon';
import { Tool } from '../../Tool';
import { useAdjustSize } from '../../adjust-size/useAdjustSize';
import { useFadeDiagramElements } from '../../fade/useFadeDiagramElements';
import { usePinDiagramElements } from '../../pin/usePinDiagramElements';
import { isSingleClickOnDiagramElementTool } from '../Palette';
import {
  UsePaletteQuickAccessToolBarProps,
  UsePaletteQuickAccessToolBarValue,
} from './usePaletteQuickAccessToolBar.types';

const useStyle = makeStyles()((theme) => ({
  quickAccessTools: {
    display: 'flex',
    flexWrap: 'nowrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    overflowX: 'auto',
  },
  toolIcon: {
    width: theme.spacing(4.5),
    color: theme.palette.text.primary,
  },
}));

export const usePaletteQuickAccessToolBar = ({
  node,
  diagramElementId,
  palette,
  onToolClick,
  paletteToolComponents,
  hideableDiagramElement,
  x,
  y,
}: UsePaletteQuickAccessToolBarProps): UsePaletteQuickAccessToolBarValue => {
  const { classes } = useStyle();
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const { adjustSize } = useAdjustSize();
  let pinUnpinTool: JSX.Element | undefined;
  let adjustSizeTool: JSX.Element | undefined;
  if (node) {
    pinUnpinTool = node.data.pinned ? (
      <Tooltip title="Unpin element" key={'tooltip_unpin_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Unpin element"
          onClick={() => pinDiagramElements([diagramElementId], !node.data.pinned)}
          data-testid="Unpin-element">
          <UnpinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    ) : (
      <Tooltip title="Pin element" key={'tooltip_pin_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Pin element"
          onClick={() => pinDiagramElements([diagramElementId], true)}
          data-testid="Pin-element">
          <PinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
    adjustSizeTool = (
      <Tooltip title="Adjust size" key={'tooltip_adjust_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Adjust element"
          onClick={() => adjustSize(diagramElementId)}
          data-testid="adjust-element">
          <AdjustIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
  }

  const invokeFadeDiagramElementTool = () => {
    fadeDiagramElements([diagramElementId], true);
  };
  const quickAccessToolComponents: JSX.Element[] = [];
  palette?.quickAccessTools
    .filter(isSingleClickOnDiagramElementTool)
    .forEach((tool) =>
      quickAccessToolComponents.push(<Tool tool={tool} onClick={onToolClick} thumbnail key={'tool_' + tool.id} />)
    );

  paletteToolComponents.forEach((PaletteToolComponent, index) =>
    quickAccessToolComponents.push(
      <PaletteToolComponent
        x={x}
        y={y}
        diagramElementId={diagramElementId}
        key={'paletteToolComponents_' + index.toString()}
      />
    )
  );
  if (hideableDiagramElement) {
    quickAccessToolComponents.push(
      <Tooltip title="Fade element" key={'tooltip_fade_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Fade element"
          onClick={invokeFadeDiagramElementTool}
          data-testid="Fade-element">
          <TonalityIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );

    if (pinUnpinTool) {
      quickAccessToolComponents.push(pinUnpinTool);
    }
    if (adjustSizeTool) {
      quickAccessToolComponents.push(adjustSizeTool);
    }
  }
  let element: JSX.Element | null = null;
  if (quickAccessToolComponents.length > 0) {
    element = <Box className={classes.quickAccessTools}>{quickAccessToolComponents}</Box>;
  }
  return { element };
};
