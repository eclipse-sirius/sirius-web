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

import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow, useViewport } from '@xyflow/react';
import React from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DraggablePalette } from './draggable-palette/DraggablePalette';
import { PaletteEntry, ToolSection } from './draggable-palette/DraggablePalette.types';
import {
  DiagramPaletteToolContributionComponentProps,
  DiagramPaletteToolContributionProps,
} from './extensions/DiagramPaletteToolContribution.types';
import { diagramPaletteToolExtensionPoint } from './extensions/DiagramPaletteToolExtensionPoints';
import {
  GQLPaletteDivider,
  GQLPaletteEntry,
  GQLSingleClickOnDiagramElementTool,
  GQLToolSection,
  PaletteProps,
} from './Palette.types';
import { useSingleElementQuickAccessTools } from './quick-access-tool/useSingleElementQuickAccessTools';
import { useDiagramPalette } from './useDiagramPalette';
import { usePalette } from './usePalette';

export const isSingleClickOnDiagramElementTool = (tool: GQLPaletteEntry): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteEntry): entry is GQLPaletteDivider =>
  entry.__typename === 'PaletteDivider';

export const Palette = ({
  x: paletteX,
  y: paletteY,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
  onEscape,
}: PaletteProps) => {
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  let x: number = 0;
  let y: number = 0;
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  if (viewportZoom !== 0 && paletteX && paletteY) {
    x = (paletteX - viewportX) / viewportZoom;
    y = (paletteY - viewportY) / viewportZoom;
  }
  const { handleToolClick, palette } = usePalette({ x, y, diagramElementId, onDirectEditClick, targetObjectId });

  const paletteToolData: DataExtension<DiagramPaletteToolContributionProps[]> = useData(
    diagramPaletteToolExtensionPoint
  );

  const diagramElement: Node<NodeData> | Edge<EdgeData> | undefined =
    getEdges().find((edge) => edge.id === diagramElementId) ?? getNodes().find((node) => node.id === diagramElementId);

  const paletteToolComponents: React.ComponentType<DiagramPaletteToolContributionComponentProps>[] = diagramElement
    ? paletteToolData.data.filter((data) => data.canHandle(diagramElement)).map((data) => data.component)
    : [];

  const paletteEntries: PaletteEntry[] = palette ? filterSingleClickOnDiagramElementTool(palette?.paletteEntries) : [];

  const { quickAccessToolComponents } = useSingleElementQuickAccessTools({
    diagramElementId: diagramElementId,
    onToolClick: handleToolClick,
    diagramElement: diagramElement ?? null,
    quickAccessTools: palette?.quickAccessTools ?? [],
    paletteToolComponents,
    x,
    y,
  });

  const toolCount = paletteEntries.length + quickAccessToolComponents.length;

  const { getLastToolInvoked } = useDiagramPalette();
  const lastToolInvoked = palette ? getLastToolInvoked(palette.id) : null;
  const lastSingleClickOnDiagramElementToolInvoked =
    lastToolInvoked && isSingleClickOnDiagramElementTool(lastToolInvoked) ? lastToolInvoked : null;
  const shouldRender = palette && (diagramElement || (!diagramElement && toolCount > 0));
  if (!shouldRender) {
    return null;
  }

  return (
    <DraggablePalette
      x={paletteX}
      y={paletteY}
      onToolClick={handleToolClick}
      paletteEntries={paletteEntries}
      quickAccessToolComponents={quickAccessToolComponents}
      lastToolInvoked={lastSingleClickOnDiagramElementToolInvoked}
      onEscape={onEscape}
    />
  );
};

// This Palette is only intended to be use following a single click on the diagram or a diagram element.
const filterSingleClickOnDiagramElementTool = (paletteEntries: GQLPaletteEntry[]): PaletteEntry[] => {
  const filteredPaletteEntries: PaletteEntry[] = [];
  paletteEntries.forEach((paletteEntry) => {
    if (isSingleClickOnDiagramElementTool(paletteEntry) || isPaletteDivider(paletteEntry)) {
      filteredPaletteEntries.push(paletteEntry);
    } else if (isToolSection(paletteEntry)) {
      const currentToolSection = paletteEntry as GQLToolSection;
      const filteredTools = currentToolSection.tools.filter(isSingleClickOnDiagramElementTool);
      if (filteredTools.length > 0) {
        const filteredToolSection: ToolSection = {
          ...currentToolSection,
          tools: filteredTools,
          __typename: 'ToolSection',
        };
        filteredPaletteEntries.push(filteredToolSection);
      }
    }
  });
  return filteredPaletteEntries;
};
