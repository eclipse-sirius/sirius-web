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

    diagram.nodes.forEach((node) => {
      const {
        id,
        height,
        width,
        position: { x, y },
      } = node;
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
        });
      }
    });
    return {
      nodeLayoutData,
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
