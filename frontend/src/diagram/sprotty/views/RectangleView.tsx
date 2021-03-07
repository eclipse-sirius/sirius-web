/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { svg } from 'snabbdom-jsx';
import { RectangularNodeView } from 'sprotty';

/**
 * The view used to display nodes with a rectangle style.
 *
 * @sbegaudeau
 */
export class RectangleView extends RectangularNodeView {
  /**
   * Renders the given node in the context.
   * @param node The node
   * @param context The context
   */
  // @ts-ignore
  render(node, context) {
    const nodeStyle = node.style;
    const styleObject = {
      fill: nodeStyle.color,
      stroke: nodeStyle.borderColor,
      'stroke-width': nodeStyle.borderSize,
    };

    if (node.selected) {
      styleObject['outline'] = 'var(--red) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--red) solid 2px';
    }

    switch (nodeStyle.borderStyle) {
      case 'Dash':
        styleObject['stroke-dasharray'] = '4,4';
        break;
      case 'Dot':
        styleObject['stroke-dasharray'] = '2,2';
        break;
      case 'Dash_Dot':
        styleObject['stroke-dasharray'] = '2,4,2';
        break;
      default:
        break;
    }

    return (
      <g attrs-data-testid={`Rectangle - ${node.children[0]?.text}`} attrs-data-testselected={`${node.selected}`}>
        <rect
          class-selected={node.selected}
          class-mouseover={node.hoverFeedback}
          x={0}
          y={0}
          width={Math.max(0, node.bounds.width)}
          height={Math.max(0, node.bounds.height)}
          style={styleObject}
        />
        {context.renderChildren(node)}
      </g>
    );
  }
}
