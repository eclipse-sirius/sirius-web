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

/** @jsx svg */
/** @jsxRuntime classic */
import { svg } from 'sprotty';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

import { Diagram, Node } from '../Diagram.types';

export const debugInfos = (node: Node, size: { width: any; height: any }) => {
  const diagram: Diagram = node.root as Diagram;
  if (diagram.debug && node.selected) {
    const fontSize = '6px';
    const fill = '#455a64';
    return (
      <g>
        <text x={0} y={-3} style={{ fontSize, fill }}>
          x: {node.bounds.x.toFixed(2)}, y: {node.bounds.y.toFixed(2)}
        </text>
        <text x={size.width / 2} y={size.height + 8} style={{ fontSize: '6px', fill, textAnchor: 'middle' }}>
          Width: {size.width.toFixed(2)}
        </text>
        <text
          x={-3}
          y={size.height / 2}
          style={{ fontSize: '6px', fill, alignmentBaseline: 'middle', textAnchor: 'end' }}>
          Height: {size.height.toFixed(2)}
        </text>
        <text x={size.width + 3} y={size.height / 2} style={{ fontSize: '6px', fill, alignmentBaseline: 'middle' }}>
          <tspan x={size.width + 3} dy="-1.1em">
            node id: {node.id}
          </tspan>
          <tspan x={size.width + 3} dy="1.1em">
            node description id: {node.descriptionId}
          </tspan>
          <tspan x={size.width + 3} dy="1.1em">
            target object id: {node.targetObjectId}
          </tspan>
          <tspan x={size.width + 3} dy="1.1em">
            target object kind: {node.targetObjectKind}
          </tspan>
        </text>
      </g>
    );
  }
  return null;
};
