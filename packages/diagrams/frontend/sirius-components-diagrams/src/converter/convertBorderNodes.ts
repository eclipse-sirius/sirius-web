/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLBorderNodePosition } from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition } from '../renderer/DiagramRenderer.types';

export const convertBorderNodePosition = (
  initialBorderNodePosition: GQLBorderNodePosition,
  defaultPosition: BorderNodePosition
) => {
  let borderNodePosition: BorderNodePosition = defaultPosition;
  if (initialBorderNodePosition === 'WEST') {
    borderNodePosition = BorderNodePosition.WEST;
  } else if (initialBorderNodePosition === 'EAST') {
    borderNodePosition = BorderNodePosition.EAST;
  } else if (initialBorderNodePosition === 'SOUTH') {
    borderNodePosition = BorderNodePosition.SOUTH;
  } else if (initialBorderNodePosition === 'NORTH') {
    borderNodePosition = BorderNodePosition.NORTH;
  }
  return borderNodePosition;
};
