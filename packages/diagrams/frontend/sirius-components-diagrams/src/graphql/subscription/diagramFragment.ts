/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { insideLabelFragment, labelFragment, outsideLabelFragment } from './labelFragment';
import { nodeFragment } from './nodeFragment';

export const diagramFragment = `
fragment diagramFragment on Diagram {
  id
  targetObjectId
  metadata {
    label
    kind
  }
  layoutData {
    nodeLayoutData {
      id
      position { x y }
      size { width height }
      resizedByUser
      handleLayoutData { 
        edgeId
        position { x y }
        handlePosition
        type
      }
    }
    edgeLayoutData {
      id
      bendingPoints { x y }
      edgeAnchorLayoutData { 
        edgeId
        positionRatio
        handlePosition
        type
      }
    }
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
${insideLabelFragment}
${outsideLabelFragment}
`;
