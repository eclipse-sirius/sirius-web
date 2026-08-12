/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ForwardedRef, forwardRef, Fragment, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, Separator, useGroupRef, usePanelRef } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelCollapseContextProvider } from './PanelCollapseContext';
import { WorkbenchPanelHandle } from './Panels.types';
import { Sidebar } from './Sidebar';
import { SidePanelProps, SidePanelState } from './SidePanel.types';
import {
  WorkbenchPanelsHandle,
  WorkbenchViewConfiguration,
  WorkbenchViewContribution,
  WorkbenchViewHandle,
} from './Workbench.types';
import { WorkbenchPart } from './WorkbenchPart';

const usePanelStyles = makeStyles()((theme) => ({
  panel: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: 'minmax(0, 1fr)',
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

export const SidePanel = forwardRef<WorkbenchPanelsHandle | null, SidePanelProps>(
  (
    { editingContextId, readOnly, contributions, panelConfiguration, side, initialSize }: SidePanelProps,
    refPanelsHandle: ForwardedRef<WorkbenchPanelsHandle | null>
  ) => {
    let initialActiveConfigurationIds: string[] = [];
    if (panelConfiguration) {
      initialActiveConfigurationIds = panelConfiguration.views
        .filter((configuration) => configuration && configuration.isActive)
        .map((configuration) => configuration.id);
    } else if (contributions[0]) {
      initialActiveConfigurationIds = [contributions[0].id];
    }

    const initialState: SidePanelState = {
      selectedContributionIds: initialActiveConfigurationIds,
      collapsedContributionIds: [],
      isOpen: panelConfiguration?.isOpen ?? false,
    };

    const { classes } = usePanelStyles();
    const theme = useTheme();
    const workbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const panelRef = usePanelRef();
    const groupRef = useGroupRef();
    const [panelState, setPanelState] = useState<SidePanelState>(initialState);
    const selectedContributions: WorkbenchViewContribution[] = contributions.filter((contribution) =>
      panelState.selectedContributionIds.includes(contribution.id)
    );
    const spacerId = `${side}-spacer`;
    const viewHeaderHeight: string = theme.spacing(4.5);

    useImperativeHandle(
      refPanelsHandle,
      () => {
        return {
          getWorkbenchPanelConfigurations: () => {
            const viewConfigurations: WorkbenchViewConfiguration[] = contributions.map((contribution) => {
              const data: Record<string, unknown> =
                workbenchViewRef.current.get(contribution.id)?.getWorkbenchViewConfiguration() ?? {};
              return {
                id: contribution.id,
                isActive: panelState.selectedContributionIds.includes(contribution.id),
                ...data,
              };
            });
            return [{ id: side, isOpen: panelState?.isOpen, views: viewConfigurations }];
          },
          getWorkbenchPanelHandles: () => {
            const panelHandles: WorkbenchPanelHandle[] = [];
            panelHandles.push({
              side: side,
              getWorkbenchViewHandles: () =>
                Array.from(workbenchViewRef.current.values()).filter((handle) =>
                  panelState.selectedContributionIds.includes(handle.id)
                ),
            });
            return panelHandles;
          },
        };
      },
      [contributions, selectedContributions, panelState]
    );

    const handleContributionClicked = (id: string) => {
      if (panelRef.current) {
        if (panelState.selectedContributionIds.includes(id)) {
          if (!panelRef.current.isCollapsed() && panelState.selectedContributionIds.length === 1) {
            panelRef.current.collapse();
          }
        } else {
          if (panelRef.current.isCollapsed()) {
            panelRef.current.expand();
          }
        }
      }

      setPanelState((prevState) => {
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
          const nextSelectedContributionIds = [...prevState.selectedContributionIds, id];
          return {
            ...prevState,
            selectedContributionIds: contributions
              .filter((contribution) => nextSelectedContributionIds.includes(contribution.id))
              .map((contribution) => contribution.id),
          };
        }
      });
    };

    const togglePanel = (isOpen: boolean) => {
      setPanelState((prevState) => (prevState.isOpen === isOpen ? prevState : { ...prevState, isOpen }));
    };

    const handleCollapseChange = (id: string, collapsed: boolean) => {
      if (collapsed) {
        setPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: prevState.collapsedContributionIds.includes(id)
            ? prevState.collapsedContributionIds
            : [...prevState.collapsedContributionIds, id],
        }));
      } else {
        setPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: prevState.collapsedContributionIds.filter(
            (contributionId) => contributionId !== id
          ),
        }));
      }
    };

    const isSeparatorEnabled = (panelId: string): boolean => {
      const selectedContributionIds = selectedContributions.map((contribution) => contribution.id);
      const panelIndex = selectedContributionIds.indexOf(panelId);
      if (panelIndex >= selectedContributionIds.length - 1) {
        return false;
      }
      const hasExpandedPanelBefore = selectedContributionIds
        .slice(0, panelIndex + 1)
        .some((id) => !panelState.collapsedContributionIds.includes(id));
      const hasExpandedPanelAfter = selectedContributionIds
        .slice(panelIndex + 1)
        .some((id) => !panelState.collapsedContributionIds.includes(id));
      return hasExpandedPanelBefore && hasExpandedPanelAfter;
    };

    const changeCollapsePanelState = () => {
      // A view's panel constraints (min/max) are applied by the library one frame after its
      // collapsed state changes. Setting the layout on the next frame ensures setLayout validates
      // against the up-to-date constraints. Otherwise it clamps the freshly expanded panel back to
      // its (collapsed) size.
      requestAnimationFrame(() => {
        const group = groupRef.current;
        if (!group) {
          return;
        }
        const currentLayout = group.getLayout();
        const expandedCount = selectedContributions.filter(
          (contribution) => !panelState.collapsedContributionIds.includes(contribution.id)
        ).length;

        const collapsedTotalSize = selectedContributions
          .filter((contribution) => panelState.collapsedContributionIds.includes(contribution.id))
          .reduce((total, contribution) => total + (currentLayout[`view-${contribution.id}`] ?? 0), 0);
        const remainingSize = Math.max(0, 100 - collapsedTotalSize);
        const expandedSize = expandedCount > 0 ? remainingSize / expandedCount : 0;

        // react-resizable-panels reads layout object values by enumeration order, so insert
        // properties in the same order as the panels are rendered
        const nextLayout: Record<string, number> = {};
        selectedContributions.forEach((contribution) => {
          const collapsed = panelState.collapsedContributionIds.includes(contribution.id);
          nextLayout[`view-${contribution.id}`] = collapsed
            ? currentLayout[`view-${contribution.id}`] ?? 0
            : expandedSize;
        });

        nextLayout[spacerId] = expandedCount === 0 ? remainingSize : 0;

        group.setLayout(nextLayout);
      });
    };

    useEffect(() => {
      changeCollapsePanelState();
    }, [panelState.collapsedContributionIds]);

    const expandedContributionCount = selectedContributions.filter(
      (contribution) => !panelState.collapsedContributionIds.includes(contribution.id)
    ).length;
    const expandedContributionDefaultSize =
      expandedContributionCount > 0 ? `${100 / expandedContributionCount}%` : '0%';

    const sidebar = (
      <Sidebar
        side={side}
        contributions={contributions}
        selectedContributionIds={panelState.selectedContributionIds}
        onContributionClick={handleContributionClicked}
      />
    );

    return (
      <>
        {side === 'left' ? sidebar : null}
        <PanelCollapseContextProvider onCollapseChange={handleCollapseChange} viewHeaderHeight={viewHeaderHeight}>
          <Panel
            id={side}
            className={classes.panel}
            defaultSize={`${initialSize}%`}
            collapsible
            collapsedSize={'0%'}
            minSize="10%"
            onResize={(_panelSize, _id, prevPanelSize) => {
              if (prevPanelSize === undefined) {
                if (!initialState.isOpen) {
                  panelRef.current?.collapse();
                }
              } else {
                togglePanel(!(panelRef.current?.isCollapsed() ?? false));
              }
            }}
            panelRef={panelRef}>
            {panelState.isOpen ? (
              <Group orientation="vertical" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }} groupRef={groupRef}>
                {selectedContributions.map((contribution) => {
                  const collapsed = panelState.collapsedContributionIds.includes(contribution.id);
                  return (
                    <Fragment key={contribution.id}>
                      <Panel
                        id={`view-${contribution.id}`}
                        className={classes.panel}
                        collapsedSize={viewHeaderHeight}
                        defaultSize={collapsed ? viewHeaderHeight : expandedContributionDefaultSize}
                        minSize={collapsed ? viewHeaderHeight : '10%'}
                        maxSize={collapsed ? viewHeaderHeight : '100%'}
                        disabled={collapsed}>
                        <WorkbenchPart
                          editingContextId={editingContextId}
                          readOnly={readOnly}
                          side={side}
                          contribution={contribution}
                          initialConfiguration={
                            panelConfiguration?.views.find(
                              (configuration) => configuration && configuration.id === contribution.id
                            ) ?? null
                          }
                          ref={(workbenchViewHandle: WorkbenchViewHandle | null) => {
                            if (workbenchViewHandle) {
                              workbenchViewRef.current.set(contribution.id, workbenchViewHandle);
                            }
                            return () => {
                              workbenchViewRef.current.delete(contribution.id);
                            };
                          }}
                        />
                      </Panel>
                      <Separator
                        className={classes.horizontalResizer}
                        data-testid="view-resizer"
                        disabled={!isSeparatorEnabled(contribution.id)}
                      />
                    </Fragment>
                  );
                })}
                <Panel id={spacerId} key={spacerId} minSize="0%" defaultSize="0%" maxSize="100%" disabled />
              </Group>
            ) : null}
          </Panel>
        </PanelCollapseContextProvider>
        {side === 'right' ? sidebar : null}
      </>
    );
  }
);
