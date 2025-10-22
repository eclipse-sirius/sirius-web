/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useCallback, useContext, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLErrorPayload,
  GQLPinDiagramElementData,
  GQLPinDiagramElementInput,
  GQLPinDiagramElementPayload,
  GQLPinDiagramElementVariables,
  UsePinDiagramElements,
} from './usePinDiagramElements.types';

const pinDiagramElementMutation = gql`
  mutation pinDiagramElement($input: PinDiagramElementInput!) {
    pinDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
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

const isErrorPayload = (payload: GQLPinDiagramElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const usePinDiagramElements = (): UsePinDiagramElements => {
  const { t: coreT } = useTranslation('siriusComponentsCore');
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const [pinElementMutation, { data: pinDiagramElementData, error: pinDiagramElementError }] = useMutation<
    GQLPinDiagramElementData,
    GQLPinDiagramElementVariables
  >(pinDiagramElementMutation);

  useEffect(() => {
    if (pinDiagramElementError) {
      addErrorMessage(coreT('errors.unexpected'));
    }
    if (pinDiagramElementData) {
      const { pinDiagramElement } = pinDiagramElementData;
      if (isErrorPayload(pinDiagramElement)) {
        addErrorMessage(pinDiagramElement.message);
      }
    }
  }, [coreT, pinDiagramElementData, pinDiagramElementError]);

  const pinDiagramElements = useCallback(
    (nodeId: string[], pinned: boolean) => {
      const input: GQLPinDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds: nodeId,
        pinned,
      };
      if (!readOnly) {
        pinElementMutation({ variables: { input } });
      }
    },
    [editingContextId, diagramId, readOnly, pinElementMutation]
  );

  return { pinDiagramElements };
};
