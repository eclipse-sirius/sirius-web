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
      isOpen: leftPanelConfiguration?.isOpen ?? true,
    };
    const rightInitialState: PanelState = {
      selectedContributionIndex: indexOfRightActivePanelContribution !== -1 ? indexOfRightActivePanelContribution : 0,
      isOpen: rightPanelConfiguration?.isOpen ?? true,
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
            const leftViewConfigurations: WorkbenchViewConfiguration[] = leftContributions.map((contribution) => {
              return {
                ...contribution.ref?.current?.getWorkbenchViewConfiguration(),
                id: contribution.id,
                isActive: contribution.id === leftContribution?.id,
              };
            });
            const rightViewConfigurations: WorkbenchViewConfiguration[] = rightContributions.map((contribution) => {
              return {
                ...contribution.ref?.current?.getWorkbenchViewConfiguration(),
                id: contribution.id,
                isActive: contribution.id === rightContribution?.id,
              };
            });
            return [
              { id: 'left', isOpen: leftPanelState?.isOpen, views: leftViewConfigurations },
              { id: 'right', isOpen: rightPanelState?.isOpen, views: rightViewConfigurations },
            ];
          },
        };
      },
      [leftContribution, leftPanelState, rightContribution, rightPanelState]
    );

    const handleLeftContributionSelection = (index: number) => {
      setLeftPanelState((prevState) => ({ ...prevState, selectedContributionIndex: index }));
    };

    const handleRightContributionSelection = (index: number) => {
      setRightPanelState((prevState) => ({ ...prevState, selectedContributionIndex: index }));
    };

    const toggleLeftPanel = (isOpen: boolean) => {
      setLeftPanelState((prevState) => ({ ...prevState, isOpen }));
    };

    const toggleRightPanel = (isOpen: boolean) => {
      setRightPanelState((prevState) => ({ ...prevState, isOpen }));
    };

    const collapsedSize: number = 0;

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
            defaultSize={leftPanelState.isOpen ? leftPanelInitialSize : collapsedSize}
            collapsible
            collapsedSize={collapsedSize}
            minSize={10}
            onExpand={() => toggleLeftPanel(true)}
            onCollapse={() => toggleLeftPanel(false)}
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
            defaultSize={rightPanelState.isOpen ? rightPanelInitialSize : collapsedSize}
            collapsible
            collapsedSize={collapsedSize}
            minSize={10}
            onExpand={() => toggleRightPanel(true)}
            onCollapse={() => toggleRightPanel(false)}
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
