/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { DRAG_SOURCES_TYPE, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect } from 'react';
import { Viewport, XYPosition, useStoreApi, useViewport } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLDropOnDiagramData,
  GQLDropOnDiagramInput,
  GQLDropOnDiagramPayload,
  GQLDropOnDiagramSuccessPayload,
  GQLDropOnDiagramVariables,
  GQLErrorPayload,
  UseDropValue,
} from './useDrop.types';

const dropOnDiagramMutation = gql`
  mutation dropOnDiagram($input: DropOnDiagramInput!) {
    dropOnDiagram(input: $input) {
      __typename
      ... on DropOnDiagramSuccessPayload {
        diagram {
          id
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const computeDropPosition = (
  event: MouseEvent | React.MouseEvent,
  { x: viewportX, y: viewportY, zoom: viewportZoom }: Viewport,
  bounds?: DOMRect
): XYPosition => {
  return {
    x: (event.clientX - (bounds?.left ?? 0) - viewportX) / viewportZoom,
    y: (event.clientY - (bounds?.top ?? 0) - viewportY) / viewportZoom,
  };
};

const isErrorPayload = (payload: GQLDropOnDiagramPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDropOnDiagramPayload): payload is GQLDropOnDiagramSuccessPayload =>
  payload.__typename === 'DropOnDiagramSuccessPayload';

export const useDrop = (): UseDropValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const [dropMutation, { data: droponDiagramElementData, error: droponDiagramError }] = useMutation<
    GQLDropOnDiagramData,
    GQLDropOnDiagramVariables
  >(dropOnDiagramMutation);
  const viewport = useViewport();
  const { domNode } = useStoreApi().getState();
  const element = domNode?.getBoundingClientRect();

  useEffect(() => {
    if (droponDiagramError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (droponDiagramElementData) {
      const { dropOnDiagram } = droponDiagramElementData;
      if (isSuccessPayload(dropOnDiagram)) {
        addMessages(dropOnDiagram.messages);
      }
      if (isErrorPayload(dropOnDiagram)) {
        addMessages(dropOnDiagram.messages);
      }
    }
  }, [droponDiagramElementData, droponDiagramError]);

  const onDrop = useCallback(
    (event: React.DragEvent, diagramElementId?: string) => {
      event.preventDefault();
      event.stopPropagation();

      const data = event.dataTransfer.getData(DRAG_SOURCES_TYPE);

      // check if the dropped element is valid
      if (data === '') {
        return;
      }

      const dropPosition = computeDropPosition(event, viewport, element);
      const selectedIds = JSON.parse(data).map((entry) => entry.id);

      const input: GQLDropOnDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        objectIds: selectedIds,
        startingPositionX: dropPosition.x,
        startingPositionY: dropPosition.y,
        diagramTargetElementId: diagramElementId ? diagramElementId : diagramId,
      };
      dropMutation({ variables: { input } });
    },
    [element?.top, element?.left, viewport]
  );

  const onDragOver = (event: React.DragEvent) => {
    event.preventDefault();
  };

  return { onDrop, onDragOver };
};
