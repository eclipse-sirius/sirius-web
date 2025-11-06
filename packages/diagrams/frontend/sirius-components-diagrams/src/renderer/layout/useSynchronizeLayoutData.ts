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
import { Node, Position } from '@xyflow/react';
import { useContext, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeLabel, NodeData } from '../DiagramRenderer.types';
import { EdgeAnchorNodeData, isEdgeAnchorNode } from '../node/EdgeAnchorNode.types';
import { RawDiagram } from './layout.types';
import {
  GQLDiagramLayoutData,
  GQLEdgeLayoutData,
  GQLErrorPayload,
  GQLHandleLayoutData,
  GQLLabelLayoutData,
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
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'useSynchronizeLayoutData' });
  const { diagramId: representationId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { addErrorMessage, addMessages } = useMultiToast();
  const [layoutDiagram, { data, error }] = useMutation<GQLLayoutDiagramData, GQLLayoutDiagramVariables>(
    layoutDiagramMutation
  );
  useEffect(() => {
    if (error) {
      addErrorMessage(t('errors.unexpected'));
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
    const nodeId2node = new Map<string, Node<NodeData | EdgeAnchorNodeData>>();
    diagram.nodes.forEach((node) => nodeId2node.set(node.id, node));
    const nodeLayoutData: GQLNodeLayoutData[] = [];
    const edgeLayoutData: GQLEdgeLayoutData[] = [];
    const labelLayoutData: GQLLabelLayoutData[] = [];

    diagram.nodes.forEach((node) => {
      const {
        id,
        height,
        width,
        position: { x, y },
      } = node;
      const { resizedByUser, movedByUser, minComputedHeight, minComputedWidth } = node.data;
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
          movedByUser,
          handleLayoutData: handleLayoutDatas,
          minComputedSize: {
            height: minComputedHeight ?? 0,
            width: minComputedWidth ?? 0,
          },
        });
      }
      const outsideLabelPosition = Object.values(node.data.outsideLabels)[0];
      if (outsideLabelPosition) {
        labelLayoutData.push({
          id: outsideLabelPosition.id,
          position: {
            x: outsideLabelPosition.position.x,
            y: outsideLabelPosition.position.y,
          },
          size: {
            width: outsideLabelPosition.width,
            height: outsideLabelPosition.height,
          },
          resizedByUser: outsideLabelPosition.resizedByUser,
        });
      }
    });

    diagram.edges.forEach((edge) => {
      const centerLabel = edge.data?.label;
      if (centerLabel) {
        labelLayoutData.push({
          id: centerLabel.id,
          position: {
            x: centerLabel.position.x,
            y: centerLabel.position.y,
          },
          size: {
            width: centerLabel.width,
            height: centerLabel.height,
          },
          resizedByUser: centerLabel.resizedByUser,
        });
      }
      if (edge.data && 'beginLabel' in edge.data) {
        const beginLabel = edge.data.beginLabel as EdgeLabel;
        if (beginLabel) {
          labelLayoutData.push({
            id: beginLabel.id,
            position: {
              x: beginLabel.position.x,
              y: beginLabel.position.y,
            },
            size: {
              width: beginLabel.width,
              height: beginLabel.height,
            },
            resizedByUser: beginLabel.resizedByUser,
          });
        }
      }
      if (edge.data && 'endLabel' in edge.data) {
        const endLabel = edge.data.endLabel as EdgeLabel;
        if (endLabel) {
          labelLayoutData.push({
            id: endLabel.id,
            position: {
              x: endLabel.position.x,
              y: endLabel.position.y,
            },
            size: {
              width: endLabel.width,
              height: endLabel.height,
            },
            resizedByUser: endLabel.resizedByUser,
          });
        }
      }
    });

    diagram.edges.forEach((edge) => {
      let edgeLayout: GQLEdgeLayoutData = {
        id: edge.id,
        bendingPoints: [],
        edgeAnchorLayoutData: [],
      };

      if (edge.data && edge.data.bendingPoints) {
        edge.data.bendingPoints.forEach((point) => {
          edgeLayout.bendingPoints.push({ x: point.x, y: point.y });
        });
      }

      const edgeAnchorNode = nodeId2node.get(edge.id);
      const isLayoutedEdgeAnchorNode =
        edgeAnchorNode && isEdgeAnchorNode(edgeAnchorNode) && edgeAnchorNode.data.isLayouted;

      if (isLayoutedEdgeAnchorNode) {
        edgeLayout.edgeAnchorLayoutData.push({
          edgeId: edgeAnchorNode.data.edgeId,
          positionRatio: edgeAnchorNode.data.positionRatio,
          handlePosition: Position.Left,
          type: edgeAnchorNode.id === edge.source ? 'target' : 'source',
        });
      }

      edgeLayoutData.push(edgeLayout);
    });
    return {
      nodeLayoutData,
      edgeLayoutData,
      labelLayoutData,
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
