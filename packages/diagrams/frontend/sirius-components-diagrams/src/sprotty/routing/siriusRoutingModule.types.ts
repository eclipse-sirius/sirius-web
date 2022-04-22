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

import { IAnchorComputer, SConnectableElement } from 'sprotty';
import { Point } from 'sprotty-protocol';
import { Edge } from '../Diagram.types';

export interface ISiriusAnchorComputer extends IAnchorComputer {
  getSelfLoopAnchor: (connectable: SConnectableElement, refPoint: Point, offset?: number) => Point;
  getReferencePosition: (connectable: SConnectableElement, edge: Edge) => Point;

  /**
   * Compute an anchor position for routing an end of an edge towards this element - the other edge end -.
   *
   * @param connectable The node or port an edge should be connected to.
   * @param insideConnectableBoundsPoint The point from which the edge is routed from, in the same coordinate system as the connectable. It should represent a point inside the bounds of the connectable.
   * @param otherEndRefPoint The point from which the edge is routed towards, in the same coordinate system as the connectable.
   * @param offset An optional offset value to be considered in the anchor computation;
   *               positive values should shift the anchor away from this element, negative values
   *               should shift the anchor more to the inside. Use this to adapt ot arrow heads.
   * @returns the anchor position
   */
  getSiriusAnchor: (
    connectable: SConnectableElement,
    insideConnectableBoundsPoint: Point,
    otherEndRefPoint: Point,
    offset: number
  ) => Point;

  /**
   * Update the anchor position of the edge regarding the connectable and the given position.
   *
   * This update is temporary since the anchor position will be update by the server in a second time.
   * It prevents the edge from blink between the moment the edge is reconnected and the moment the diagram is refreshed.
   *
   * @param connectable The connectable
   * @param edge The edge which one of its end has been moved
   * @param position The position in the same coordinate system has the connectable
   */
  updateAnchor: (connectable: SConnectableElement, edge: Edge, position: Point) => void;
}

export const isSiriusAnchor = (anchorComputer: IAnchorComputer): anchorComputer is ISiriusAnchorComputer =>
  'getSelfLoopAnchor' in anchorComputer &&
  'getReferencePosition' in anchorComputer &&
  'getSiriusAnchor' in anchorComputer &&
  'updateAnchor' in anchorComputer;
