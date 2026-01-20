/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import { PanelsProps, PanelState, WorkbenchPanelHandle } from './Panels.types';
import { Sidebar } from './Sidebar';
import {
  WorkbenchPanelsHandle,
  WorkbenchViewConfiguration,
  WorkbenchViewContribution,
  WorkbenchViewHandle,
} from './Workbench.types';
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
    width: '3px',
    cursor: 'col-resize',
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderRightStyle: 'solid',
  },
  horizontalResizer: {
    display: 'grid',
    height: '3px',
    cursor: 'row-resize',
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderBottomStyle: 'solid',
  },
}));

export const Panels = forwardRef<WorkbenchPanelsHandle | null, PanelsProps>(
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
    refPanelsHandle: ForwardedRef<WorkbenchPanelsHandle | null>
  ) => {
    disableGlobalCursorStyles();

    let leftInitialActiveConfigurationIds: string[] = [];
    if (leftPanelConfiguration) {
      leftInitialActiveConfigurationIds = leftPanelConfiguration.views
        .filter((configuration) => configuration && configuration.isActive)
        .map((configuration) => configuration.id);
    } else if (leftContributions[0]) {
      leftInitialActiveConfigurationIds = [leftContributions[0].id];
    }

    let rightInitialActiveConfigurationIds: string[] = [];
    if (rightPanelConfiguration) {
      rightInitialActiveConfigurationIds = rightPanelConfiguration.views
        .filter((configuration) => configuration && configuration.isActive)
        .map((configuration) => configuration.id);
    } else if (rightContributions[0]) {
      rightInitialActiveConfigurationIds = [rightContributions[0].id];
    }

    const leftInitialState: PanelState = {
      selectedContributionIds: leftInitialActiveConfigurationIds,
      isOpen: leftPanelConfiguration?.isOpen ?? false,
    };
    const rightInitialState: PanelState = {
      selectedContributionIds: rightInitialActiveConfigurationIds,
      isOpen: rightPanelConfiguration?.isOpen ?? false,
    };

    const { classes } = usePanelStyles();
    const leftWorkbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const rightWorkbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const leftRef = useRef<ImperativePanelHandle>(null);
    const rightRef = useRef<ImperativePanelHandle>(null);
    const [leftPanelState, setLeftPanelState] = useState<PanelState>(leftInitialState);
    const [rightPanelState, setRightPanelState] = useState<PanelState>(rightInitialState);

    const leftSelectedContributions: WorkbenchViewContribution[] = leftContributions.filter((contribution) =>
      leftPanelState.selectedContributionIds.includes(contribution.id)
    );
    const rightSelectedContributions: WorkbenchViewContribution[] = rightContributions.filter((contribution) =>
      rightPanelState.selectedContributionIds.includes(contribution.id)
    );

    useImperativeHandle(
      refPanelsHandle,
      () => {
        return {
          getWorkbenchPanelConfigurations: () => {
            const leftViewConfigurations: WorkbenchViewConfiguration[] = leftContributions.map((contribution) => {
              const data: Record<string, unknown> =
                leftWorkbenchViewRef.current.get(contribution.id)?.getWorkbenchViewConfiguration() ?? {};
              return {
                id: contribution.id,
                isActive: leftPanelState.selectedContributionIds.includes(contribution.id),
                ...data,
              };
            });
            const rightViewConfigurations: WorkbenchViewConfiguration[] = rightContributions.map((contribution) => {
              const data: Record<string, unknown> =
                rightWorkbenchViewRef.current.get(contribution.id)?.getWorkbenchViewConfiguration() ?? {};
              return {
                id: contribution.id,
                isActive: rightPanelState.selectedContributionIds.includes(contribution.id),
                ...data,
              };
            });
            return [
              { id: 'left', isOpen: leftPanelState?.isOpen, views: leftViewConfigurations },
              { id: 'right', isOpen: rightPanelState?.isOpen, views: rightViewConfigurations },
            ];
          },
          getWorkbenchPanelHandles: () => {
            const panelHandles: WorkbenchPanelHandle[] = [];
            panelHandles.push({
              side: 'left',
              getWorkbenchViewHandles: () =>
                Array.from(leftWorkbenchViewRef.current.values()).filter((handle) =>
                  leftPanelState.selectedContributionIds.includes(handle.id)
                ),
            });
            panelHandles.push({
              side: 'right',
              getWorkbenchViewHandles: () =>
                Array.from(rightWorkbenchViewRef.current.values()).filter((handle) =>
                  rightPanelState.selectedContributionIds.includes(handle.id)
                ),
            });
            return panelHandles;
          },
        };
      },
      [
        leftContributions,
        leftSelectedContributions,
        leftPanelState,
        rightContributions,
        rightSelectedContributions,
        rightPanelState,
      ]
    );

    const handleLeftContributionClicked = (id: string) => {
      if (leftRef.current) {
        if (leftPanelState.selectedContributionIds.includes(id)) {
          if (leftRef.current.isExpanded() && leftPanelState.selectedContributionIds.length === 1) {
            leftRef.current.collapse();
          }
        } else {
          if (leftRef.current.isCollapsed()) {
            leftRef.current.expand();
          }
        }
      }

      setLeftPanelState((prevState) => {
        if (prevState.selectedContributionIds.includes(id)) {
          return {
            ...prevState,
            selectedContributionIds: prevState.selectedContributionIds.filter(
              (contributionId) => contributionId !== id
            ),
          };
        } else {
          return {
            ...prevState,
            selectedContributionIds: [...prevState.selectedContributionIds, id],
          };
        }
      });
    };

    const handleRightContributionClicked = (id: string) => {
      if (rightRef.current) {
        if (rightPanelState.selectedContributionIds.includes(id)) {
          if (rightRef.current.isExpanded() && rightPanelState.selectedContributionIds.length === 1) {
            rightRef.current.collapse();
          }
        } else {
          if (rightRef.current.isCollapsed()) {
            rightRef.current.expand();
          }
        }
      }

      setRightPanelState((prevState) => {
        if (prevState.selectedContributionIds.includes(id)) {
          return {
            ...prevState,
            selectedContributionIds: prevState.selectedContributionIds.filter(
              (contributionId) => contributionId !== id
            ),
          };
        } else {
          return {
            ...prevState,
            selectedContributionIds: [...prevState.selectedContributionIds, id],
          };
        }
      });
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
          contributions={leftContributions}
          selectedContributionIds={leftPanelState.selectedContributionIds}
          onContributionClick={handleLeftContributionClicked}
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
            {leftPanelState.isOpen ? (
              <PanelGroup direction="vertical">
                {leftSelectedContributions.map((leftContribution, index) => (
                  <>
                    <Panel
                      id={leftContribution.id}
                      key={leftContribution.id}
                      order={index}
                      className={classes.panel}
                      minSize={10}>
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
                        ref={(workbenchViewHandle: WorkbenchViewHandle | null) => {
                          if (workbenchViewHandle) {
                            leftWorkbenchViewRef.current.set(leftContribution.id, workbenchViewHandle);
                          }
                          return () => {
                            leftWorkbenchViewRef.current.delete(leftContribution.id);
                          };
                        }}
                      />
                    </Panel>
                    {index < leftSelectedContributions.length - 1 ? (
                      <PanelResizeHandle
                        key={`left-resizer-${leftContribution.id}`}
                        className={classes.horizontalResizer}
                        data-testid="view-resizer"
                      />
                    ) : null}
                  </>
                ))}
              </PanelGroup>
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
            {rightPanelState.isOpen ? (
              <PanelGroup direction="vertical">
                {rightSelectedContributions.map((rightContribution, index) => (
                  <>
                    <Panel
                      id={rightContribution.id}
                      key={rightContribution.id}
                      order={index}
                      className={classes.panel}
                      minSize={10}>
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
                        ref={(workbenchViewHandle: WorkbenchViewHandle | null) => {
                          if (workbenchViewHandle) {
                            rightWorkbenchViewRef.current.set(rightContribution.id, workbenchViewHandle);
                          }
                          return () => {
                            rightWorkbenchViewRef.current.delete(rightContribution.id);
                          };
                        }}
                      />
                    </Panel>
                    {index < rightSelectedContributions.length - 1 ? (
                      <PanelResizeHandle
                        key={`right-resizer-${rightContribution.id}`}
                        className={classes.horizontalResizer}
                        data-testid="view-resizer"
                      />
                    ) : null}
                  </>
                ))}
              </PanelGroup>
            ) : null}
          </Panel>
        </PanelGroup>
        <Sidebar
          contributions={rightContributions}
          side="right"
          selectedContributionIds={rightPanelState.selectedContributionIds}
          onContributionClick={handleRightContributionClicked}
        />
      </div>
    );
  }
);
