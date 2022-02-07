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
import { RectangularNodeView, svg } from 'sprotty';
import { createResizeHandles } from './ViewUtils';

/**
 * The view used to display nodes with an image style.
 *
 * @sbegaudeau
 */
export class ImageView extends RectangularNodeView {
  // @ts-ignore
  render(node, context) {
    const { selected, hoverFeedback, bounds, style } = node;

    const styleObject = {};

    if (node.selected) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 2px';
    }
    const size = {
      width: Math.max(0, bounds.width),
      height: Math.max(0, bounds.height),
    };

    const selectedHandles = createResizeHandles(node);

    return (
      <g
        attrs-data-testid={`Image - ${node.children[0]?.text}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}
      >
        <image
          class-selected={selected}
          class-mouseover={hoverFeedback}
          x={0}
          y={0}
          width={size.width}
          height={size.height}
          href={style.imageURL}
          style={styleObject}
        />
        {selectedHandles}
        {context.renderChildren(node)}
      </g>
    );
  }
}
