/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { MousePosition, useMousePosition, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow, useStoreApi, useViewport, Viewport, XYPosition } from '@xyflow/react';
import { useCallback, useContext, useEffect, useState } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isSingleClickOnDiagramElementTool, isToolSection } from '../palette/Palette';
import { GQLPalette, GQLSingleClickOnDiagramElementTool } from '../palette/Palette.types';
import { usePaletteContents } from '../palette/usePaletteContents';
import { useSingleClickTool } from '../tools/useSingleClickTool';
import { UseDiagramKeyBindingState, UseDiagramKeyBindingValue } from './useDiagramKeyBinding.types';

const getAllPaletteTools = (palette: GQLPalette) => {
  return palette.quickAccessTools
    .filter(isSingleClickOnDiagramElementTool)
    .concat(palette.paletteEntries.filter(isSingleClickOnDiagramElementTool))
    .concat(
      palette.paletteEntries
        .filter(isToolSection)
        .flatMap((section) => section.tools.filter(isSingleClickOnDiagramElementTool))
    );
};

const computeCursorPosition = (
  mousePosition: MousePosition,
  viewport: Viewport,
  bounds: DOMRect | undefined
): XYPosition => {
  const { x: mouseX, y: mouseY } = mousePosition;
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = viewport;
  let x: number = 0;
  let y: number = 0;
  if (viewportZoom !== 0 && mouseX && mouseY) {
    x = (mouseX - (bounds?.left ?? 0) - viewportX) / viewportZoom;
    y = (mouseY - (bounds?.top ?? 0) - viewportY) / viewportZoom;
  }
  return { x, y };
};

const toolHasKeyBinding = (tool: GQLSingleClickOnDiagramElementTool, event: React.KeyboardEvent<Element>) => {
  return tool.keyBindings.some(
    (keyBinding) =>
      keyBinding.key === event.key &&
      keyBinding.isCtrl === event.ctrlKey &&
      keyBinding.isAlt === event.altKey &&
      keyBinding.isMeta === event.metaKey
  );
};

export const useDiagramKeyBinding = (diagramTargetObjectId: string): UseDiagramKeyBindingValue => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { getNodes, getNode, getEdges, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const [state, setState] = useState<UseDiagramKeyBindingState>({
    currentEvent: null,
  });

  const { getMousePosition } = useMousePosition();
  const viewport = useViewport();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { addErrorMessage } = useMultiToast();

  const { invokeSingleClickTool } = useSingleClickTool();

  const diagramElementIds = getNodes()
    .filter((node) => node.selected)
    .map((node) => node.id)
    .concat(
      getEdges()
        .filter((edge) => edge.selected)
        .map((edge) => edge.id)
    );
  const elementIds = diagramElementIds[0] ? diagramElementIds : [diagramId];

  const getTargetObjectId = (selectedDiagramElementIds: string[]) => {
    return (
      getNode(selectedDiagramElementIds[0] || '')?.data.targetObjectId ||
      getEdge(selectedDiagramElementIds[0] || '')?.data?.targetObjectId ||
      diagramTargetObjectId
    );
  };

  const { palette, loading } = usePaletteContents(elementIds, !state.currentEvent);

  useEffect(() => {
    if (!loading && palette && state.currentEvent) {
      const allPaletteTools = getAllPaletteTools(palette);
      const toolToExecute = allPaletteTools.filter((tool) => toolHasKeyBinding(tool, state.currentEvent!));

      const { domNode } = store.getState();
      const domElement = domNode?.getBoundingClientRect();
      const mousePosition = getMousePosition();
      const { x, y } = computeCursorPosition(mousePosition, viewport, domElement);
      const targetObjectId = getTargetObjectId(elementIds);

      if (toolToExecute.length === 1 && toolToExecute[0]) {
        invokeSingleClickTool(editingContextId, diagramId, toolToExecute[0], elementIds, targetObjectId, x, y);
      } else if (toolToExecute.length > 1) {
        const keysPressed =
          (state.currentEvent.ctrlKey ? 'CTRL + ' : '') +
          (state.currentEvent.metaKey ? 'META + ' : '') +
          (state.currentEvent.altKey ? 'ALT + ' : '') +
          state.currentEvent.key;
        const toolLabels = toolToExecute.map((tool) => tool.label).join(', ');
        addErrorMessage(`Found multiple tools associated to ${keysPressed}: ${toolLabels}`);
      }
      // There is no tool associated to the pressed keys, don't do anything.
      // In any case, reset the current event, we don't need it anymore and we don't want to reuse it.
      setState((prevState) => ({
        ...prevState,
        currentEvent: null,
      }));
    }
  }, [palette, loading, state.currentEvent]);

  const onKeyBinding = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key, altKey, ctrlKey, metaKey, shiftKey } = event;
      const isTextField = event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement;
      // If a modifier key is hit alone or if the event comes from a text field, do nothing
      if (
        (altKey && key === 'Alt') ||
        (ctrlKey && key === 'Control') ||
        (metaKey && key === 'Meta') ||
        (shiftKey && key === 'Shift') ||
        isTextField
      ) {
        return;
      }

      setState((prevState) => ({
        ...prevState,
        currentEvent: event,
      }));
    },
    [diagramId]
  );

  return {
    onKeyBinding,
  };
};
