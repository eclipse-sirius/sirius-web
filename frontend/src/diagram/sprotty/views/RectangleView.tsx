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
      styleObject['outline'] = 'var(--blue-lagoon) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 2px';
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
    let selectedHandles = null;
    const size = {
      width: Math.max(0, node.bounds.width),
      height: Math.max(0, node.bounds.height),
    };
    if (node.selected) {
      const selectorSize = 6;
      const delta = selectorSize / 2;
      const north = {
        x: size.width / 2 - delta,
        y: -delta,
      };
      const south = {
        x: size.width / 2 - delta,
        y: size.height - delta,
      };
      const west = {
        x: -delta,
        y: size.height / 2 - delta,
      };
      const east = {
        x: size.width - delta,
        y: size.height / 2 - delta,
      };
      const nw = {
        x: -delta,
        y: -delta,
      };
      const ne = {
        x: size.width - delta,
        y: -delta,
      };
      const se = {
        x: size.width - delta,
        y: size.height - delta,
      };
      const sw = {
        x: -delta,
        y: size.height - delta,
      };
      selectedHandles = (
        <g>
          <rect
            id="selectorGrip_resize_n"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'n-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={north.x}
            y={north.y}></rect>
          <rect
            id="selectorGrip_resize_s"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 's-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={south.x}
            y={south.y}></rect>
          <rect
            id="selectorGrip_resize_w"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'w-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={west.x}
            y={west.y}></rect>
          <rect
            id="selectorGrip_resize_e"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'e-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={east.x}
            y={east.y}></rect>
          <rect
            id="selectorGrip_resize_nw"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'nw-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={nw.x}
            y={nw.y}></rect>
          <rect
            id="selectorGrip_resize_ne"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'ne-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={ne.x}
            y={ne.y}></rect>
          <rect
            id="selectorGrip_resize_sw"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'sw-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={sw.x}
            y={sw.y}></rect>
          <rect
            id="selectorGrip_resize_se"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'se-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={se.x}
            y={se.y}></rect>
        </g>
      );
    }
    return (
      <g
        attrs-data-testid={`Rectangle - ${node.children[0]?.text}`}
        attrs-data-testselected={`${node.selected}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}>
        <rect
          class-selected={node.selected}
          class-mouseover={node.hoverFeedback}
          x={0}
          y={0}
          width={size.width}
          height={size.height}
          style={styleObject}
        />
        {selectedHandles}
        {context.renderChildren(node)}
      </g>
    );
  }
}
