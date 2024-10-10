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

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import PhotoSizeSelectSmallIcon from '@mui/icons-material/PhotoSizeSelectSmall';
import TonalityIcon from '@mui/icons-material/Tonality';
import VerticalAlignCenterIcon from '@mui/icons-material/VerticalAlignCenter';
import ViewColumnIcon from '@mui/icons-material/ViewColumn';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import ViewStreamIcon from '@mui/icons-material/ViewStream';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import { Edge, Node, OnSelectionChangeFunc, useOnSelectionChange, useReactFlow } from '@xyflow/react';
import { Fragment, memo, useCallback, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { AlignHorizontalCenterIcon } from '../../../icons/AlignHorizontalCenterIcon';
import { AlignHorizontalLeftIcon } from '../../../icons/AlignHorizontalLeftIcon';
import { AlignHorizontalRightIcon } from '../../../icons/AlignHorizontalRightIcon';
import { AlignVerticalBottomIcon } from '../../../icons/AlignVerticalBottomIcon';
import { AlignVerticalCenterIcon } from '../../../icons/AlignVerticalCenterIcon';
import { AlignVerticalTopIcon } from '../../../icons/AlignVerticalTopIcon';
import { JustifyHorizontalIcon } from '../../../icons/JustifyHorizontalIcon';
import { JustifyVerticalIcon } from '../../../icons/JustifyVerticalIcon';
import { PinIcon } from '../../../icons/PinIcon';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useFadeDiagramElements } from '../../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { useDistributeElements } from '../../layout/useDistributeElements';
import { ListNodeData } from '../../node/ListNode.types';
import { usePinDiagramElements } from '../../pin/usePinDiagramElements';
import { PalettePortal } from '../PalettePortal';
import { PaletteTool } from '../PaletteTool';
import { GroupPaletteProps, GroupPaletteSectionTool, GroupPaletteState } from './GroupPalette.types';

const usePaletteStyle = makeStyles()((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    zIndex: 5,
    position: 'fixed',
    display: 'flex',
    flexWrap: 'wrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    maxWidth: theme.spacing(45.25),
  },
  toolSection: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    width: theme.spacing(4.5),
  },
  toolList: {
    padding: theme.spacing(0.5),
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    width: 'max-content',
  },
  distributeElementList: {
    display: 'flex',
    flexDirection: 'column',
  },
  distributeElementSeparator: {
    borderTop: `1px solid ${theme.palette.divider}`,
  },
  distributeElementTool: {
    flexDirection: 'row',
    cursor: 'pointer',
  },
  arrow: {
    cursor: 'pointer',
    height: '14px',
    width: '14px',
    marginLeft: -theme.spacing(0.5),
    marginTop: theme.spacing(1.5),
  },
}));

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const canSelectedNodesBeDistributed = (selectedNodes: Node[]) => selectedNodes.length > 1;

