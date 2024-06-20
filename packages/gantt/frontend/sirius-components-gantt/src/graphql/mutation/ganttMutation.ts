/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export const deleteTaskMutation = gql`
  mutation deleteGanttTask($input: DeleteGanttTaskInput!) {
    deleteGanttTask(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const editTaskMutation = gql`
  mutation editGanttTask($input: EditGanttTaskInput!) {
    editGanttTask(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const createTaskMutation = gql`
  mutation createGanttTask($input: CreateGanttTaskInput!) {
    createGanttTask(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const dropTaskMutation = gql`
  mutation dropGanttTask($input: DropGanttTaskInput!) {
    dropGanttTask(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const createTaskDependencyMutation = gql`
  mutation createGanttTaskDependency($input: CreateGanttTaskDependencyInput!) {
    createGanttTaskDependency(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const deleteTaskDependencyMutation = gql`
  mutation deleteTaskDependency($input: DeleteGanttTaskDependencyInput!) {
    deleteGanttTaskDependency(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const changeTaskCollapseStateMutation = gql`
  mutation changeGanttTaskCollapseState($input: ChangeGanttTaskCollapseStateInput!) {
    changeGanttTaskCollapseState(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const changeColumnMutation = gql`
  mutation changeGanttColumn($input: ChangeGanttColumnInput!) {
    changeGanttColumn(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;
