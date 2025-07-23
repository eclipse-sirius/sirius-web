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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { Edge, InternalNode, Node, useStoreApi } from '@xyflow/react';
import React from 'react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { PaletteAppearanceSectionContributionProps } from '../appearance/extensions/PaletteAppearanceSectionContribution.types';
import { paletteAppearanceSectionExtensionPoint } from '../appearance/extensions/PaletteAppearanceSectionExtensionPoints';
import { PaletteExtensionSectionComponentProps } from '../PaletteExtensionSection.types';
import { EdgeAppearanceSection } from './edge/EdgeAppearanceSection';
import { ImageNodeAppearanceSection } from './ImageNodeAppearanceSection';
import { RectangularNodeAppearanceSection } from './RectangularNodeAppearanceSection';

const useStyle = makeStyles()((theme) => ({
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  toolList: {
    width: '100%',
    padding: 0,
  },
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  sectionTitleListItemText: {
    '& .MuiListItemText-primary': {
      fontWeight: theme.typography.fontWeightBold,
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
}));

const isEdgeElement = (element: Edge<EdgeData> | InternalNode<Node<NodeData>> | undefined): element is Edge<EdgeData> =>
  !!element && (element.type === 'smoothStepEdge' || element.type === 'smartStepEdge');

const isNodeElement = (
  element: Edge<EdgeData> | InternalNode<Node<NodeData>> | undefined
): element is InternalNode<Node<NodeData>> =>
  !!element && element.type !== 'smoothStepEdge' && element.type !== 'smartStepEdge';

const isFreeFormNode = (
  element: Edge<EdgeData> | InternalNode<Node<NodeData>> | undefined
): element is InternalNode<Node<NodeData>> => !!element && element.type === 'freeFormNode';

export const PaletteAppearanceSection = ({
  diagramElementId,
  onBackToMainList,
}: PaletteExtensionSectionComponentProps) => {
  const { classes } = useStyle();
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const diagramElement = nodeLookup.get(diagramElementId) || edgeLookup.get(diagramElementId);

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  const paletteAppearanceSectionData: DataExtension<PaletteAppearanceSectionContributionProps[]> = useData(
    paletteAppearanceSectionExtensionPoint
  );

  const paletteAppearanceSectionComponents: JSX.Element[] = [];
  if (isNodeElement(diagramElement)) {
    paletteAppearanceSectionData.data
      .filter((data) => data.canHandle(diagramElement))
      .map((data) => data.component)
      .forEach((PaletteAppearanceSectionComponent, index) =>
        paletteAppearanceSectionComponents.push(
          <PaletteAppearanceSectionComponent
            element={diagramElement}
            elementId={diagramElementId}
            key={'paletteAppearanceSectionComponents_' + index.toString()}
          />
        )
      );
  }

  if (isFreeFormNode(diagramElement)) {
    if (diagramElement.data.nodeAppearanceData?.gqlStyle.__typename === 'RectangularNodeStyle') {
      paletteAppearanceSectionComponents.push(
        <RectangularNodeAppearanceSection nodeId={diagramElement.id} nodeData={diagramElement.data} />
      );
    }
    if (diagramElement.data.nodeAppearanceData?.gqlStyle.__typename === 'ImageNodeStyle') {
      paletteAppearanceSectionComponents.push(
        <ImageNodeAppearanceSection nodeId={diagramElement.id} nodeData={diagramElement.data} />
      );
    }
  } else if (isEdgeElement(diagramElement) && diagramElement.data) {
    paletteAppearanceSectionComponents.push(
      <EdgeAppearanceSection edgeId={diagramElement.id} edgeData={diagramElement.data} />
    );
  }

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title="Appearance" key="tooltip_appearance" placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-Appearance`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary="Appearance" />
        </ListItemButton>
      </Tooltip>
      {paletteAppearanceSectionComponents.length > 0 ? (
        paletteAppearanceSectionComponents
      ) : (
        <ListItem>
          <Typography>No appearance editor available for this style of element</Typography>
        </ListItem>
      )}
    </List>
  );
};
