/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { SLabelView, svg } from 'sprotty';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

/**
 * The view used to display nodes with a list item style.
 *
 * @gcoutable
 */
export class ListItemView extends SLabelView {
  /**
   * Renders the given node in the context.
   * @param node The node
   * @param context The context
   */
  // @ts-ignore
  render(node, context) {
    const nodeStyle = node.style;
    const styleObject = {
      fill: nodeStyle.backgroundColor,
    };

    if (node.selected) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 1px';
    }

    if (node.hoverFeedback) {
      styleObject['outline'] = 'var(--blue-lagoon) solid 2px';
    }

    const nodeLabel = node.children[0];

    return (
      <g
        attrs-data-testid={`Item - ${nodeLabel?.text}`}
        attrs-data-testselected={`${node.selected}`}
        attrs-data-nodeid={node.id}
        attrs-data-descriptionid={node.descriptionId}>
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
