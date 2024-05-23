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
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import { makeStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { useCallback, useRef } from 'react';
import { Tool } from '../../Tool';
import { GQLSingleClickOnDiagramElementTool, GQLTool } from '../Palette.types';
import { useDiagramPalette } from '../useDiagramPalette';
import { ToolSectionProps } from './ToolSection.types';

const useToolSectionStyles = makeStyles((theme) => ({
  toolSection: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: '8px',
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

  const classes = useToolSectionStyles();

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
        <div className={classes.toolSection} data-testid={toolSection.label}>
          <Tool tool={defaultTool} onClick={() => onToolClick(defaultTool)} thumbnail />
          {caretContent}
        </div>
      )}
      <Popper
        open={toolSectionExpandId === toolSection.id}
        anchorEl={anchorRef.current}
        placement="bottom-start"
        transition
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
