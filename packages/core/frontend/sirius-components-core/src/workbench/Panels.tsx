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
import { useTheme } from '@mui/material/styles';
import React, { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, Separator, usePanelRef } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelCollapseContextProvider } from './PanelCollapseContext';
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
    cursor: 'col-resize',
    backgroundColor: theme.palette.divider,
    borderColor: theme.palette.divider,
    borderRightStyle: 'solid',
    borderRightWidth: '1px',
  },
  horizontalResizer: {
    '&[data-separator="disabled"]': {
      cursor: 'pointer !important',
    },
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
      collapsedContributionIds: [],
      isOpen: leftPanelConfiguration?.isOpen ?? false,
    };
    const rightInitialState: PanelState = {
      selectedContributionIds: rightInitialActiveConfigurationIds,
      collapsedContributionIds: [],
      isOpen: rightPanelConfiguration?.isOpen ?? false,
    };

    const { classes } = usePanelStyles();
    const theme = useTheme();
    const leftWorkbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const rightWorkbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const leftRef = usePanelRef();
    const rightRef = usePanelRef();
    const viewPanelRefs = useRef<Map<string, ReturnType<typeof usePanelRef>['current']>>(new Map());
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
            collapsedContributionIds: prevState.collapsedContributionIds.filter(
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
            collapsedContributionIds: prevState.collapsedContributionIds.filter(
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
    const collapsedPanelPixelSize: string = theme.spacing(3.5);

    const handleCollapseChangeLeft = (id: string, collapsed: boolean) => {
      if (collapsed) {
        setLeftPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: [...prevState.collapsedContributionIds, id],
        }));
      } else {
        setLeftPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: prevState.collapsedContributionIds.filter(
            (contributionId) => contributionId !== id
          ),
        }));
      }
    };

    const handleCollapseChangeRight = (id: string, collapsed: boolean) => {
      if (collapsed) {
        setRightPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: [...prevState.collapsedContributionIds, id],
        }));
      } else {
        setRightPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: prevState.collapsedContributionIds.filter(
            (contributionId) => contributionId !== id
          ),
        }));
      }
    };

    const isSeparatorEnabled = (
      panelId: string,
      selectedContributionIds: string[],
      collapsedContributionIds: string[]
    ): boolean => {
      const panelIndex = selectedContributionIds.indexOf(panelId);

      // Don't show separator after the last panel (it would be between last panel and spacer)
      if (panelIndex >= selectedContributionIds.length - 1) {
        return false;
      }

      // Check if there's at least one expanded panel at or before this one
      const hasExpandedPanelBefore = selectedContributionIds
        .slice(0, panelIndex + 1)
        .some((id) => !collapsedContributionIds.includes(id));

      // Check if there's at least one expanded panel after this one
      const hasExpandedPanelAfter = selectedContributionIds
        .slice(panelIndex + 1)
        .some((id) => !collapsedContributionIds.includes(id));

      return hasExpandedPanelBefore && hasExpandedPanelAfter;
    };

    const changeCollapsePanelState = (
      selectedContributionIds: string[],
      collapsedContributionIds: string[],
      spacerId: string
    ) => {
      const nbCollapsed = collapsedContributionIds.length;
      const nbSelected = selectedContributionIds.length;
      const nbExpanded = nbSelected - nbCollapsed;
      // Expanded panels share the remaining space equally
      const expandedSize = nbExpanded > 0 ? 100 / nbExpanded : 0;
      // Spacer takes all the space if all panels are collapsed
      const spacerSize = nbExpanded === 0 ? '100%' : '0%';
      selectedContributionIds.forEach((contributionId) => {
        const collapsed = collapsedContributionIds.includes(contributionId);
        const targetSize = collapsed ? collapsedPanelPixelSize : `${expandedSize}%`;
        viewPanelRefs.current.get(contributionId)?.resize(targetSize);
      });
      viewPanelRefs.current.get(spacerId)?.resize(spacerSize);
    };

    useEffect(() => {
      changeCollapsePanelState(
        leftPanelState.selectedContributionIds,
        leftPanelState.collapsedContributionIds,
        'leftSpacer'
      );
    }, [leftPanelState.collapsedContributionIds]);

    useEffect(() => {
      changeCollapsePanelState(
        rightPanelState.selectedContributionIds,
        rightPanelState.collapsedContributionIds,
        'rightSpacer'
      );
    }, [rightPanelState.collapsedContributionIds]);

    return (
      <div style={{ display: 'flex' }}>
        <Sidebar
          side="left"
          contributions={leftContributions}
          selectedContributionIds={leftPanelState.selectedContributionIds}
          onContributionClick={handleLeftContributionClicked}
        />
        <Group orientation="horizontal" disableCursor>
          <PanelCollapseContextProvider onCollapseChange={handleCollapseChangeLeft}>
            <Panel
              id="left"
              className={classes.panel}
              defaultSize={leftPanelState.isOpen ? `${leftPanelInitialSize}%` : `${collapsedSize}%`}
              collapsible
              collapsedSize={`${collapsedSize}%`}
              minSize="10%"
              onResize={(size, prevSize) => {
                if (prevSize !== undefined) {
                  const wasCollapsed = prevSize === 0;
                  const isCollapsed = size.asPercentage === 0;
                  if (isCollapsed !== wasCollapsed) {
                    toggleLeftPanel(isCollapsed);
                  }
                }
              }}
              panelRef={leftRef}>
              {leftPanelState.isOpen ? (
                <Group orientation="vertical">
                  {leftSelectedContributions.map((leftContribution) => (
                    <React.Fragment key={leftContribution.id}>
                      <Panel
                        id={leftContribution.id}
                        key={leftContribution.id}
                        className={classes.panel}
                        collapsedSize={collapsedPanelPixelSize}
                        minSize={
                          leftPanelState.collapsedContributionIds.includes(leftContribution.id)
                            ? collapsedPanelPixelSize
                            : '10%'
                        }
                        maxSize={
                          leftPanelState.collapsedContributionIds.includes(leftContribution.id)
                            ? collapsedPanelPixelSize
                            : '100%'
                        }
                        disabled={leftPanelState.collapsedContributionIds.includes(leftContribution.id)}
                        panelRef={(panelHandle) => {
                          if (panelHandle) {
                            viewPanelRefs.current.set(leftContribution.id, panelHandle);
                          }
                          return () => {
                            viewPanelRefs.current.delete(leftContribution.id);
                          };
                        }}>
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
                      <Separator
                        className={classes.horizontalResizer}
                        data-testid="view-resizer"
                        disabled={
                          !isSeparatorEnabled(
                            leftContribution.id,
                            leftPanelState.selectedContributionIds,
                            leftPanelState.collapsedContributionIds
                          )
                        }
                      />
                    </React.Fragment>
                  ))}
                  <Panel id="leftSpacer" key="leftSpacer" minSize="0%" defaultSize="0%" maxSize="100%" disabled />
                </Group>
              ) : null}
            </Panel>
          </PanelCollapseContextProvider>
          <Separator className={classes.verticalResizer} data-testid="left-resizer" />
          <Panel id="mainArea" minSize="30%">
            <div className={classes.mainArea}>{mainArea}</div>
          </Panel>
          <Separator className={classes.verticalResizer} data-testid="right-resizer" />
          <PanelCollapseContextProvider onCollapseChange={handleCollapseChangeRight}>
            <Panel
              id="right"
              className={classes.panel}
              defaultSize={rightPanelState.isOpen ? `${rightPanelInitialSize}%` : `${collapsedSize}%`}
              collapsible
              collapsedSize={`${collapsedSize}%`}
              minSize="10%"
              onResize={(size, prevSize) => {
                if (prevSize !== undefined) {
                  const wasCollapsed = prevSize === 0;
                  const isCollapsed = size.asPercentage === 0;
                  if (isCollapsed !== wasCollapsed) {
                    toggleRightPanel(isCollapsed);
                  }
                }
              }}
              panelRef={rightRef}>
              {rightPanelState.isOpen ? (
                <Group orientation="vertical">
                  {rightSelectedContributions.map((rightContribution) => (
                    <React.Fragment key={rightContribution.id}>
                      <Panel
                        id={rightContribution.id}
                        key={rightContribution.id}
                        className={classes.panel}
                        collapsedSize={collapsedPanelPixelSize}
                        minSize={
                          rightPanelState.collapsedContributionIds.includes(rightContribution.id)
                            ? collapsedPanelPixelSize
                            : '10%'
                        }
                        maxSize={
                          rightPanelState.collapsedContributionIds.includes(rightContribution.id)
                            ? collapsedPanelPixelSize
                            : '100%'
                        }
                        disabled={rightPanelState.collapsedContributionIds.includes(rightContribution.id)}
                        panelRef={(panelHandle) => {
                          if (panelHandle) {
                            viewPanelRefs.current.set(rightContribution.id, panelHandle);
                          }
                          return () => {
                            viewPanelRefs.current.delete(rightContribution.id);
                          };
                        }}>
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
                      <Separator
                        className={classes.horizontalResizer}
                        data-testid="view-resizer"
                        disabled={
                          !isSeparatorEnabled(
                            rightContribution.id,
                            rightPanelState.selectedContributionIds,
                            rightPanelState.collapsedContributionIds
                          )
                        }
                      />
                    </React.Fragment>
                  ))}
                  <Panel id="rightSpacer" key="rightSpacer" minSize="0%" defaultSize="0%" maxSize="100%" disabled />
                </Group>
              ) : null}
            </Panel>
          </PanelCollapseContextProvider>
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
