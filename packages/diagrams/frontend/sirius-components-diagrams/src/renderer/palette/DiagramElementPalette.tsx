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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { memo, useCallback, useContext, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { PaletteAppearanceSection } from './appearance/PaletteAppearanceSection';
import { DiagramElementPaletteProps } from './DiagramElementPalette.types';
import { Palette } from './Palette';
import { PalettePortal } from './PalettePortal';
import { ShowInSection } from './ShowInSection';
import { useDiagramElementPalette } from './useDiagramElementPalette';

export const DiagramElementPalette = memo(
  ({ diagramElementId, elementDescriptionId, targetObjectId, labelId }: DiagramElementPaletteProps) => {
    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const { isOpened, x, y, hideDiagramElementPalette } = useDiagramElementPalette();
    const { setCurrentlyEditedLabelId, currentlyEditedLabelId } = useDiagramDirectEdit();

    //If the Palette search field has the focus on, the useKeyPress from reactflow ignore the key pressed event.
    const onClose = () => {
      hideDiagramElementPalette();

      // Focus the diagram
      store.getState().domNode?.focus();
    };

    const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

    const onKeyDown = useCallback(
      (event: React.KeyboardEvent<Element>) => {
        const { key } = event;
        if (isOpened && key === 'Escape') {
          // Stop propagating the event in order to keep the node/edge selected
          event.stopPropagation();

          hideDiagramElementPalette();

          // Focus the diagram
          store.getState().domNode?.focus();
        }
      },
      [hideDiagramElementPalette, isOpened]
    );

    const handleDirectEditClick = () => {
      if (labelId) {
        setCurrentlyEditedLabelId('palette', labelId, null);
      }
    };

    const extensionSections = useMemo(() => {
      const sectionComponents: React.ReactElement<PaletteExtensionSectionProps>[] = [];
      sectionComponents.push(
        <PaletteExtensionSection
          component={PaletteAppearanceSection}
          title="Appearance"
          id="appearance"
          onClose={() => {}}
        />
      );
      sectionComponents.push(
        <PaletteExtensionSection component={ShowInSection} title="Show in" id="show_in" onClose={onClose} />
      );

      return sectionComponents;
    }, []);

    if (readOnly) {
      return null;
    }

    return isOpened && x && y && !currentlyEditedLabelId ? (
      <PalettePortal>
        <div onKeyDown={onKeyDown}>
          <Palette
            x={x}
            y={y}
            diagramElementId={diagramElementId}
            elementDescriptionId={elementDescriptionId}
            targetObjectId={targetObjectId}
            onDirectEditClick={handleDirectEditClick}
            onClose={onClose}>
            {extensionSections}
          </Palette>
        </div>
      </PalettePortal>
    ) : null;
  }
);
