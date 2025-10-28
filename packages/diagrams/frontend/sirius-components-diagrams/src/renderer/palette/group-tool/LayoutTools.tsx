/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { PaletteToolContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import PhotoSizeSelectSmallIcon from '@mui/icons-material/PhotoSizeSelectSmall';
import VerticalAlignCenterIcon from '@mui/icons-material/VerticalAlignCenter';
import ViewColumnIcon from '@mui/icons-material/ViewColumn';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import ViewStreamIcon from '@mui/icons-material/ViewStream';
import Divider from '@mui/material/Divider';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { AlignHorizontalCenterIcon } from '../../../icons/AlignHorizontalCenterIcon';
import { AlignHorizontalLeftIcon } from '../../../icons/AlignHorizontalLeftIcon';
import { AlignHorizontalRightIcon } from '../../../icons/AlignHorizontalRightIcon';
import { AlignVerticalBottomIcon } from '../../../icons/AlignVerticalBottomIcon';
import { AlignVerticalCenterIcon } from '../../../icons/AlignVerticalCenterIcon';
import { AlignVerticalTopIcon } from '../../../icons/AlignVerticalTopIcon';
import { JustifyHorizontalIcon } from '../../../icons/JustifyHorizontalIcon';
import { JustifyVerticalIcon } from '../../../icons/JustifyVerticalIcon';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useDistributeElements } from '../../layout/useDistributeElements';
import { useDiagramPalette } from '../useDiagramPalette';

const useStyle = makeStyles()((theme) => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
  },
}));

interface ListItemProps {
  id: string;
  label: string;
  icon: JSX.Element;
  onClick: () => void;
}

const ListItem = ({ id, label, icon, onClick }: ListItemProps) => {
  const { classes } = useStyle();
  return (
    <Tooltip title={label} placement="right" key={id}>
      <ListItemButton className={classes.listItemButton} onClick={onClick} data-testid={`tool-${label}`}>
        <ListItemIcon className={classes.listItemIcon}>{icon}</ListItemIcon>
        <ListItemText primary={label} className={classes.listItemText} />
      </ListItemButton>
    </Tooltip>
  );
};

const useRefElementsId = (representationElementIds: string[]) => {
  const { getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const paletteId = representationElementIds.join('-');
  const isListNode = (nodeId: string) => getNode(nodeId)?.type === 'listNode';
  const filteredNodeIds = representationElementIds.filter((id) => !isListNode(id));
  const refElementId = filteredNodeIds.at(filteredNodeIds.length - 1);
  return {
    paletteId: paletteId,
    refElementId: refElementId,
    filteredNodeIds: filteredNodeIds,
  };
};

export const AlignLeftComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-left';
  const { distributeAlignLeft } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignLeft(filteredNodeIds, refElementId);
  };
  return (
    <ListItem
      id={toolId}
      label={'Align left'}
      icon={<AlignHorizontalLeftIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const AlignCenterComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-center';
  const { distributeAlignCenter } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignCenter(filteredNodeIds, refElementId);
  };
  return (
    <ListItem
      id={toolId}
      label={'Align center'}
      icon={<AlignHorizontalCenterIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const AlignRightComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-right';
  const { distributeAlignRight } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignRight(filteredNodeIds, refElementId);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Align center'}
        icon={<AlignHorizontalRightIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};

export const AlignTopComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-top';
  const { distributeAlignTop } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignTop(filteredNodeIds, refElementId);
  };
  return (
    <ListItem
      id={toolId}
      label={'Align top'}
      icon={<AlignVerticalTopIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const AlignMiddleComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-middle';
  const { distributeAlignMiddle } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignMiddle(filteredNodeIds, refElementId);
  };
  return (
    <ListItem
      id={toolId}
      label={'Align middle'}
      icon={<AlignVerticalCenterIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const AlignBottomComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'align-bottom';
  const { distributeAlignBottom } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeAlignBottom(filteredNodeIds, refElementId);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Align bottom'}
        icon={<AlignVerticalBottomIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};

export const ArrangeInRowComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'arrange-in-row';
  const { arrangeInRow } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    arrangeInRow(filteredNodeIds);
  };
  return (
    <ListItem
      id={toolId}
      label={'Arrange in row'}
      icon={<ViewColumnIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const ArrangeInColumnComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'arrange-in-column';
  const { arrangeInColumn } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    arrangeInColumn(filteredNodeIds);
  };
  return (
    <ListItem
      id={toolId}
      label={'Arrange in column'}
      icon={<ViewStreamIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const ArrangeInGridComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'arrange-in-grid';
  const { arrangeInGrid } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    arrangeInGrid(filteredNodeIds);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Arrange in grid'}
        icon={<ViewModuleIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};

export const DistributeHorizontalComponentTool = ({
  representationElementIds,
}: PaletteToolContributionComponentProps) => {
  const toolId = 'distribute-horizontal';
  const { distributeGapHorizontally } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeGapHorizontally(filteredNodeIds);
  };
  return (
    <ListItem
      id={toolId}
      label={'Distribute horizontal'}
      icon={<VerticalAlignCenterIcon style={{ transform: 'rotate(90deg)' }} fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const DistributeVerticalComponentTool = ({
  representationElementIds,
}: PaletteToolContributionComponentProps) => {
  const toolId = 'distribute-vertical';
  const { distributeGapVertically } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    distributeGapVertically(filteredNodeIds);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Distribute vertical'}
        icon={<VerticalAlignCenterIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};

export const JustifyHorizontalComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'justify-horizontally';
  const { justifyHorizontally } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    justifyHorizontally(filteredNodeIds, refElementId);
  };
  return (
    <ListItem
      id={toolId}
      label={'Justify horizontally'}
      icon={<JustifyHorizontalIcon fontSize="small" />}
      onClick={onClick}></ListItem>
  );
};

export const JustifyVerticallyComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'justify-vertically';
  const { justifyVertically } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    justifyVertically(filteredNodeIds, refElementId);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Justify vertically'}
        icon={<JustifyVerticalIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};

export const MakeSameSizeComponentTool = ({ representationElementIds }: PaletteToolContributionComponentProps) => {
  const toolId = 'make-same-size';
  const { makeNodesSameSize } = useDistributeElements();
  const { setLastToolInvokedId } = useDiagramPalette();
  const { paletteId, refElementId, filteredNodeIds } = useRefElementsId(representationElementIds);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }
  const onClick = () => {
    setLastToolInvokedId(paletteId, toolId);
    makeNodesSameSize(filteredNodeIds, refElementId);
  };
  return (
    <>
      <ListItem
        id={toolId}
        label={'Make same size'}
        icon={<PhotoSizeSelectSmallIcon fontSize="small" />}
        onClick={onClick}></ListItem>
      <Divider />
    </>
  );
};
