/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

export const formDescriptionEditorEventSubscription = gql`
  subscription formDescriptionEditorEvent($input: FormDescriptionEditorEventInput!) {
    formDescriptionEditorEvent(input: $input) {
      __typename
      ... on ErrorPayload {
        id
        message
      }
      ... on SubscribersUpdatedEventPayload {
        id
        subscribers {
          username
        }
      }
      ... on FormDescriptionEditorRefreshedEventPayload {
        id
        formDescriptionEditor {
          id
          metadata {
            kind
            label
            description {
              id
            }
          }
          widgets {
            id
            label
            kind
          }
        }
      }
    }
  }
`;

export const addWidgetMutation = gql`
  mutation addWidget($input: AddWidgetInput!) {
    addWidget(input: $input) {
      __typename
      ... on AddWidgetSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const deleteWidgetMutation = gql`
  mutation deleteWidget($input: DeleteWidgetInput!) {
    deleteWidget(input: $input) {
      __typename
      ... on DeleteWidgetSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const moveWidgetMutation = gql`
  mutation moveWidget($input: MoveWidgetInput!) {
    moveWidget(input: $input) {
      __typename
      ... on MoveWidgetSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;
