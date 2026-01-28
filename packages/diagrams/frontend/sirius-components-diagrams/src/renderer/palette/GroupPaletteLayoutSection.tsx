/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { PaletteExtensionSectionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import PhotoSizeSelectSmallIcon from '@mui/icons-material/PhotoSizeSelectSmall';
import VerticalAlignCenterIcon from '@mui/icons-material/VerticalAlignCenter';
import ViewColumnIcon from '@mui/icons-material/ViewColumn';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import ViewStreamIcon from '@mui/icons-material/ViewStream';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { AlignHorizontalCenterIcon } from '../../icons/AlignHorizontalCenterIcon';
import { AlignHorizontalLeftIcon } from '../../icons/AlignHorizontalLeftIcon';
import { AlignHorizontalRightIcon } from '../../icons/AlignHorizontalRightIcon';
import { AlignVerticalBottomIcon } from '../../icons/AlignVerticalBottomIcon';
import { AlignVerticalCenterIcon } from '../../icons/AlignVerticalCenterIcon';
import { AlignVerticalTopIcon } from '../../icons/AlignVerticalTopIcon';
import { JustifyHorizontalIcon } from '../../icons/JustifyHorizontalIcon';
import { JustifyVerticalIcon } from '../../icons/JustifyVerticalIcon';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDistributeElements } from '../layout/useDistributeElements';
import { ListNodeData } from '../node/ListNode.types';

const useStyle = makeStyles()((theme) => ({
  distributeElementList: {
    display: 'flex',
    flexDirection: 'column',
  },
  toolList: {
    padding: theme.spacing(0.5),
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    width: 'max-content',
  },
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  sectionTitleListItemText: {
    '& .MuiListItemText-primary': {
      fontWeight: theme.typography.fontWeightBold,
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  distributeElementTool: {
    flexDirection: 'row',
    cursor: 'pointer',
  },
  distributeElementSeparator: {
    borderTop: `1px solid ${theme.palette.divider}`,
  },
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

export const GroupPaletteLayoutSection = ({
  onBackToMainList,
  diagramElementIds,
}: PaletteExtensionSectionComponentProps) => {
  const { classes } = useStyle();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'groupPalette' });
  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

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
  const { getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';
  const filteredNodeIds = diagramElementIds.filter((elementId) => {
    const node = getNode(elementId);
    if (node) {
      const parent = getNode(node.parentId ?? '');
      if (parent) {
        return !isListData(parent);
      }
    }
    return true;
  });
  const refElementId = filteredNodeIds.at(diagramElementIds.length - 1);
  if (!refElementId || filteredNodeIds.length < 2) {
    return null;
  }

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title={t('layout')} key="tooltip_layout" placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-layout`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={t('layout')} />
        </ListItemButton>
      </Tooltip>

      <ListItem
        id={'align-left'}
        label={t('alignLeft')}
        icon={<AlignHorizontalLeftIcon fontSize="small" />}
        onClick={() => distributeAlignLeft(filteredNodeIds, refElementId)}></ListItem>
      <ListItem
        id={'align-center'}
        label={t('alignCenter')}
        icon={<AlignHorizontalCenterIcon fontSize="small" />}
        onClick={() => distributeAlignCenter(filteredNodeIds, refElementId)}></ListItem>
      <ListItem
        id={'align-right'}
        label={t('alignRight')}
        icon={<AlignHorizontalRightIcon fontSize="small" />}
        onClick={() => distributeAlignRight(filteredNodeIds, refElementId)}></ListItem>
      <Divider />

      <ListItem
        id={'align-top'}
        label={t('alignTop')}
        icon={<AlignVerticalTopIcon fontSize="small" />}
        onClick={() => distributeAlignTop(filteredNodeIds, refElementId)}></ListItem>
      <ListItem
        id={'align-middle'}
        label={t('alignMiddle')}
        icon={<AlignVerticalCenterIcon fontSize="small" />}
        onClick={() => distributeAlignMiddle(filteredNodeIds, refElementId)}></ListItem>
      <ListItem
        id={'align-bottom'}
        label={t('alignBottom')}
        icon={<AlignVerticalBottomIcon fontSize="small" />}
        onClick={() => distributeAlignBottom(filteredNodeIds, refElementId)}></ListItem>
      <Divider />

      <ListItem
        id={'arrange-in-row'}
        label={t('arrangeInRow')}
        icon={<ViewColumnIcon fontSize="small" />}
        onClick={() => arrangeInRow(filteredNodeIds)}></ListItem>
      <ListItem
        id={'arrange-in-column'}
        label={t('arrangeInColumn')}
        icon={<ViewStreamIcon fontSize="small" />}
        onClick={() => arrangeInColumn(filteredNodeIds)}></ListItem>
      <ListItem
        id={'arrange-in-grid'}
        label={t('arrangeInGrid')}
        icon={<ViewModuleIcon fontSize="small" />}
        onClick={() => arrangeInGrid(filteredNodeIds)}></ListItem>
      <Divider />

      <ListItem
        id={'distribute-horizontal'}
        label={t('distributeHorizontal')}
        icon={<VerticalAlignCenterIcon style={{ transform: 'rotate(90deg)' }} fontSize="small" />}
        onClick={() => distributeGapHorizontally(filteredNodeIds)}></ListItem>
      <ListItem
        id={'distribute-vertical'}
        label={t('distributeVertical')}
        icon={<VerticalAlignCenterIcon fontSize="small" />}
        onClick={() => distributeGapVertically(filteredNodeIds)}></ListItem>
      <Divider />

      <ListItem
        id={'justify-horizontally'}
        label={t('justifyHorizontally')}
        icon={<JustifyHorizontalIcon fontSize="small" />}
        onClick={() => justifyHorizontally(filteredNodeIds, refElementId)}></ListItem>
      <ListItem
        id={'justify-vertically'}
        label={t('justifyVertically')}
        icon={<JustifyVerticalIcon fontSize="small" />}
        onClick={() => justifyVertically(filteredNodeIds, refElementId)}></ListItem>
      <Divider />

      <ListItem
        id={'make-same-size'}
        label={t('makeSameSize')}
        icon={<PhotoSizeSelectSmallIcon fontSize="small" />}
        onClick={() => makeNodesSameSize(filteredNodeIds, refElementId)}></ListItem>
    </List>
  );
};
