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
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import { useCallback, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { Tool } from '../../Tool';
import { GQLSingleClickOnDiagramElementTool, GQLTool } from '../Palette.types';
import { useDiagramPalette } from '../useDiagramPalette';
import { ToolSectionProps } from './ToolSection.types';

const useToolSectionStyles = makeStyles()((theme) => ({
  toolSection: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    width: theme.spacing(4.5),
  },
  toolList: {
    padding: '4px',
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    width: 'max-content',
  },
  arrow: {
    cursor: 'pointer',
    height: '14px',
    width: '14px',
    marginLeft: '-4px',
    marginTop: '12px',
  },
}));

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const ToolSection = ({ toolSection, onToolClick, toolSectionExpandId, onExpand }: ToolSectionProps) => {
  const tools = toolSection.tools.filter(isSingleClickOnDiagramElementTool);
  const { getLastToolInvoked, setLastToolInvoked } = useDiagramPalette();

  const { classes } = useToolSectionStyles();

  const onActiveTool = useCallback(
    (tool) => {
      onToolClick(tool);
      setLastToolInvoked(toolSection.id, tool);
    },
    [onToolClick, setLastToolInvoked, toolSection.id]
  );

  const anchorRef = useRef<SVGSVGElement | null>(null);
  let caretContent: JSX.Element | undefined;
  if (tools.length > 1) {
    caretContent = (
      <ExpandMoreIcon
        className={classes.arrow}
        style={{ fontSize: 20 }}
        onClick={(event) => {
          event.stopPropagation();
          onExpand(toolSectionExpandId === toolSection.id ? null : toolSection.id);
        }}
        data-testid="expand"
        ref={anchorRef}
      />
    );
  }

  const onMouseEnter = () => {
    if (tools.length > 1) {
      onExpand(toolSection.id);
    }
  };

  const checkLastToolInvoked = (): GQLTool | undefined => {
    const lastToolInvoked = getLastToolInvoked(toolSection.id);
    if (lastToolInvoked && tools.some((tool) => tool.id === lastToolInvoked.id)) {
      return lastToolInvoked;
    }
    return undefined;
  };

  const defaultTool: GQLTool | undefined = checkLastToolInvoked() || tools[0];

  return (
    <>
      {defaultTool && (
        <div className={classes.toolSection} data-testid={toolSection.label} onMouseEnter={onMouseEnter}>
          <Tool tool={defaultTool} onClick={() => onToolClick(defaultTool)} thumbnail />
          {caretContent}
        </div>
      )}
      <Popper
        open={toolSectionExpandId === toolSection.id}
        anchorEl={anchorRef.current}
        placement="bottom-start"
        disablePortal
        style={{ zIndex: 9999 }}>
        <Paper className={classes.toolList} elevation={2}>
          <ClickAwayListener onClickAway={() => onExpand(null)}>
            <div>
              {tools.map((tool) => (
                <Tool tool={tool} onClick={() => onActiveTool(tool)} key={tool.id} />
              ))}
            </div>
          </ClickAwayListener>
        </Paper>
      </Popper>
    </>
  );
};
