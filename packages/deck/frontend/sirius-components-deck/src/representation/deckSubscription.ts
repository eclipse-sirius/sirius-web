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
import { gql } from '@apollo/client';

export const deckEventSubscription = gql`
  subscription deckEvent($input: DeckEventInput!) {
    deckEvent(input: $input) {
      __typename
      ... on ErrorPayload {
        id
        messages {
          body
          level
        }
      }
      ... on DeckRefreshedEventPayload {
        id
        deck {
          id
          metadata {
            kind
            label
          }
          targetObjectId
          lanes {
            id
            title
            label
            targetObjectId
            cards {
              id
              targetObjectId
              targetObjectKind
              targetObjectLabel
              title
              label
              description
            }
          }
        }
      }
    }
  }
`;
