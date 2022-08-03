/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { RectangularNodeView, RenderingContext, svg, SLabel } from 'sprotty';
import { Node, ParametricSVGNodeStyle } from '../Diagram.types';
import { createResizeHandles } from './ViewUtils';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

/**
 * The view used to display nodes with a svg image style.
 *
 * @lfasani
 */
export class ParametricSVGImageView extends RectangularNodeView {
  // @ts-ignore
  render(node: Readonly<Node>, context: RenderingContext) {
    const { selected, hoverFeedback, bounds } = node;
    const style = node.style as ParametricSVGNodeStyle;
    const styleObject = {
      fill: 'none',
      stroke: style.borderSize > 0 ? style.borderColor : 'none',
      'stroke-width': style.borderSize,
    };

    if (node.selected) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 2px';
    }

    let borderStyle;
    switch (style.borderStyle) {
      case 'Dash':
        borderStyle = '4,4';
        break;
      case 'Dot':
        borderStyle = '2,2';
        break;
      case 'Dash_Dot':
        borderStyle = '2,4,2';
        break;
      default:
        break;
    }

    const imageSize = {
      width: Math.max(0, bounds.width),
      height: Math.max(0, bounds.height),
    };
    const rectangleSize = {
      width: Math.max(0, bounds.width),
      height: Math.max(0, bounds.height),
    };

    let rectanglePosition = { x: 0, y: 0 };
    // Adapt the size and position to show the full width of the border
    if (style.borderSize && style.borderSize > 0) {
      rectangleSize.width += style.borderSize;
      rectangleSize.height += style.borderSize;
      rectanglePosition.x -= style.borderSize / 2;
      rectanglePosition.y -= style.borderSize / 2;
    }
    const selectedHandles = createResizeHandles(node);
    const labelWidth = node.editableLabel.size.width;
    const labelHeight = node.editableLabel.size.height;
    const svgUrl =
      style.svgURL +
      `?width=${imageSize.width}&height=${
        imageSize.height
      }&labelWidth=${labelWidth}&labelHeight=${labelHeight}&color=${encodeURIComponent(
        style.backgroundColor
      )}&borderColor=${encodeURIComponent(style.borderColor)}&borderStyle=${borderStyle}&borderSize=${
        style.borderSize
      }`;

    let nodeLabel: SLabel | undefined = undefined;
    const labelIndex = node.children.findIndex((child) => child instanceof SLabel);
    if (labelIndex > -1) {
      nodeLabel = node.children.at(labelIndex) as SLabel;
    }
    return (
      <g
        attrs-data-testid={`Image - ${nodeLabel?.text}`}
        attrs-data-testselected={`${node.selected}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}>
        <image
          class-selected={selected}
          class-mouseover={hoverFeedback}
          x={rectanglePosition.x}
          y={rectanglePosition.y}
          width={rectangleSize.width}
          height={rectangleSize.height}
          href={svgUrl}
        />
        {selectedHandles}
        {context.renderChildren(node)}
      </g>
    );
  }
}
