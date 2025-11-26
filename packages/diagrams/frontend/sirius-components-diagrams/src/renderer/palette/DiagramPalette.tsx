/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { PaletteExtensionSection, PaletteExtensionSectionProps } from '@eclipse-sirius/sirius-components-palette';
import { Edge, Node, useReactFlow, useViewport } from '@xyflow/react';
import { memo, useCallback, useContext, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { PaletteAppearanceSection } from './appearance/PaletteAppearanceSection';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { Palette } from './Palette';
import { GQLTool } from './Palette.types';
import { PalettePortal } from './PalettePortal';
import { ShowInSection } from './ShowInSection';
import { useDiagramPalette } from './useDiagramPalette';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

export const DiagramPalette = memo(({ diagramElementId, diagramTargetObjectId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x: paletteX, y: paletteY, diagramElementIds, hideDiagramPalette } = useDiagramPalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);
  const { setCurrentlyEditedLabelId, currentlyEditedLabelId } = useDiagramDirectEdit();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { getNode, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramPalette' });

  const elementId = diagramElementIds[0] ? diagramElementIds[0] : diagramElementId;
  const { palette }: UsePaletteContentValue = usePaletteContents(elementId);

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        hideDiagramPalette();
      }
    },
    [hideDiagramPalette, isOpened]
  );

  const handleDirectEditClick = useCallback(() => {
    let currentlyEditedLabelId: string | null = null;
    let isLabelEditable = false;
    if (diagramElementIds.length === 1 && diagramElementIds[0]) {
      const node = getNode(diagramElementIds[0]);
      if (node) {
        if (node.data.insideLabel) {
          currentlyEditedLabelId = node.data.insideLabel.id;
        } else if (node.data.outsideLabels.BOTTOM_MIDDLE) {
          currentlyEditedLabelId = node.data.outsideLabels.BOTTOM_MIDDLE.id;
        }
        isLabelEditable = node.data.labelEditable;
      } else {
        const edge = getEdge(diagramElementIds[0]);
        if (edge && edge.data && edge.data.label) {
          currentlyEditedLabelId = edge.data.label.id;
          isLabelEditable = edge.data.centerLabelEditable;
        }
      }
      if (isLabelEditable && currentlyEditedLabelId) {
        setCurrentlyEditedLabelId('palette', currentlyEditedLabelId, null);
      }
    }
  }, [diagramElementIds]);

  const targetObjectId =
    elementId === diagramElementId
      ? diagramTargetObjectId
      : getNode(diagramElementIds[0] || '')?.data.targetObjectId ||
        getEdge(diagramElementIds[0] || '')?.data?.targetObjectId ||
        diagramTargetObjectId;

  const onToolClick = (tool: GQLTool) => {
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }
    executeTool(x, y, elementId, targetObjectId, handleDirectEditClick, tool);
  };

  const extensionSections = useMemo(() => {
    const sectionComponents: React.ReactElement<PaletteExtensionSectionProps>[] = [];
    if (diagramElementIds.length === 1) {
      sectionComponents.push(
        <PaletteExtensionSection
          component={PaletteAppearanceSection}
          title={t('appearance')}
          id="appearance"
          onClose={hideDiagramPalette}
          key={'appearance'}
        />
      );
      sectionComponents.push(
        <PaletteExtensionSection
          component={ShowInSection}
          title={t('showIn')}
          id="show_in"
          key={'show_in'}
          onClose={hideDiagramPalette}
        />
      );
    }

    return sectionComponents;
  }, [diagramElementIds.join('-')]);

  if (readOnly) {
    return null;
  }

  const shouldRender = palette && isOpened && paletteX && paletteY && !currentlyEditedLabelId;

  return shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={paletteX}
          y={paletteY}
          diagramElementId={diagramElementIds[0] || diagramElementId}
          palette={palette}
          onToolClick={onToolClick}
          onClose={hideDiagramPalette}
          paletteToolListExtensions={extensionSections}
        />
      </div>
    </PalettePortal>
  ) : null;
});
