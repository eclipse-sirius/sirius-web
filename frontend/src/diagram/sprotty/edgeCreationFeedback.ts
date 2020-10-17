/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

const findEdge = () => document.getElementById('edge-creation-feedback');

const onHiddenFeedbackEdge = (callback) => {
  let edge = findEdge();
  if (edge && edge.getAttribute('visibility') === 'hidden') {
    callback(edge);
  }
};

const onVisibleFeedbackEdge = (callback) => {
  let edge = findEdge();
  if (edge && edge.getAttribute('visibility') !== 'hidden') {
    callback(edge);
  }
};

export const edgeCreationFeedback = {
  /**
   * edge creation tool armed: reveal the feedback edge and set its style
   */
  init: (x, y) => {
    onHiddenFeedbackEdge((edge) => {
      edge.setAttribute('x1', x);
      edge.setAttribute('y1', y);
      edge.setAttribute('x2', x);
      edge.setAttribute('y2', y);

      edge.setAttribute('visibility', 'visible');
      edge.setAttribute('stroke', 'black');
      edge.setAttribute('stroke-dasharray', '4');
      edge.setAttribute('stroke-width', '2');
      // This is needed so that when selecting the target element
      // the feedback edge does not "trap" the mouse click and
      // block the detection of the actual targetElement.
      edge.setAttribute('pointer-events', 'none');
    });
  },
  /**
   * mouse moved: update the target position to follow the mouse
   */
  update: (x, y) => {
    onVisibleFeedbackEdge((edge) => {
      edge.setAttribute('x2', x);
      edge.setAttribute('y2', y);
    });
  },
  /**
   * edge creation tool disarmed: hide the feedback edge
   */
  reset: () => {
    onVisibleFeedbackEdge((edge) => {
      edge.setAttribute('visibility', 'hidden');
      edge.setAttribute('x1', 0);
      edge.setAttribute('y1', 0);
      edge.setAttribute('x2', 0);
      edge.setAttribute('y2', 0);
    });
  },
};
