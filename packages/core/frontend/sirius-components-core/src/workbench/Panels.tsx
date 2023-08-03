/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import React, { useRef, useState } from 'react';
import { PanelsProps } from './Panels.types';
import { Site } from './Site';

const MIN_PANEL_WIDTH: number = 42;
const MAIN_AREA_MIN_WIDTH: number = 100;
const RESIZER_WIDTH: number = 4;

const usePanelStyles = makeStyles((theme) => ({
  panel: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: 'minmax(0, 1fr)',
  },
  verticalResizer: {
    display: 'grid',
    width: `${RESIZER_WIDTH}px`,
    cursor: 'col-resize',
    '& div': {
      backgroundColor: theme.palette.divider,
      borderColor: theme.palette.divider,
      borderRightStyle: 'solid',
      borderRightWidth: '1px',
    },
  },
}));

interface PanelState {
  isDragging: boolean;
  initialPosition: number;
  expanded: boolean;
  resizablePanelSize: number;
}

export const Panels = ({
  editingContextId,
  readOnly,
  leftContributions,
  rightContributions,
  mainArea,
  leftPanelInitialSize,
  rightPanelInitialSize,
}: PanelsProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [leftPanelState, setLeftPanelState] = useState<PanelState>({
    isDragging: false,
    initialPosition: 0,
    expanded: true,
    resizablePanelSize: leftPanelInitialSize,
  });
  const [rightPanelState, setRightPanelState] = useState<PanelState>({
    isDragging: false,
    initialPosition: 0,
    expanded: true,
    resizablePanelSize: rightPanelInitialSize,
  });

  const startResizeLeft: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const initialPosition: number = event.clientX;
    event.preventDefault();
    setLeftPanelState((prevState) => {
      return {
        isDragging: true,
        initialPosition,
        expanded: prevState.expanded,
        resizablePanelSize: prevState.expanded ? prevState.resizablePanelSize : MIN_PANEL_WIDTH,
      };
    });
  };

  const startResizeRight: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const initialPosition: number = event.clientX;
    event.preventDefault();
    setRightPanelState((prevState) => {
      return {
        isDragging: true,
        initialPosition,
        expanded: prevState.expanded,
        resizablePanelSize: prevState.expanded ? prevState.resizablePanelSize : MIN_PANEL_WIDTH,
      };
    });
  };

  const resizePanel: React.MouseEventHandler<HTMLDivElement> = (event) => {
    if (leftPanelState.isDragging) {
      const initialPosition: number = event.clientX;
      event.preventDefault();
      setLeftPanelState((prevState) => {
        const delta = initialPosition - prevState.initialPosition;
        let resizablePanelSize = Math.max(MIN_PANEL_WIDTH, prevState.resizablePanelSize + delta);
        if (ref.current) {
          resizablePanelSize = Math.min(
            resizablePanelSize,
            ref.current.clientWidth - rightPanelState.resizablePanelSize - 2 * RESIZER_WIDTH - MAIN_AREA_MIN_WIDTH
          );
        }
        return {
          ...prevState,
          initialPosition,
          resizablePanelSize,
          expanded: resizablePanelSize > MIN_PANEL_WIDTH,
        };
      });
    }
    if (rightPanelState.isDragging) {
      const initialPosition: number = event.clientX;
      event.preventDefault();
      setRightPanelState((prevState) => {
        const delta = initialPosition - prevState.initialPosition;
        let resizablePanelSize = Math.max(MIN_PANEL_WIDTH, prevState.resizablePanelSize - delta);
        if (ref.current) {
          resizablePanelSize = Math.min(
            resizablePanelSize,
            ref.current.clientWidth - leftPanelState.resizablePanelSize - 2 * RESIZER_WIDTH - MAIN_AREA_MIN_WIDTH
          );
        }
        return {
          ...prevState,
          initialPosition,
          resizablePanelSize,
          expanded: resizablePanelSize > MIN_PANEL_WIDTH,
        };
      });
    }
  };

  const stopResize: React.MouseEventHandler<HTMLDivElement> = () => {
    if (leftPanelState.isDragging) {
      setLeftPanelState((prevState) => {
        return { ...prevState, isDragging: false, initialPosition: 0 };
      });
    }
    if (rightPanelState.isDragging) {
      setRightPanelState((prevState) => {
        return { ...prevState, isDragging: false, initialPosition: 0 };
      });
    }
  };

  const leftWidth: number = leftPanelState.expanded ? leftPanelState.resizablePanelSize : MIN_PANEL_WIDTH;
  const rightWidth: number = rightPanelState.expanded ? rightPanelState.resizablePanelSize : MIN_PANEL_WIDTH;
  let style: React.CSSProperties = {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: `${leftWidth}px min-content minmax(0, 1fr) min-content ${rightWidth}px`,
  };

  const styles = usePanelStyles();
  return (
    <div style={style} onMouseMove={resizePanel} onMouseUp={stopResize} onMouseLeave={stopResize} ref={ref}>
      <div className={styles.panel}>
        <Site
          editingContextId={editingContextId}
          readOnly={readOnly}
          side="left"
          expanded={leftPanelState.expanded}
          toggleExpansion={() => {
            setLeftPanelState((prevState) => {
              const newExpanded: boolean = !prevState.expanded;
              let newWidth: number = prevState.resizablePanelSize;
              if (newExpanded && newWidth === MIN_PANEL_WIDTH) {
                newWidth = leftPanelInitialSize;
              }
              return {
                ...prevState,
                expanded: !prevState.expanded,
                resizablePanelSize: newWidth,
              };
            });
          }}
          contributions={leftContributions}
        />
      </div>
      <div className={styles.verticalResizer} onMouseDown={startResizeLeft} data-testid="left-resizer">
        <div />
      </div>
      <div className={styles.panel}>{mainArea}</div>
      <div className={styles.verticalResizer} onMouseDown={startResizeRight} data-testid="right-resizer">
        <div />
      </div>
      <div className={styles.panel}>
        <Site
          editingContextId={editingContextId}
          readOnly={readOnly}
          side="right"
          expanded={rightPanelState.expanded}
          toggleExpansion={() => {
            setRightPanelState((prevState) => {
              const newExpanded: boolean = !prevState.expanded;
              let newWidth: number = prevState.resizablePanelSize;
              if (newExpanded && newWidth === MIN_PANEL_WIDTH) {
                newWidth = rightPanelInitialSize;
              }
              return {
                ...prevState,
                expanded: newExpanded,
                resizablePanelSize: newWidth,
              };
            });
          }}
          contributions={rightContributions}
        />
      </div>
    </div>
  );
};
