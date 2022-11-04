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
import { RectangularNodeView, svg } from 'sprotty';
import { createResizeHandles } from './ViewUtils';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

/**
 * The view used to display nodes with an image style.
 *
 * @sbegaudeau
 */
export class ImageView extends RectangularNodeView {
  // @ts-ignore
  render(node, context) {
    const { selected, hoverFeedback, bounds, style } = node;
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

    switch (style.borderStyle) {
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

    return (
      <g
        attrs-data-testid={`Image - ${node.children[0]?.text}`}
        attrs-data-testselected={`${node.selected}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}>
        <rect
          class-selected={selected}
          class-mouseover={hoverFeedback}
          x={rectanglePosition.x}
          y={rectanglePosition.y}
          rx={style.borderRadius}
          width={rectangleSize.width}
          height={rectangleSize.height}
          style={styleObject}
        />
        <image
          x={0}
          y={0}
          width={imageSize.width}
          height={imageSize.height}
          href={style.imageURL}
          opacity={style.opacity}
        />
        {selectedHandles}
        {context.renderChildren(node)}
      </g>
    );
  }
}
