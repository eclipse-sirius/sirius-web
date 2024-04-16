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
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import {
  GQLAddPortalViewMutationData,
  GQLAddPortalViewMutationVariables,
  GQLLayoutPortalInput,
  GQLLayoutPortalLayoutData,
  GQLLayoutPortalMutationData,
  GQLLayoutPortalMutationVariables,
  GQLRemovePortalViewMutationData,
  GQLRemovePortalViewMutationVariables,
  UsePortalMutationsValue,
} from './usePortalMutations.types';

const addPortalViewMutation = gql`
  mutation addPortalView($input: AddPortalViewInput!) {
    addPortalView(input: $input) {
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

const removePortalViewMutation = gql`
  mutation removePortalView($input: RemovePortalViewInput!) {
    removePortalView(input: $input) {
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

const layoutPortalMutation = gql`
  mutation layoutPortal($input: LayoutPortalInput!) {
    layoutPortal(input: $input) {
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

  useReporting(rawAddPortalViewResult, (data: GQLAddPortalViewMutationData) => data.addPortalView);

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

  useReporting(rawRemovePortalViewResult, (data: GQLRemovePortalViewMutationData) => data.removePortalView);

  const [layoutsInProgress, setLayoutsInProgress] = useState<number>(0);

  const [rawLayoutPortal, rawLayoutPortalResult] = useMutation<
    GQLLayoutPortalMutationData,
    GQLLayoutPortalMutationVariables
  >(layoutPortalMutation);

  const layoutPortal = (layoutData: GQLLayoutPortalLayoutData[]) => {
    const input: GQLLayoutPortalInput = {
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

  useReporting(rawLayoutPortalResult, (data: GQLLayoutPortalMutationData) => data.layoutPortal);

  return {
    addPortalView,
    removePortalView,
    layoutPortal,
    layoutInProgress: layoutsInProgress > 0,
  };
};
