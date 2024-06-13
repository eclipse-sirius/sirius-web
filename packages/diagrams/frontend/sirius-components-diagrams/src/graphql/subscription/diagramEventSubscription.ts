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

import { diagramFragment } from './diagramFragment';
import { GraphQLNodeStyleFragment } from './nodeFragment.types';

export const diagramEventSubscription = (contributions: GraphQLNodeStyleFragment[]) => `
subscription diagramEvent($input: DiagramEventInput!) {
  diagramEvent(input: $input) {
    ... on ErrorPayload {
      id
      message
    }
    ... on DiagramRefreshedEventPayload {
      id
      diagram {
        ...diagramFragment
      }
      cause
      referencePosition {
        parentId
        position { x y }
      }
    }
  }
}

${diagramFragment(contributions)}
`;
