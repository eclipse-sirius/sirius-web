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
import { VNode } from 'snabbdom';
import { RenderingContext, RoutedPoint, setAttr, SRoutingHandle, SRoutingHandleView, svg } from 'sprotty';
const preventRemovalOfUnusedImportByPrettier = svg !== null;

/**
 * View used to handle 'line' routing handles.
 */
export class VolatileRoutingHandleView extends SRoutingHandleView {
  override render(
    handle: Readonly<SRoutingHandle>,
    context: RenderingContext,
    args?: { route?: RoutedPoint[] }
  ): VNode {
    const handleVNode = super.render(handle, context, args);
    setAttr(handleVNode, 'stroke', 'var(--daintree)');
    setAttr(handleVNode, 'stroke-width', '1');
    setAttr(handleVNode, 'fill', '#fafafa');
    setAttr(handleVNode, 'opacity', '0.35');

    if (handle.hoverFeedback) {
      setAttr(handleVNode, 'opacity', '0.65');
    }

    if (handle.selected) {
      setAttr(handleVNode, 'fill', 'var(--blue-lagoon)');
      setAttr(handleVNode, 'opacity', '1');
    }

    return handleVNode;
  }

  override getRadius(): number {
    return 2.5;
  }
}
