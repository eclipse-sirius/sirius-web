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
import { gql, useSubscription } from '@apollo/client';
import { useEffect, useState } from 'react';
import {
  GQLPortalEventPayload,
  GQLPortalEventSubscription,
  GQLPortalEventVariables,
  GQLPortalRefreshedEventPayload,
  PortalSubscriptionState,
  UsePortalValue,
} from './usePortal.types';

const portalEventSubscription = gql`
  subscription portalEvent($input: PortalEventInput!) {
    portalEvent(input: $input) {
      __typename
      ... on PortalRefreshedEventPayload {
        id
        portal {
          id
          targetObjectId
          views {
            id
            representationMetadata {
              id
              kind
              label
            }
          }
          layoutData {
            portalViewId
            x
            y
            width
            height
          }
        }
      }
    }
  }
`;

const isPortalRefreshedEventPayload = (payload: GQLPortalEventPayload): payload is GQLPortalRefreshedEventPayload =>
  payload.__typename === 'PortalRefreshedEventPayload';

export const usePortal = (editingContextId: string, representationId: string): UsePortalValue => {
  const [state, setState] = useState<PortalSubscriptionState>({
    subscriptionId: crypto.randomUUID(),
    portal: null,
    complete: false,
    message: null,
  });

  const variables: GQLPortalEventVariables = {
    input: {
      id: state.subscriptionId,
      editingContextId,
      portalId: representationId,
    },
  };

  const { error } = useSubscription<GQLPortalEventSubscription, GQLPortalEventVariables>(portalEventSubscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData: ({ data }) => {
      if (data.data) {
        const { portalEvent } = data.data;
        if (isPortalRefreshedEventPayload(portalEvent)) {
          setState((prevState) => ({ ...prevState, portal: portalEvent.portal }));
        }
      }
    },
    onComplete: () => {
      setState((prevState) => ({ ...prevState, portal: null, complete: true }));
    },
  });

  useEffect(() => {
    if (error) {
      setState((prevState) => ({ ...prevState, message: error.message }));
    }
  }, [error]);

  return { portal: state.portal, complete: state.complete, message: state.message };
};
