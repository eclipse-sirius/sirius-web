/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

export const ViewerProjectsFragment = gql`
  fragment ViewerProjects on Viewer {
    projects(after: $after, before: $before, first: $first, last: $last, filter: $filter) {
      edges {
        node {
          ...Project
        }
        cursor
      }
      pageInfo {
        hasNextPage
        hasPreviousPage
        startCursor
        endCursor
        count
      }
    }
  }
`;

export const ProjectFragment = gql`
  fragment Project on Project {
    id
    name
  }
`;
