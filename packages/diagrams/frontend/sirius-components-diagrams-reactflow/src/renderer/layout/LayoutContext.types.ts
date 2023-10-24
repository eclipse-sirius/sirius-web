/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { XYPosition } from 'reactflow';
export interface LayoutContextContextValue {
  referencePosition: ReferencePosition | null;

  /**
   * Store the reference position in the layout context for a later use, like in the next refresh to position new created node at the reference position stored in the context.
   *
   * The reference position will be reset using resetReferencePosition once it has been used.
   * In other world, the setReferencePosition caller is responsible to also call the resetReferencePosition
   *
   * @param referencePosition The reference position meant to be use in a later use.
   * @param parentId The element id directly under the reference position, null or undefined if it is the diagram.
   */
  setReferencePosition: (referencePosition: ReferencePosition) => void;

  /**
   * Reset the reference position stored in the layout context.
   *
   * This meant to be used by the caller of setReferencePosition once the reference position has been used.
   */
  resetReferencePosition: () => void;
}

export interface LayoutContextContextProviderProps {
  children: React.ReactNode;
}

export interface LayoutContextContextProviderState {
  referencePosition: ReferencePosition | null;
}

export interface ReferencePosition {
  position: XYPosition;
  parentId: string | null | undefined;
}
