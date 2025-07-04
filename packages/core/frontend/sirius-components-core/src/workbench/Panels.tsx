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
import { ForwardedRef, forwardRef, useImperativeHandle, useRef, useState } from 'react';
import {
  disableGlobalCursorStyles,
  ImperativePanelHandle,
  Panel,
  PanelGroup,
  PanelResizeHandle,
} from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelsProps, PanelState } from './Panels.types';
import { Sidebar } from './Sidebar';
import { PanelsHandle, WorkbenchViewConfiguration, WorkbenchViewContribution } from './Workbench.types';
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

export const Panels = forwardRef<PanelsHandle | null, PanelsProps>(
  (
    {
      editingContextId,
      readOnly,
      leftContributions,
      leftPanelConfiguration,
      rightContributions,
      rightPanelConfiguration,
      mainArea,
      leftPanelInitialSize,
      rightPanelInitialSize,
    }: PanelsProps,
    refPanelsHandle: ForwardedRef<PanelsHandle | null>
  ) => {
    disableGlobalCursorStyles();

    const leftActivePanelConfiguration: WorkbenchViewConfiguration | null =
      leftPanelConfiguration?.views.find((configuration) => configuration && configuration.isActive) ?? null;
    const indexOfLeftActivePanelContribution = leftContributions.findIndex(
      (contribution) => contribution.id === leftActivePanelConfiguration?.id
    );
    const rightActivePanelConfiguration: WorkbenchViewConfiguration | null =
      rightPanelConfiguration?.views.find((configuration) => configuration && configuration.isActive) ?? null;
    const indexOfRightActivePanelContribution = rightContributions.findIndex(
      (contribution) => contribution.id === rightActivePanelConfiguration?.id
    );

    const leftInitialState: PanelState = {
      selectedContributionIndex: indexOfLeftActivePanelContribution !== -1 ? indexOfLeftActivePanelContribution : 0,
      isOpen: false,
    };
    const rightInitialState: PanelState = {
      selectedContributionIndex: indexOfRightActivePanelContribution !== -1 ? indexOfRightActivePanelContribution : 0,
      isOpen: false,
    };

    const { classes } = usePanelStyles();
    const leftRef = useRef<ImperativePanelHandle>(null);
    const rightRef = useRef<ImperativePanelHandle>(null);
    const [leftPanelState, setLeftPanelState] = useState<PanelState>(leftInitialState);
    const [rightPanelState, setRightPanelState] = useState<PanelState>(rightInitialState);

    const leftContribution: WorkbenchViewContribution | null =
      leftContributions[leftPanelState.selectedContributionIndex] || null;
    const rightContribution: WorkbenchViewContribution | null =
      rightContributions[rightPanelState.selectedContributionIndex] || null;

    useImperativeHandle(
      refPanelsHandle,
      () => {
        return {
          getSidePanelConfigurations: () => {
            const leftViewConfigurations: WorkbenchViewConfiguration[] = leftContributions.map((contribution) => ({
              id: contribution.id,
              isActive: contribution.id === leftContribution?.id,
            }));
            const rightViewConfigurations: WorkbenchViewConfiguration[] = rightContributions.map((contribution) => ({
              id: contribution.id,
              isActive: contribution.id === rightContribution?.id,
            }));
            return [
              { id: 'left', views: leftViewConfigurations },
              { id: 'right', views: rightViewConfigurations },
            ];
          },
        };
      },
      [leftContribution, rightContribution]
    );

    const handleLeftContributionSelection = (index: number) => {
      setLeftPanelState((prevState) => ({ ...prevState, selectedContributionIndex: index }));
    };

    const handleRightContributionSelection = (index: number) => {
      setRightPanelState((prevState) => ({ ...prevState, selectedContributionIndex: index }));
    };

    const toggleLeftPanelOpen = () => {
      setLeftPanelState((prevState) => ({ ...prevState, isOpen: !prevState.isOpen }));
    };

    const toggleRightPanelOpen = () => {
      setRightPanelState((prevState) => ({ ...prevState, isOpen: !prevState.isOpen }));
    };

    return (
      <div style={{ display: 'flex' }}>
        <Sidebar
          side="left"
          panelRef={leftRef}
          contributions={leftContributions}
          selectedContributionIndex={leftPanelState.selectedContributionIndex}
          onContributionSelected={handleLeftContributionSelection}
        />
        <PanelGroup direction="horizontal">
          <Panel
            id="left"
            className={classes.panel}
            defaultSize={leftPanelInitialSize}
            collapsible
            collapsedSize={0}
            minSize={10}
            onExpand={toggleLeftPanelOpen}
            onCollapse={toggleLeftPanelOpen}
            ref={leftRef}>
            {leftContribution !== null && leftPanelState.isOpen ? (
              <WorkbenchPart
                editingContextId={editingContextId}
                readOnly={readOnly}
                side="left"
                contribution={leftContribution}
                initialConfiguration={
                  leftPanelConfiguration?.views.find(
                    (configuration) => configuration && configuration.id === leftContribution.id
                  ) ?? null
                }
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
            onExpand={toggleRightPanelOpen}
            onCollapse={toggleRightPanelOpen}
            ref={rightRef}>
            {rightContribution !== null && rightPanelState.isOpen ? (
              <WorkbenchPart
                editingContextId={editingContextId}
                readOnly={readOnly}
                side="right"
                contribution={rightContribution}
                initialConfiguration={
                  rightPanelConfiguration?.views.find(
                    (configuration) => configuration && configuration.id === rightContribution.id
                  ) ?? null
                }
              />
            ) : null}
          </Panel>
        </PanelGroup>
        <Sidebar
          contributions={rightContributions}
          side="right"
          panelRef={rightRef}
          selectedContributionIndex={rightPanelState.selectedContributionIndex}
          onContributionSelected={handleRightContributionSelection}
        />
      </div>
    );
  }
);
