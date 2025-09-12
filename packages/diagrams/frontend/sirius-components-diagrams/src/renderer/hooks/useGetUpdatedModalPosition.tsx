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
import { ReactFlowState, useStore, XYPosition } from '@xyflow/react';
import { UseGetUpdatedModalPositionValue } from './useGetUpdatedModalPosition.types';

const equalityFunction = (oldValue: DOMRect | undefined, newValue: DOMRect | undefined) =>
  Object.is(oldValue, newValue) ||
  (oldValue?.bottom === newValue?.bottom &&
    oldValue?.top === newValue?.top &&
    oldValue?.left === newValue?.left &&
    oldValue?.right === newValue?.right);
const xyFlowDomBoundsSelector = (state: ReactFlowState): DOMRect | undefined => state.domNode?.getBoundingClientRect();

// Using this hook will make the component rerender when the value of the xyFlowDomBoundsSelector changes
export const useGetUpdatedModalPosition = (): UseGetUpdatedModalPositionValue => {
  const xyFlowDomBounds = useStore(xyFlowDomBoundsSelector, equalityFunction);

  const getUpdatedModalPosition = (initialPosition: XYPosition, modalRef: React.RefObject<HTMLDivElement>) => {
    let position: XYPosition = { ...initialPosition };
    if (modalRef.current && xyFlowDomBounds) {
      const modalBounds: DOMRect = modalRef.current.getBoundingClientRect();
      if (modalBounds) {
        if (initialPosition.x + modalBounds.width > xyFlowDomBounds.width) {
          position.x = xyFlowDomBounds.width - modalBounds.width;
        }

        if (initialPosition.y + modalBounds.height > xyFlowDomBounds.height) {
          position.y = xyFlowDomBounds.height - modalBounds.height;
        }
      }
    }
    return position;
  };

  const getUpdatedBounds = (modalRef: React.RefObject<HTMLDivElement>) => {
    let draggableBounds = {
      left: 0,
      top: 0,
      bottom: xyFlowDomBounds?.height ?? 0,
      right: xyFlowDomBounds?.width ?? 0,
    };

    if (modalRef && modalRef.current && xyFlowDomBounds) {
      const modalBounds: DOMRect = modalRef.current.getBoundingClientRect();
      if (modalBounds) {
        draggableBounds.right = xyFlowDomBounds.width - modalBounds.width;
        draggableBounds.bottom = xyFlowDomBounds.height - modalBounds.height;
      }
    }
    return draggableBounds;
  };

  return { getUpdatedModalPosition, getUpdatedBounds };
};
