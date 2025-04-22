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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { RawDiagram } from './layout.types';
import {
  GQLDiagramLayoutData,
  GQLEdgeLayoutData,
  GQLErrorPayload,
  GQLHandleLayoutData,
  GQLLayoutDiagramData,
  GQLLayoutDiagramInput,
  GQLLayoutDiagramPayload,
  GQLLayoutDiagramVariables,
  GQLNodeLayoutData,
  GQLSuccessPayload,
  UseSynchronizeLayoutDataValue,
} from './useSynchronizeLayoutData.types';

const layoutDiagramMutation = gql`
  mutation layoutDiagram($input: LayoutDiagramInput!) {
    layoutDiagram(input: $input) {
      __typename
      ... on SuccessPayload {
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

const isErrorPayload = (payload: GQLLayoutDiagramPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLLayoutDiagramPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useSynchronizeLayoutData = (): UseSynchronizeLayoutDataValue => {
  const { diagramId: representationId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { addErrorMessage, addMessages } = useMultiToast();
  const [layoutDiagram, { data, error }] = useMutation<GQLLayoutDiagramData, GQLLayoutDiagramVariables>(
    layoutDiagramMutation
  );
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { layoutDiagram } = data;
      if (isSuccessPayload(layoutDiagram)) {
        addMessages(layoutDiagram.messages);
      }
      if (isErrorPayload(layoutDiagram)) {
        addMessages(layoutDiagram.messages);
      }
    }
  }, [data, error]);

  const toDiagramLayoutData = (diagram: RawDiagram): GQLDiagramLayoutData => {
    const nodeLayoutData: GQLNodeLayoutData[] = [];
    const edgeLayoutData: GQLEdgeLayoutData[] = [];

    diagram.nodes.forEach((node) => {
      const {
        id,
        height,
        width,
        position: { x, y },
      } = node;
      const { resizedByUser } = node.data;
      if (height && width) {
        const handleLayoutDatas: GQLHandleLayoutData[] = [];
        node.data.connectionHandles.forEach((handle) => {
          if (handle.XYPosition && (handle.XYPosition.x || handle.XYPosition.y)) {
            handleLayoutDatas.push({
              edgeId: handle.edgeId,
              position: { x: handle.XYPosition.x, y: handle.XYPosition.y },
              handlePosition: handle.position,
              type: handle.type,
            });
          } else if (handle.isFixedHandlePosition) {
            handleLayoutDatas.push({
              edgeId: handle.edgeId,
              position: { x: 0, y: 0 },
              handlePosition: handle.position,
              type: handle.type,
            });
          }
        });

        nodeLayoutData.push({
          id,
          position: {
            x,
            y,
          },
          size: {
            height,
            width,
          },
          resizedByUser,
          handleLayoutData: handleLayoutDatas,
        });
      }
    });

    diagram.edges.forEach((edge) => {
      if (edge.data?.bendingPoints) {
        edgeLayoutData.push({
          id: edge.id,
          bendingPoints: edge.data.bendingPoints.map((point) => {
            return { x: point.x, y: point.y };
          }),
        });
      }
    });
    return {
      nodeLayoutData,
      edgeLayoutData,
    };
  };

  const synchronizeLayoutData = (id: string, cause: string, diagram: RawDiagram) => {
    const diagramLayoutData = toDiagramLayoutData(diagram);

    const input: GQLLayoutDiagramInput = {
      id,
      editingContextId,
      representationId,
      cause,
      diagramLayoutData,
    };

    const variables: GQLLayoutDiagramVariables = { input };
    layoutDiagram({ variables });
  };

  return {
    synchronizeLayoutData,
  };
};
