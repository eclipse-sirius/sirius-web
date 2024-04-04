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

import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import { makeStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PhotoSizeSelectSmallIcon from '@material-ui/icons/PhotoSizeSelectSmall';
import TonalityIcon from '@material-ui/icons/Tonality';
import VerticalAlignCenterIcon from '@material-ui/icons/VerticalAlignCenter';
import ViewColumnIcon from '@material-ui/icons/ViewColumn';
import ViewModuleIcon from '@material-ui/icons/ViewModule';
import ViewStreamIcon from '@material-ui/icons/ViewStream';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import { memo, useCallback, useRef, useState } from 'react';
import { Edge, Node, OnSelectionChangeFunc, useOnSelectionChange, useReactFlow } from 'reactflow';
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
import { ContextualPaletteStyleProps } from '../Palette.types';
import { PalettePortal } from '../PalettePortal';
import { PaletteTool } from '../PaletteTool';
import { GroupPaletteProps, GroupPaletteSectionTool, GroupPaletteState } from './GroupPalette.types';

const usePaletteStyle = makeStyles((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
  paletteContent: {
    display: 'grid',
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
    gridTemplateRows: theme.spacing(3.5),
    gridAutoRows: theme.spacing(3.5),
    placeItems: 'center',
  },
  toolSection: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: theme.spacing(1),
  },
  toolList: {
    padding: theme.spacing(0.5),
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    width: 'max-content',
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

const isSelectionContainingEdge = (selectedEdges: Edge[]) => selectedEdges.length > 0;
const canSelectedNodesBeDistributed = (selectedNodes: Node[]) => selectedNodes.length < 2;

export const GroupPalette = memo(
  ({ x, y, isOpened, refElementId, hidePalette, resolveNodeOverlap }: GroupPaletteProps) => {
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
    } = useDistributeElements(resolveNodeOverlap);
    const [state, setState] = useState<GroupPaletteState>({
      selectedElementIds: [],
      isMinimalPalette: false,
      isDistributeElementToolSectionExpand: false,
      lastDistributeElementToolId: null,
    });
    const { getNode } = useReactFlow<NodeData, EdgeData>();

    const onChange: OnSelectionChangeFunc = useCallback(({ nodes, edges }) => {
      const selectedNodes = nodes.filter((node: Node) => node.selected);
      const selectedListChildFiltered = selectedNodes.filter((node: Node) => {
        const parent = getNode(node.parentNode ?? '');
        if (parent) {
          return !isListData(parent);
        }
        return true;
      });
      const selectedEdges = edges.filter((edge) => edge.selected);

      const isMinimalPalette =
        isSelectionContainingEdge(selectedEdges) || canSelectedNodesBeDistributed(selectedListChildFiltered);

      const computeSelectedNodes = isMinimalPalette ? selectedNodes : selectedListChildFiltered;
      const selectedElements = [...computeSelectedNodes, ...selectedEdges];
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

    const toolCount = state.isMinimalPalette ? 2 : 4;
    const classes = usePaletteStyle({ toolCount });
    const anchorRef = useRef<SVGSVGElement | null>(null);

    const distributeElementTools: GroupPaletteSectionTool[] = state.isMinimalPalette
      ? []
      : [
          {
            id: 'distribute-element-horizontally',
            title: 'Distribute elements horizontally',
            action: () => distributeGapHorizontally(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon style={{ transform: 'rotate(90deg)' }} fontSize="small" />,
          },
          {
            id: 'distribute-element-vertically',
            title: 'Distribute elements vertically',
            action: () => distributeGapVertically(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon fontSize="small" />,
          },
          {
            id: 'align-left',
            title: 'Align left',
            action: () => distributeAlignLeft(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalLeftIcon fontSize="small" />,
          },
          {
            id: 'align-right',
            title: 'Align right',
            action: () => distributeAlignRight(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalRightIcon fontSize="small" />,
          },
          {
            id: 'align-center',
            title: 'Align center',
            action: () => distributeAlignCenter(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalCenterIcon fontSize="small" />,
          },
          {
            id: 'align-top',
            title: 'Align top',
            action: () => distributeAlignTop(state.selectedElementIds, refElementId),
            icon: <AlignVerticalTopIcon fontSize="small" />,
          },
          {
            id: 'align-bottom',
            title: 'Align bottom',
            action: () => distributeAlignBottom(state.selectedElementIds, refElementId),
            icon: <AlignVerticalBottomIcon fontSize="small" />,
          },
          {
            id: 'align-middle',
            title: 'Align middle',
            action: () => distributeAlignMiddle(state.selectedElementIds, refElementId),
            icon: <AlignVerticalCenterIcon fontSize="small" />,
          },
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
          {
            id: 'make-same-size',
            title: 'Make same size',
            action: () => makeNodesSameSize(state.selectedElementIds, refElementId),
            icon: <PhotoSizeSelectSmallIcon fontSize="small" />,
          },
        ];

    const shouldRender = state.selectedElementIds.length > 1 && isOpened && x && y;
    if (!shouldRender) {
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
      distributeElementTools.find((tool) => tool.id === state.lastDistributeElementToolId) ?? distributeElementTools[0];

    return (
      <PalettePortal>
        <Paper className={classes.palette} style={{ position: 'absolute', left: x, top: y }} data-testid="GroupPalette">
          <div className={classes.paletteContent}>
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
              transition
              disablePortal
              style={{ zIndex: 9999 }}>
              <Paper className={classes.toolList} elevation={2}>
                <ClickAwayListener
                  onClickAway={() =>
                    setState((prevState) => ({ ...prevState, isDistributeElementToolSectionExpand: false }))
                  }>
                  <div>
                    {distributeElementTools.map((tool) => (
                      <PaletteTool
                        toolName={tool.title}
                        onClick={() => handleDistributeElementToolClick(tool)}
                        key={tool.id}>
                        {tool.icon}
                      </PaletteTool>
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
          </div>
        </Paper>
      </PalettePortal>
    );
  }
);
