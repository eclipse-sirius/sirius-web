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
import { PaletteExtensionSectionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { RectangularNodeAppearanceSection } from './RectangularNodeAppearanceSection';

export const PaletteAppearanceSection = ({ diagramElementId }: PaletteExtensionSectionComponentProps) => {
  const { nodeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const diagramElement = nodeLookup.get(diagramElementId);

  const nodeAppearanceData = diagramElement?.data.nodeAppearanceData;

  return diagramElement && nodeAppearanceData?.gqlStyle.__typename === 'RectangularNodeStyle' ? (
    <RectangularNodeAppearanceSection nodeId={diagramElement.id} nodeData={diagramElement.data as NodeData} />
  ) : (
    <ListItem>
      <Typography>No appearance editor available for this style of element</Typography>
    </ListItem>
  );
};
