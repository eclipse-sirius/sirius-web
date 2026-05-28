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
import { ForwardedRef, forwardRef, Fragment, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, Separator, usePanelRef } from 'react-resizable-panels';
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
    width: `1px`,
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderRightStyle: 'solid',
    borderRightWidth: '1px',
  },
  horizontalResizer: {
    display: 'grid',
    height: `1px`,
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderBottomStyle: 'solid',
    borderBottomWidth: '1px',
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
    const leftRef = usePanelRef();
    const rightRef = usePanelRef();
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
          if (!leftRef.current.isCollapsed() && leftPanelState.selectedContributionIds.length === 1) {
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
          if (!rightRef.current.isCollapsed() && rightPanelState.selectedContributionIds.length === 1) {
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
      setLeftPanelState((prevState) => (prevState.isOpen === isOpen ? prevState : { ...prevState, isOpen }));
    };

    const toggleRightPanel = (isOpen: boolean) => {
      setRightPanelState((prevState) => (prevState.isOpen === isOpen ? prevState : { ...prevState, isOpen }));
    };

    const collapsedSize: string = '0%';

    return (
      <div style={{ display: 'flex' }}>
        <Sidebar
          side="left"
          contributions={leftContributions}
          selectedContributionIds={leftPanelState.selectedContributionIds}
          onContributionClick={handleLeftContributionClicked}
        />
        <Group orientation="horizontal" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }}>
          <Panel
            id="left"
            className={classes.panel}
            defaultSize={`${leftPanelInitialSize}%`}
            collapsible
            collapsedSize={collapsedSize}
            minSize="10%"
            onResize={(_panelSize, _id, prevPanelSize) => {
              if (prevPanelSize === undefined) {
                if (!leftInitialState.isOpen) {
                  leftRef.current?.collapse();
                }
              } else {
                toggleLeftPanel(!(leftRef.current?.isCollapsed() ?? false));
              }
            }}
            panelRef={leftRef}>
            {leftPanelState.isOpen ? (
              <Group orientation="vertical" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }}>
                {leftSelectedContributions.map((leftContribution, index) => (
                  <Fragment key={leftContribution.id}>
                    <Panel id={`view-${leftContribution.id}`} className={classes.panel} minSize="10%">
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
                      <Separator className={classes.horizontalResizer} data-testid="view-resizer" />
                    ) : null}
                  </Fragment>
                ))}
              </Group>
            ) : null}
          </Panel>
          <Separator className={classes.verticalResizer} data-testid="left-resizer" />
          <Panel id="mainArea" minSize="30%">
            <div className={classes.mainArea}>{mainArea}</div>
          </Panel>
          <Separator className={classes.verticalResizer} data-testid="right-resizer" />
          <Panel
            id="right"
            className={classes.panel}
            defaultSize={`${rightPanelInitialSize}%`}
            collapsible
            collapsedSize={collapsedSize}
            minSize="10%"
            onResize={(_panelSize, _id, prevPanelSize) => {
              if (prevPanelSize === undefined) {
                if (!rightInitialState.isOpen) {
                  rightRef.current?.collapse();
                }
              } else {
                toggleRightPanel(!(rightRef.current?.isCollapsed() ?? false));
              }
            }}
            panelRef={rightRef}>
            {rightPanelState.isOpen ? (
              <Group orientation="vertical" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }}>
                {rightSelectedContributions.map((rightContribution, index) => (
                  <Fragment key={rightContribution.id}>
                    <Panel id={`view-${rightContribution.id}`} className={classes.panel} minSize="10%">
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
                      <Separator className={classes.horizontalResizer} data-testid="view-resizer" />
                    ) : null}
                  </Fragment>
                ))}
              </Group>
            ) : null}
          </Panel>
        </Group>
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
