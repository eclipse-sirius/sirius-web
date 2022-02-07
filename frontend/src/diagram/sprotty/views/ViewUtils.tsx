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
import { svg } from 'sprotty';
import { isResizeable } from '../resize/model';

/**
 * Adds resize handles to the given node.
 *
 * @gcoutable
 */
export const createResizeHandles = (node) => {
  const width = Math.max(0, node.bounds.width);
  const height = Math.max(0, node.bounds.height);

  let selectedHandles = null;

  if (node.selected) {
    const selectorSize = 6;
    const delta = selectorSize / 2;
    const north = {
      x: width / 2 - delta,
      y: -delta,
    };
    const south = {
      x: width / 2 - delta,
      y: height - delta,
    };
    const west = {
      x: -delta,
      y: height / 2 - delta,
    };
    const east = {
      x: width - delta,
      y: height / 2 - delta,
    };
    const nw = {
      x: -delta,
      y: -delta,
    };
    const ne = {
      x: width - delta,
      y: -delta,
    };
    const se = {
      x: width - delta,
      y: height - delta,
    };
    const sw = {
      x: -delta,
      y: height - delta,
    };
    if (isResizeable(node)) {
      selectedHandles = (
        <g>
          <rect
            id="selectorGrip_resize_n"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'n-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={north.x}
            y={north.y}
          ></rect>
          <rect
            id="selectorGrip_resize_s"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 's-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={south.x}
            y={south.y}
          ></rect>
          <rect
            id="selectorGrip_resize_w"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'w-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={west.x}
            y={west.y}
          ></rect>
          <rect
            id="selectorGrip_resize_e"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'e-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={east.x}
            y={east.y}
          ></rect>
          <rect
            id="selectorGrip_resize_nw"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'nw-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={nw.x}
            y={nw.y}
          ></rect>
          <rect
            id="selectorGrip_resize_ne"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'ne-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={ne.x}
            y={ne.y}
          ></rect>
          <rect
            id="selectorGrip_resize_sw"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'sw-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={sw.x}
            y={sw.y}
          ></rect>
          <rect
            id="selectorGrip_resize_se"
            width={selectorSize}
            height={selectorSize}
            style={{ cursor: 'se-resize', fill: 'var(--blue-lagoon) ' }}
            pointer-events="all"
            x={se.x}
            y={se.y}
          ></rect>
        </g>
      );
    }
  }

  return selectedHandles;
};
