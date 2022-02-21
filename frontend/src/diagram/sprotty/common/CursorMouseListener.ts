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

import { decorate, inject } from 'inversify';
import { MouseListener, TYPES } from 'sprotty';
import { UpdateModelAction } from 'sprotty-protocol';
import { DiagramServer } from '../DiagramServer';

/**
 * Update the cursor if need.
 *
 * It triggers a model update every time the cursor has to change. This will trigger a rendering that will use the new cursor value.
 */
export class CursorMouseListener extends MouseListener {
  constructor(protected readonly diagramServer: DiagramServer) {
    super();
  }

  mouseMove(element, event) {
    const root = element.root;
    const elementWithTarget = findElementWithTarget(element);
    const expectedCursor = this.diagramServer.getCursorOn(elementWithTarget, this.diagramServer);
    if (root.cursor !== expectedCursor) {
      root.cursor = expectedCursor;
      return [UpdateModelAction.create(root)];
    }

    return [];
  }
}
decorate(inject(TYPES.ModelSource) as ParameterDecorator, CursorMouseListener, 0);

const findElementWithTarget = (element) => {
  if (element.targetObjectId) {
    return element;
  } else if (element.parent) {
    return findElementWithTarget(element.parent);
  }
  // Otherwise, use the diagram as element with target.
  return element.root;
};
