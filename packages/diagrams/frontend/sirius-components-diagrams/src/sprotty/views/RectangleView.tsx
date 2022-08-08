/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { RectangularNodeView, RenderingContext, SLabel, svg } from 'sprotty';
import { Node, RectangularNodeStyle } from '../Diagram.types';
import { createResizeHandles } from './ViewUtils';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

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
  render(node: Readonly<Node>, context: RenderingContext) {
    const nodeStyle: RectangularNodeStyle = node.style as RectangularNodeStyle;
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
    const size = {
      width: Math.max(0, node.bounds.width),
      height: Math.max(0, node.bounds.height),
    };

    const radius = nodeStyle.borderRadius;

    const selectedHandles = createResizeHandles(node);

    let children = [...node.children];

    const nodeLabel: SLabel = children.shift() as SLabel;
    const renderedNodeLabel = context.renderElement(nodeLabel);

    const headerSeparator = createHeaderSeparator(node, nodeStyle, nodeLabel);

    const renderedChildren = children.map((item) => context.renderElement(item));

    return (
      <g
        attrs-data-testid={`Rectangle - ${nodeLabel.text}`}
        attrs-data-testselected={`${node.selected}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}>
        <rect
          class-selected={node.selected}
          class-mouseover={node.hoverFeedback}
          x={0}
          y={0}
          rx={radius}
          width={size.width}
          height={size.height}
          style={styleObject}
        />
        {selectedHandles}
        {renderedNodeLabel}
        {headerSeparator}
        {renderedChildren}
        {context.renderChildren(node)}
      </g>
    );
  }
}

const createHeaderSeparator = (node: Node, nodeStyle: RectangularNodeStyle, nodeLabel: SLabel) => {
  if (nodeStyle.withHeader) {
    const headerSeparatorStyle = {
      stroke: nodeStyle.borderColor,
      'stroke-width': nodeStyle.borderSize,
    };

    // The label y position indicates the padding top, we suppose the same padding is applied to the bottom.
    const headerLabelPadding = nodeLabel?.bounds?.y;

    return (
      <line
        x1={0}
        y1={nodeLabel.bounds.height + 2 * headerLabelPadding}
        x2={node.bounds.width}
        y2={nodeLabel.bounds.height + 2 * headerLabelPadding}
        style={headerSeparatorStyle}
      />
    );
  }
  return null;
};
