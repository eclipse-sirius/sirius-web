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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { RawDiagram } from './layout.types';
import {
  GQLDiagramLayoutData,
  GQLErrorPayload,
  GQLLayoutDiagramData,
  GQLLayoutDiagramInput,
  GQLLayoutDiagramPayload,
  GQLLayoutDiagramVariables,
  GQLNodeLayoutData,
  GQLSuccessPayload,
  UseSynchronizeLayoutDataValue,
  GQLLabelLayoutData,
} from './useSynchronizeLayoutData.types';
import { EdgeLabel } from '../DiagramRenderer.types';

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
    const labelLayoutData: GQLLabelLayoutData[] = [];

    diagram.nodes.forEach((node) => {
      const {
        id,
        height,
        width,
        position: { x, y },
      } = node;
      const { resizedByUser } = node.data;
      if (height && width) {
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
        });
      }
      if (edge.data && 'beginLabel' in edge.data) {
        const beginLabel = edge.data?.beginLabel as EdgeLabel;
        if (beginLabel) {
          labelLayoutData.push({
            id: beginLabel.id,
            position: {
              x: beginLabel.position.x,
              y: beginLabel.position.y,
            },
          });
        }
      }
      if (edge.data && 'endLabel' in edge.data) {
        const endLabel = edge.data?.endLabel as EdgeLabel;
        if (endLabel) {
          labelLayoutData.push({
            id: endLabel.id,
            position: {
              x: endLabel.position.x,
              y: endLabel.position.y,
            },
          });
        }
      }
    });
    return {
      nodeLayoutData,
      labelLayoutData,
    };
  };

  const synchronizeLayoutData = (id: string, diagram: RawDiagram) => {
    const diagramLayoutData = toDiagramLayoutData(diagram);
    const input: GQLLayoutDiagramInput = {
      id,
      editingContextId,
      representationId,
      diagramLayoutData,
    };

    const variables: GQLLayoutDiagramVariables = { input };
    layoutDiagram({ variables });
  };

  return {
    synchronizeLayoutData,
  };
};
