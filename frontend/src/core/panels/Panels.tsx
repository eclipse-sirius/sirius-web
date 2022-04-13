/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import PropTypes from 'prop-types';
import React, { useState } from 'react';
import styles from './Panels.module.css';

export const FIRST_PANEL = 'FIRST_PANEL';
export const SECOND_PANEL = 'SECOND_PANEL';

const propTypes = {
  resizablePanel: PropTypes.oneOf([FIRST_PANEL, SECOND_PANEL]),
  initialResizablePanelSize: PropTypes.number.isRequired,
};
const defaultProps = {
  resizablePanel: FIRST_PANEL,
};

export const Panels = ({ firstPanel, secondPanel, resizablePanel, initialResizablePanelSize }) => {
  const initialState = { isDragging: false, initialPosition: 0, resizablePanelSize: initialResizablePanelSize };
  const [state, setState] = useState(initialState);
  const { isDragging, resizablePanelSize } = state;

  const startResize = (event) => {
    let initialPosition = event.clientX;
    setState((prevState) => {
      return { isDragging: true, initialPosition, resizablePanelSize: prevState.resizablePanelSize };
    });
  };

  const resizePanel = (event) => {
    if (isDragging) {
      let initialPosition = event.clientX;
      setState((prevState) => {
        const delta = initialPosition - prevState.initialPosition;
        let resizablePanelSize = prevState.resizablePanelSize + delta;
        if (resizablePanel === SECOND_PANEL) {
          resizablePanelSize = prevState.resizablePanelSize - delta;
        }
        return { ...prevState, initialPosition, resizablePanelSize };
      });
    }
  };

  const stopResize = () => {
    if (isDragging) {
      setState((prevState) => {
        return { ...prevState, isDragging: false, initialPosition: 0 };
      });
    }
  };

  let style = {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: `${resizablePanelSize}px min-content minmax(0, 1fr)`,
  };
  if (resizablePanel === SECOND_PANEL) {
    style = {
      display: 'grid',
      gridTemplateRows: 'minmax(0, 1fr)',
      gridTemplateColumns: `minmax(0, 1fr) min-content ${resizablePanelSize}px`,
    };
  }

  let resizerClassName = styles.verticalResizer;
  return (
    <div style={style} onMouseMove={resizePanel} onMouseUp={stopResize} onMouseLeave={stopResize}>
      <div className={styles.panel}>{firstPanel}</div>
      <div className={resizerClassName} onMouseDown={startResize}>
        <div />
      </div>
      <div className={styles.panel}>{secondPanel}</div>
    </div>
  );
};
Panels.propTypes = propTypes;
Panels.defaultProps = defaultProps;