export const GroupPalette = memo(({ x, y, isOpened, refElementId, hidePalette }: GroupPaletteProps) => {
  const { hideDiagramElements } = useHideDiagramElements();
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const {
    distributeGapVertically,
    distributeGapHorizontally,
    distributeAlignLeft,
    distributeAlignRight,
    distributeAlignCenter,
    distributeAlignTop,
    distributeAlignBottom,
    distributeAlignMiddle,
    justifyHorizontally,
    justifyVertically,
    arrangeInRow,
    arrangeInColumn,
    arrangeInGrid,
    makeNodesSameSize,
  } = useDistributeElements();
  const [state, setState] = useState<GroupPaletteState>({
    selectedElementIds: [],
    isMinimalPalette: false,
    isDistributeElementToolSectionExpand: false,
    lastDistributeElementToolId: null,
  });
  const { getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const onChange: OnSelectionChangeFunc = useCallback(({ nodes, edges }) => {
    const selectedListChildFiltered = nodes.filter((node: Node) => {
      const parent = getNode(node.parentId ?? '');
      if (parent) {
        return !isListData(parent);
      }
      return true;
    });
    const selectedEdges = edges.filter((edge) => edge.selected);

    const isMinimalPalette = !canSelectedNodesBeDistributed(selectedListChildFiltered);

    const selectedElements = isMinimalPalette ? [...nodes, ...selectedEdges] : [...selectedListChildFiltered];
    if (selectedElements.length > 1) {
      setState((prevState) => ({
        ...prevState,
        selectedElementIds: selectedElements.map((element) => element.id),
        isMinimalPalette,
      }));
    } else {
      setState((prevState) => ({ ...prevState, selectedElementIds: [] }));
    }
  }, []);
  useOnSelectionChange({
    onChange,
  });

  const { classes } = usePaletteStyle();
  const anchorRef = useRef<SVGSVGElement | null>(null);

  const distributeElementTools: GroupPaletteSectionTool[][] = state.isMinimalPalette
    ? []
    : [
        [
          {
            id: 'align-left',
            title: 'Align left',
            action: () => distributeAlignLeft(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalLeftIcon fontSize="small" />,
          },
          {
            id: 'align-center',
            title: 'Align center',
            action: () => distributeAlignCenter(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalCenterIcon fontSize="small" />,
          },
          {
            id: 'align-right',
            title: 'Align right',
            action: () => distributeAlignRight(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalRightIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'align-top',
            title: 'Align top',
            action: () => distributeAlignTop(state.selectedElementIds, refElementId),
            icon: <AlignVerticalTopIcon fontSize="small" />,
          },
          {
            id: 'align-middle',
            title: 'Align middle',
            action: () => distributeAlignMiddle(state.selectedElementIds, refElementId),
            icon: <AlignVerticalCenterIcon fontSize="small" />,
          },
          {
            id: 'align-bottom',
            title: 'Align bottom',
            action: () => distributeAlignBottom(state.selectedElementIds, refElementId),
            icon: <AlignVerticalBottomIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'arrange-in-row',
            title: 'Arrange in row',
            action: () => arrangeInRow(state.selectedElementIds),
            icon: <ViewColumnIcon fontSize="small" />,
          },
          {
            id: 'arrange-in-column',
            title: 'Arrange in column',
            action: () => arrangeInColumn(state.selectedElementIds),
            icon: <ViewStreamIcon fontSize="small" />,
          },
          {
            id: 'arrange-in-grid',
            title: 'Arrange in grid',
            action: () => arrangeInGrid(state.selectedElementIds),
            icon: <ViewModuleIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'distribute-horizontal-spacing',
            title: 'Distribute horizontal spacing',
            action: () => distributeGapHorizontally(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon style={{ transform: 'rotate(90deg)' }} fontSize="small" />,
          },
          {
            id: 'distribute-vertical-spacing',
            title: 'Distribute vertical spacing',
            action: () => distributeGapVertically(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'justify-horizontally',
            title: 'Justify horizontally',
            action: () => justifyHorizontally(state.selectedElementIds, refElementId),
            icon: <JustifyHorizontalIcon fontSize="small" />,
          },
          {
            id: 'justify-vertically',
            title: 'Justify vertically',
            action: () => justifyVertically(state.selectedElementIds, refElementId),
            icon: <JustifyVerticalIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'make-same-size',
            title: 'Make same size',
            action: () => makeNodesSameSize(state.selectedElementIds, refElementId),
            icon: <PhotoSizeSelectSmallIcon fontSize="small" />,
          },
        ],
      ];

  const shouldRender = state.selectedElementIds.length > 1 && isOpened && x && y;
  if (!shouldRender) {
    if (state.isDistributeElementToolSectionExpand) {
      setState((prevState) => ({ ...prevState, isDistributeElementToolSectionExpand: false }));
    }
    return null;
  }

  let caretContent: JSX.Element | undefined;
  caretContent = (
    <ExpandMoreIcon
      className={classes.arrow}
      style={{ fontSize: 20 }}
      onClick={(event) => {
        event.stopPropagation();
        setState((prevState) => ({ ...prevState, isDistributeElementToolSectionExpand: true }));
      }}
      data-testid="expand"
      ref={anchorRef}
    />
  );
  const handleDistributeElementToolClick = (tool: GroupPaletteSectionTool) => {
    tool.action();
    hidePalette();
    setState((prevState) => ({
      ...prevState,
      lastDistributeElementToolId: tool.id,
      isDistributeElementToolSectionExpand: false,
    }));
  };

  const defaultDistributeTool: GroupPaletteSectionTool | undefined =
    distributeElementTools
      .flatMap((toolSection) => toolSection)
      .find((tool) => tool.id === state.lastDistributeElementToolId) ?? distributeElementTools[0]?.[0];

  return (
    <PalettePortal>
      <Paper className={classes.palette} style={{ position: 'absolute', left: x, top: y }} data-testid="GroupPalette">
        {defaultDistributeTool && (
          <div className={classes.toolSection}>
            <PaletteTool
              toolName={defaultDistributeTool.title}
              onClick={() => handleDistributeElementToolClick(defaultDistributeTool)}
              key={defaultDistributeTool.id}>
              {defaultDistributeTool.icon}
            </PaletteTool>
            {caretContent}
          </div>
        )}
        <Popper
          open={state.isDistributeElementToolSectionExpand}
          anchorEl={anchorRef.current}
          placement="bottom-start"
          disablePortal
          style={{ zIndex: 9999 }}>
          <Paper className={classes.toolList} elevation={2}>
            <ClickAwayListener
              onClickAway={() => {
                setState((prevState) => ({ ...prevState, isDistributeElementToolSectionExpand: false }));
              }}>
              <div className={classes.distributeElementList}>
                {distributeElementTools.map((toolSection, index) => (
                  <Fragment key={index}>
                    {toolSection.map((tool) => (
                      <div
                        className={classes.distributeElementTool}
                        onClick={() => handleDistributeElementToolClick(tool)}
                        key={tool.id}>
                        <PaletteTool
                          toolName={tool.title}
                          onClick={() => handleDistributeElementToolClick(tool)}
                          key={tool.id}>
                          {tool.icon}
                        </PaletteTool>
                        {tool.title}
                      </div>
                    ))}
                    {index < distributeElementTools.length - 1 && <hr className={classes.distributeElementSeparator} />}
                  </Fragment>
                ))}
              </div>
            </ClickAwayListener>
          </Paper>
        </Popper>
        <PaletteTool toolName={'Hide elements'} onClick={() => hideDiagramElements(state.selectedElementIds, true)}>
          <VisibilityOffIcon fontSize="small" />
        </PaletteTool>
        <PaletteTool toolName={'Fade elements'} onClick={() => fadeDiagramElements(state.selectedElementIds, true)}>
          <TonalityIcon fontSize="small" />
        </PaletteTool>
        {!state.isMinimalPalette && (
          <PaletteTool toolName={'Pin elements'} onClick={() => pinDiagramElements(state.selectedElementIds, true)}>
            <PinIcon fontSize="small" />
          </PaletteTool>
        )}
      </Paper>
    </PalettePortal>
  );
});
