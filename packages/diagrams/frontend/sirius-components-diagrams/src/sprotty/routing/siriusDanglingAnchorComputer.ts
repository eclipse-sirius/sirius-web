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

import { PolylineEdgeRouter } from 'sprotty';
import { Point } from 'sprotty-protocol';
import { Edge, Node } from '../Diagram.types';
import { SiriusRectangleAnchor } from './siriusPolylineAnchor';

export const SIRIUS_DANGLING_ANCHOR_KIND = 'sirius-dangling-anchor';

export class SiriusDanglingAnchorComputer extends SiriusRectangleAnchor {
  override get kind(): string {
    return PolylineEdgeRouter.KIND + ':' + SIRIUS_DANGLING_ANCHOR_KIND;
  }
  override getReferencePosition(connectable: Node, _edge: Edge): Point {
    return connectable.position;
  }
}
