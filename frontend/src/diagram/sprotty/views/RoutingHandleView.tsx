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
import { VNode } from 'snabbdom';
import { RenderingContext, RoutedPoint, setAttr, SRoutingHandle, SRoutingHandleView } from 'sprotty';

/**
 * View used to handle 'source', 'target' and 'junction' routing handles.
 */
export class RoutingHandleView extends SRoutingHandleView {
  render(handle: Readonly<SRoutingHandle>, context: RenderingContext, args?: { route?: RoutedPoint[] }): VNode {
    const handleVNode = super.render(handle, context, args);
    setAttr(handleVNode, 'stroke', 'var(--daintree)');
    setAttr(handleVNode, 'stroke-width', '1');
    setAttr(handleVNode, 'fill', '#fafafa');

    if (handle.hoverFeedback) {
      setAttr(handleVNode, 'fill', 'var(--blue-lagoon-lighten-20)');
    }

    if (handle.selected) {
      setAttr(handleVNode, 'fill', 'var(--blue-lagoon)');
      setAttr(handleVNode, 'opacity', '1');
    }

    return handleVNode;
  }

  getRadius(): number {
    return 2.5;
  }
}
