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
import { Fragment, memo, useCallback, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Node, OnSelectionChangeFunc, useOnSelectionChange, useReactFlow } from 'reactflow';
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
  const { t } = useTranslation('siriusComponentsDiagrams', { keyPrefix: 'palette' });
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

    const isMinimalPalette = !canSelectedNodesBeDistributed(selectedListChildFiltered);

    const selectedElements = isMinimalPalette ? [...selectedNodes, ...selectedEdges] : [...selectedListChildFiltered];
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

  const distributeElementTools: GroupPaletteSectionTool[][] = state.isMinimalPalette
    ? []
    : [
        [
          {
            id: 'align-left',
            title: t('alignLeft'),
            action: () => distributeAlignLeft(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalLeftIcon fontSize="small" />,
          },
          {
            id: 'align-center',
            title: t('alignCenter'),
            action: () => distributeAlignCenter(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalCenterIcon fontSize="small" />,
          },
          {
            id: 'align-right',
            title: t('alignRight'),
            action: () => distributeAlignRight(state.selectedElementIds, refElementId),
            icon: <AlignHorizontalRightIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'align-top',
            title: t('alignTop'),
            action: () => distributeAlignTop(state.selectedElementIds, refElementId),
            icon: <AlignVerticalTopIcon fontSize="small" />,
          },
          {
            id: 'align-middle',
            title: t('alignMiddle'),
            action: () => distributeAlignMiddle(state.selectedElementIds, refElementId),
            icon: <AlignVerticalCenterIcon fontSize="small" />,
          },
          {
            id: 'align-bottom',
            title: t('alignBottom'),
            action: () => distributeAlignBottom(state.selectedElementIds, refElementId),
            icon: <AlignVerticalBottomIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'arrange-in-row',
            title: t('arrangeInRow'),
            action: () => arrangeInRow(state.selectedElementIds),
            icon: <ViewColumnIcon fontSize="small" />,
          },
          {
            id: 'arrange-in-column',
            title: t('arrangeInColumn'),
            action: () => arrangeInColumn(state.selectedElementIds),
            icon: <ViewStreamIcon fontSize="small" />,
          },
          {
            id: 'arrange-in-grid',
            title: t('arrangeInGrid'),
            action: () => arrangeInGrid(state.selectedElementIds),
            icon: <ViewModuleIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'distribute-horizontal-spacing',
            title: t('distributeHorizontal'),
            action: () => distributeGapHorizontally(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon style={{ transform: 'rotate(90deg)' }} fontSize="small" />,
          },
          {
            id: 'distribute-vertical-spacing',
            title: t('distributeVertical'),
            action: () => distributeGapVertically(state.selectedElementIds),
            icon: <VerticalAlignCenterIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'justify-horizontally',
            title: t('justifyHorizontally'),
            action: () => justifyHorizontally(state.selectedElementIds, refElementId),
            icon: <JustifyHorizontalIcon fontSize="small" />,
          },
          {
            id: 'justify-vertically',
            title: t('justifyVertically'),
            action: () => justifyVertically(state.selectedElementIds, refElementId),
            icon: <JustifyVerticalIcon fontSize="small" />,
          },
        ],
        [
          {
            id: 'make-same-size',
            title: t('makeSameSize'),
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
                      {index < distributeElementTools.length - 1 && (
                        <hr className={classes.distributeElementSeparator} />
                      )}
                    </Fragment>
                  ))}
                </div>
              </ClickAwayListener>
            </Paper>
          </Popper>
          <PaletteTool toolName={t('hideElements')} onClick={() => hideDiagramElements(state.selectedElementIds, true)}>
            <VisibilityOffIcon fontSize="small" />
          </PaletteTool>
          <PaletteTool toolName={t('fadeElements')} onClick={() => fadeDiagramElements(state.selectedElementIds, true)}>
            <TonalityIcon fontSize="small" />
          </PaletteTool>
          {!state.isMinimalPalette && (
            <PaletteTool toolName={t('pinElements')} onClick={() => pinDiagramElements(state.selectedElementIds, true)}>
              <PinIcon fontSize="small" />
            </PaletteTool>
          )}
        </div>
      </Paper>
    </PalettePortal>
  );
});
