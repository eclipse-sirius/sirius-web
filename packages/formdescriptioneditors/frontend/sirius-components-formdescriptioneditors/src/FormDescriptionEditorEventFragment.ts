/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { flexboxContainerFields, WidgetContribution, widgetFields } from '@eclipse-sirius/sirius-components-forms';

export const formDescriptionEditorEventSubscription = (contributions: Array<WidgetContribution>) => `
  ${widgetFields(contributions)}
  ${flexboxContainerFields(contributions)}
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
          pages {
            id
            label
            toolbarActions {
              ...commonFields
              ...toolbarActionFields
            }
            groups {
              id
              label
              displayMode
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
              borderStyle {
                color
                lineStyle
                size
                radius
              }
            }
          }
        }
      }
    }
  }
`;

export const addGroupMutation = gql`
  mutation addGroup($input: AddGroupInput!) {
    addGroup(input: $input) {
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

export const deleteGroupMutation = gql`
  mutation deleteGroup($input: DeleteGroupInput!) {
    deleteGroup(input: $input) {
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

export const moveGroupMutation = gql`
  mutation moveGroup($input: MoveGroupInput!) {
    moveGroup(input: $input) {
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

export const addWidgetMutation = gql`
  mutation addWidget($input: AddWidgetInput!) {
    addWidget(input: $input) {
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

export const deleteWidgetMutation = gql`
  mutation deleteWidget($input: DeleteWidgetInput!) {
    deleteWidget(input: $input) {
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

export const moveWidgetMutation = gql`
  mutation moveWidget($input: MoveWidgetInput!) {
    moveWidget(input: $input) {
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

export const addToolbarActionMutation = gql`
  mutation addToolbarAction($input: AddToolbarActionInput!) {
    addToolbarAction(input: $input) {
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

export const deleteToolbarActionMutation = gql`
  mutation deleteToolbarAction($input: DeleteToolbarActionInput!) {
    deleteToolbarAction(input: $input) {
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

export const moveToolbarActionMutation = gql`
  mutation moveToolbarAction($input: MoveToolbarActionInput!) {
    moveToolbarAction(input: $input) {
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

export const addPageMutation = gql`
  mutation addPage($input: AddPageInput!) {
    addPage(input: $input) {
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

export const movePageMutation = gql`
  mutation movePage($input: MovePageInput!) {
    movePage(input: $input) {
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

export const deletePageMutation = gql`
  mutation deletePage($input: DeletePageInput!) {
    deletePage(input: $input) {
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
