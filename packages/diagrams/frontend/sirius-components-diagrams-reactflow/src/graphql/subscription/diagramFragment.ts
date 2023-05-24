/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { labelFragment } from './labelFragment';
import { nodeFragment } from './nodeFragment';

export const diagramFragment = `
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

${nodeFragment}
${edgeFragment}
${labelFragment}
`;
