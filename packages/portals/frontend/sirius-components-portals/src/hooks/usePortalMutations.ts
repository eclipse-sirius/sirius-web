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
import {
  GQLAddPortalViewMutationData,
  GQLAddPortalViewMutationVariables,
  GQLLayoutPortalLayoutData,
  GQLLayoutPortalMutationVariables,
  GQLLayoutPortalViewMutationData,
  GQLLayoutPortalViewMutationVariables,
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

export const usePortalMutations = (editingContextId: string, portalId: string): UsePortalMutationsValue => {
  const [rawAddPortalView] = useMutation<GQLAddPortalViewMutationData, GQLAddPortalViewMutationVariables>(
    addPortalViewMutation
  );

  const addPortalView = (viewRepresentationId: string) => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      viewRepresentationId,
    };
    rawAddPortalView({ variables: { input } });
  };

  const [rawRemovePortalView] = useMutation<GQLRemovePortalViewMutationData, GQLRemovePortalViewMutationVariables>(
    removePortalViewMutation
  );

  const removePortalView = (portalViewId: string) => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      portalViewId,
    };
    rawRemovePortalView({ variables: { input } });
  };

  const [rawLayoutPortal] = useMutation<GQLLayoutPortalViewMutationData, GQLLayoutPortalViewMutationVariables>(
    layoutPortalMutation
  );

  const layoutPortal = (layoutData: GQLLayoutPortalLayoutData[]) => {
    const input: GQLLayoutPortalMutationVariables = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: portalId,
      layoutData,
    };
    rawLayoutPortal({ variables: { input } });
  };

  return {
    addPortalView,
    removePortalView,
    layoutPortal,
  };
};
