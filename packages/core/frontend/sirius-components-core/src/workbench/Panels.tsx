/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { useRef, useState } from 'react';
import {
  disableGlobalCursorStyles,
  ImperativePanelHandle,
  Panel,
  PanelGroup,
  PanelResizeHandle,
} from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelsProps } from './Panels.types';
import { Sidebar } from './Sidebar';
import { WorkbenchViewContribution } from './Workbench.types';
import { WorkbenchPart } from './WorkbenchPart';

/**
 * React Resizable Panels based panels
 */

const usePanelStyles = makeStyles()((theme) => ({
  panel: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: 'minmax(0, 1fr)',
  },
  mainArea: {
    display: 'grid',
    height: '100%',
  },
  verticalResizer: {
    display: 'grid',
    width: `0.25rem`,
    cursor: 'col-resize',
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderRightStyle: 'solid',
    borderRightWidth: '1px',
  },
}));

export const Panels = ({
  editingContextId,
  readOnly,
  leftContributions,
  rightContributions,
  mainArea,
  leftPanelInitialSize,
  rightPanelInitialSize,
}: PanelsProps) => {
  disableGlobalCursorStyles();

  const { classes } = usePanelStyles();
  const leftRef = useRef<ImperativePanelHandle>(null);
  const rightRef = useRef<ImperativePanelHandle>(null);
  const [leftSelectedContributionIndex, setLeftSelectedContributionIndex] = useState<number>(0);
  const [rightSelectedContributionIndex, setRightSelectedContributionIndex] = useState<number>(0);

  const leftContribution: WorkbenchViewContribution | null = leftContributions[leftSelectedContributionIndex] || null;
  const rightContribution: WorkbenchViewContribution | null =
    rightContributions[rightSelectedContributionIndex] || null;
  return (
    <div style={{ display: 'flex' }}>
      <Sidebar
        side="left"
        panelRef={leftRef}
        contributions={leftContributions}
        selectedContributionIndex={leftSelectedContributionIndex}
        onContributionSelected={setLeftSelectedContributionIndex}
      />
      <PanelGroup direction="horizontal">
        <Panel
          id="left"
          className={classes.panel}
          defaultSize={leftPanelInitialSize}
          collapsible
          collapsedSize={0}
          minSize={10}
          ref={leftRef}>
          {leftContribution !== null ? (
            <WorkbenchPart
              editingContextId={editingContextId}
              readOnly={readOnly}
              side="left"
              contribution={leftContribution}
            />
          ) : null}
        </Panel>
        <PanelResizeHandle className={classes.verticalResizer} data-testid="left-resizer" />
        <Panel id="mainArea" minSize={30}>
          <div className={classes.mainArea}>{mainArea}</div>
        </Panel>
        <PanelResizeHandle className={classes.verticalResizer} data-testid="right-resizer" />
        <Panel
          id="right"
          className={classes.panel}
          defaultSize={rightPanelInitialSize}
          collapsible
          collapsedSize={0}
          minSize={10}
          ref={rightRef}>
          {rightContribution !== null ? (
            <WorkbenchPart
              editingContextId={editingContextId}
              readOnly={readOnly}
              side="right"
              contribution={rightContribution}
            />
          ) : null}
        </Panel>
      </PanelGroup>
      <Sidebar
        contributions={rightContributions}
        side="right"
        panelRef={rightRef}
        selectedContributionIndex={rightSelectedContributionIndex}
        onContributionSelected={setRightSelectedContributionIndex}
      />
    </div>
  );
};
