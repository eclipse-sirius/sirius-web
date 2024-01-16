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
import { MutationResult, gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { GQLErrorPayload } from './usePortal.types';
import {
  GQLAddPortalViewMutationData,
  GQLAddPortalViewMutationVariables,
  GQLLayoutPortalLayoutData,
  GQLLayoutPortalMutationVariables,
  GQLLayoutPortalViewMutationData,
  GQLLayoutPortalViewMutationVariables,
  GQLPayload,
  GQLRemovePortalViewMutationData,
  GQLRemovePortalViewMutationVariables,
  UsePortalMutationsValue,
} from './usePortalMutations.types';

const addPortalViewMutation = gql`
  mutation addPortalView($input: AddPortalViewInput!) {
    addPortalView(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const removePortalViewMutation = gql`
  mutation removePortalView($input: RemovePortalViewInput!) {
    removePortalView(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const layoutPortalMutation = gql`
  mutation layoutPortal($input: LayoutPortalInput!) {
    layoutPortal(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

function useErrorReporting<T>(result: MutationResult<T>, extractPayload: (data: T | null) => GQLPayload | undefined) {
  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    const { loading, data, error } = result;
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      const payload: GQLPayload | undefined = extractPayload(data || null);
      if (payload && isErrorPayload(payload)) {
        const { message } = payload;
        addErrorMessage(message);
      }
    }
  }, [result.loading, result.data, result.error]);
}

const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const usePortalMutations = (editingContextId: string, portalId: string): UsePortalMutationsValue => {
  const [rawAddPortalView, rawAddPortalViewResult] = useMutation<
    GQLAddPortalViewMutationData,
    GQLAddPortalViewMutationVariables
  >(addPortalViewMutation);

  const addPortalView = (viewRepresentationId: string, x: number, y: number, width: number, height: number) => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      viewRepresentationId,
      x,
      y,
      width,
      height,
    };
    rawAddPortalView({ variables: { input } });
  };

  useErrorReporting(rawAddPortalViewResult, (data) => data?.addPortalView);

  const [rawRemovePortalView, rawRemovePortalViewResult] = useMutation<
    GQLRemovePortalViewMutationData,
    GQLRemovePortalViewMutationVariables
  >(removePortalViewMutation);

  const removePortalView = (portalViewId: string) => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      portalViewId,
    };
    rawRemovePortalView({ variables: { input } });
  };

  useErrorReporting(rawRemovePortalViewResult, (data) => data?.removePortalView);

  const [layoutsInProgress, setLayoutsInProgress] = useState<number>(0);

  const [rawLayoutPortal, rawLayoutPortalResult] = useMutation<
    GQLLayoutPortalViewMutationData,
    GQLLayoutPortalViewMutationVariables
  >(layoutPortalMutation);

  const layoutPortal = (layoutData: GQLLayoutPortalLayoutData[]) => {
    const input: GQLLayoutPortalMutationVariables = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      layoutData,
    };
    setLayoutsInProgress((prevState) => prevState + 1);
    rawLayoutPortal({ variables: { input } });
  };

  useEffect(() => {
    if (!rawLayoutPortalResult.loading) {
      setLayoutsInProgress((prevState) => prevState - 1);
    }
  }, [rawLayoutPortalResult.loading]);

  useErrorReporting(rawLayoutPortalResult, (data) => data?.layoutPortalView);

  return {
    addPortalView,
    removePortalView,
    layoutPortal,
    layoutInProgress: layoutsInProgress > 0,
  };
};
