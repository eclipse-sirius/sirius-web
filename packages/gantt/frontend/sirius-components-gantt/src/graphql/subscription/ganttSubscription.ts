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

export const ganttEventSubscription = gql`
  subscription ganttEvent($input: GanttEventInput!) {
    ganttEvent(input: $input) {
      __typename
      ... on ErrorPayload {
        id
        messages {
          body
          level
        }
      }
      ... on GanttRefreshedEventPayload {
        id
        gantt {
          id
          metadata {
            kind
            label
          }
          targetObjectId
          tasks {
            ...taskFields
            subTasks {
              ...taskFields
              subTasks {
                ...taskFields
                subTasks {
                  ...taskFields
                }
              }
            }
          }
          columns {
            id
            displayed
            width
          }
        }
      }
    }
  }

  fragment taskFields on Task {
    id
    targetObjectId
    targetObjectKind
    targetObjectLabel
    detail {
      name
      description
      startTime
      endTime
      progress
      computeStartEndDynamically
      collapsed
    }
    style {
      labelColor
      backgroundColor
      progressColor
    }
    taskDependencyIds
  }
`;
