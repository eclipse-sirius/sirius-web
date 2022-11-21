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
import { flexboxContainerFields, widgetFields } from '@eclipse-sirius/sirius-components-forms';

export const formDescriptionEditorEventSubscription = gql`
  ${widgetFields}
  ${flexboxContainerFields}
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
          toolbarActions {
            ...commonFields
            ...toolbarActionFields
          }
          widgets {
            ...widgetFields
            ... on FlexboxContainer {
              ...flexboxContainerFields
            }
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

export const addToolbarActionMutation = gql`
  mutation addToolbarAction($input: AddToolbarActionInput!) {
    addToolbarAction(input: $input) {
      __typename
      ... on AddToolbarActionSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const deleteToolbarActionMutation = gql`
  mutation deleteToolbarAction($input: DeleteToolbarActionInput!) {
    deleteToolbarAction(input: $input) {
      __typename
      ... on DeleteToolbarActionSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const moveToolbarActionMutation = gql`
  mutation moveToolbarAction($input: MoveToolbarActionInput!) {
    moveToolbarAction(input: $input) {
      __typename
      ... on MoveToolbarActionSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;
