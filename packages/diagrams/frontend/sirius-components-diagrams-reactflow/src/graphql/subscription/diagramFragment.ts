/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { edgeFragment } from './edgeFragment';
import { insideLabelFragment, labelFragment } from './labelFragment';
import { nodeFragment } from './nodeFragment';
import { GraphQLNodeStyleFragment } from './nodeFragment.types';

export const diagramFragment = (contributions: GraphQLNodeStyleFragment[]) => `
fragment diagramFragment on Diagram {
  id
  targetObjectId
  metadata {
    kind
    label
  }
  nodes {
    ...nodeFragment
    borderNodes {
      ...nodeFragment
    }
    childNodes {
      ...nodeFragment
      borderNodes {
        ...nodeFragment
      }
      childNodes {
        ...nodeFragment
        borderNodes {
          ...nodeFragment
        }
        childNodes {
          ...nodeFragment
          borderNodes {
            ...nodeFragment
          }
          childNodes {
            ...nodeFragment
            borderNodes {
              ...nodeFragment
            }
            childNodes {
              ...nodeFragment
              borderNodes {
                ...nodeFragment
              }
            }
          }
        }
      }
    }
  }
  edges {
    ...edgeFragment
  }
}

${nodeFragment(contributions)}
${edgeFragment}
${labelFragment}
${insideLabelFragment}
`;
