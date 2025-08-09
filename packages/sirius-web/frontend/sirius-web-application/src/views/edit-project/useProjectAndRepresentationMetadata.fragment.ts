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

export const ProjectAndRepresentationFragment = gql`
  fragment ProjectAndRepresentationFragment on Project {
    id
    name
    natures {
      name
    }
    capabilities {
      canDownload
      canRename
      canDelete
      canEdit
      canDuplicate
      settings {
        canView
      }
    }
    currentEditingContext(name: $name) {
      id
      representation(representationId: $representationId) @include(if: $includeRepresentation) {
        id
        label
        kind
        iconURLs
        description {
          id
        }
      }
    }
  }
`;
