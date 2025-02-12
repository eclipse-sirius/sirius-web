/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export const ViewerLibrariesFragment = gql`
  fragment ViewerLibraries on Viewer {
    libraries(page: $page, limit: $limit) {
      edges {
        node {
          ...Library
        }
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

export const LibraryFragment = gql`
  fragment Library on Library {
    id
    namespace
    name
    version
    description
    createdOn
  }
`;
