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
import { httpOrigin } from 'common/URL';
import { svg } from 'snabbdom-jsx';
import { RectangularNodeView } from 'sprotty';

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
      styleObject['outline'] = 'var(--red) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--red) solid 2px';
    }
    return (
      <g attrs-data-testid={`Image - ${node.children[0]?.text}`}>
        <image
          class-selected={selected}
          class-mouseover={hoverFeedback}
          x={0}
          y={0}
          width={Math.max(0, bounds.width)}
          height={Math.max(0, bounds.height)}
          href={httpOrigin + style.imageURL}
          style={styleObject}
        />
        {context.renderChildren(node)}
      </g>
    );
  }
}
