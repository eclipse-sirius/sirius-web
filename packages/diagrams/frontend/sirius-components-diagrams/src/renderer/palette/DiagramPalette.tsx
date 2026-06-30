/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import {
  GQLTool,
  PaletteExtensionSection,
  PaletteExtensionSectionProps,
  usePalette,
} from '@eclipse-sirius/sirius-components-palette';
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
import { DraggablePalette } from './DraggablePalette';
import { GroupPaletteLayoutSection } from './GroupPaletteLayoutSection';
import { PalettePortal } from './PalettePortal';
import { ShowInSection } from './ShowInSection';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

export const DIAGRAM_REPRESENTATION_KIND = 'diagram';

export const DiagramPalette = memo(({ diagramId, diagramTargetObjectId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x: paletteX, y: paletteY, representationElementIds, hidePalette } = usePalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);
  const { setCurrentlyEditedLabelId, currentlyEditedLabelId } = useDiagramDirectEdit();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { getNode, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramPalette' });

  const elementId = representationElementIds[0] ? representationElementIds[0] : diagramId;
  const elementsIds = representationElementIds[0] ? representationElementIds : [diagramId];
  let { palette }: UsePaletteContentValue = usePaletteContents(elementsIds, false);

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        hidePalette();
      }
    },
    [hidePalette, isOpened]
  );

  const handleDirectEditClick = useCallback(() => {
    let currentlyEditedLabelId: string | null = null;
    let isLabelEditable = false;
    if (representationElementIds.length === 1 && representationElementIds[0]) {
      const node = getNode(representationElementIds[0]);
      if (node) {
        if (node.data.insideLabel) {
          currentlyEditedLabelId = node.data.insideLabel.id;
        } else if (node.data.outsideLabels.BOTTOM_MIDDLE) {
          currentlyEditedLabelId = node.data.outsideLabels.BOTTOM_MIDDLE.id;
        }
        isLabelEditable = node.data.labelEditable;
      } else {
        const edge = getEdge(representationElementIds[0]);
        if (edge && edge.data && edge.data.label) {
          currentlyEditedLabelId = edge.data.label.id;
          isLabelEditable = edge.data.centerLabelEditable;
        }
      }
      if (isLabelEditable && currentlyEditedLabelId) {
        setCurrentlyEditedLabelId('palette', currentlyEditedLabelId, null);
      }
    }
  }, [representationElementIds]);

  const targetObjectId =
    elementId === diagramId
      ? diagramTargetObjectId
      : getNode(representationElementIds[0] || '')?.data.targetObjectId ||
        getEdge(representationElementIds[0] || '')?.data?.targetObjectId ||
        diagramTargetObjectId;

  const onToolClick = (tool: GQLTool) => {
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }
    executeTool(x, y, elementsIds, targetObjectId, handleDirectEditClick, tool);
  };

  const extensionSections = useMemo(() => {
    const sectionComponents: React.ReactElement<PaletteExtensionSectionProps>[] = [];
    if (representationElementIds.length >= 1) {
      sectionComponents.push(
        <PaletteExtensionSection
          id="appearance"
          key="appearance"
          title={t('appearance')}
          component={PaletteAppearanceSection}
          onClose={hidePalette}
        />
      );
    }
    if (representationElementIds.length > 1) {
      sectionComponents.push(
        <PaletteExtensionSection
          id="layout_section"
          key="layout_section"
          title={t('layout')}
          component={GroupPaletteLayoutSection}
          onClose={hidePalette}
        />
      );
    }
    if (representationElementIds.length >= 1) {
      sectionComponents.push(
        <PaletteExtensionSection
          id="show_in"
          key="show_in"
          title={t('showIn')}
          component={ShowInSection}
          onClose={hidePalette}
        />
      );
    }

    return sectionComponents;
  }, [representationElementIds.join('-')]);

  if (readOnly) {
    return null;
  }

  const shouldRender = isOpened && !!paletteX && !!paletteY && !currentlyEditedLabelId;

  return palette && shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <DraggablePalette
          representationKind={DIAGRAM_REPRESENTATION_KIND}
          representationElementIds={representationElementIds.length > 0 ? representationElementIds : [diagramId]}
          palette={palette}
          onToolClick={onToolClick}
          onClose={hidePalette}
          paletteToolListExtensions={extensionSections}
          x={paletteX}
          y={paletteY}
        />
      </div>
    </PalettePortal>
  ) : null;
});
