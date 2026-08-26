/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

const MAX_NODE_HIERARCHY_DEPTH = 13;

const nodeHierarchyFragments = Array.from({ length: MAX_NODE_HIERARCHY_DEPTH + 1 }, (_, depth): string => {
  const fragmentName = `nodeHierarchyFragment${depth}`;

  if (depth === 0) {
    return `
fragment ${fragmentName} on Node {
  ...nodeFragment
}`;
  }

  const descendantFragmentName = `nodeHierarchyFragment${depth - 1}`;
  return `
fragment ${fragmentName} on Node {
  ...nodeFragment
  childNodes {
    ...${descendantFragmentName}
  }
  borderNodes {
    ...${descendantFragmentName}
  }
}`;
}).join('\n');

export const diagramFragment = `
fragment diagramFragment on Diagram {
  id
  targetObjectId
  metadata {
    label
    kind
  }
  style {
    background
  }
  layoutData {
    nodeLayoutData {
      id
      position { x y }
      size { width height }
      resizedByUser
      movedByUser
      handleLayoutData { 
        edgeId
        position { x y }
        handlePosition
        type
      }
      minComputedSize { width height }
    }
    edgeLayoutData {
      id
      bendingPoints { x y }
      relativePositionBendingPoints { x y }
      edgeAnchorLayoutData { 
        edgeId
        positionRatio
        handlePosition
        type
      }
    }
    labelLayoutData {
      id
      position { x y }
      size { width height }
      resizedByUser
      movedByUser
    }
    autoLaidOut
  }
  nodes {
    ...nodeHierarchyFragment${MAX_NODE_HIERARCHY_DEPTH}
  }
  edges {
    ...edgeFragment
  }
}

${nodeHierarchyFragments}
${nodeFragment}
${edgeFragment}
${labelFragment}
${insideLabelFragment}
${outsideLabelFragment}
`;
