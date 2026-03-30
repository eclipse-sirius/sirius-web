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
import React, { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, Separator, usePanelRef } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelCollapseContextProvider } from './PanelCollapseContext';
import { SidePanelHandle, SidePanelProps, SidePanelState } from './SidePanel.types';
import { WorkbenchViewContribution, WorkbenchViewHandle } from './Workbench.types';
import { WorkbenchPart } from './WorkbenchPart';

const useSidePanelStyles = makeStyles()((theme) => ({
  panel: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: 'minmax(0, 1fr)',
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

export const SidePanel = forwardRef<SidePanelHandle, SidePanelProps>(
  (
    {
      side,
      editingContextId,
      readOnly,
      contributions,
      panelConfiguration,
      panelInitialSize,
      viewPanelRefs,
      onSelectedContributionIdsChange,
    }: SidePanelProps,
    ref: ForwardedRef<SidePanelHandle>
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

    const { classes } = useSidePanelStyles();
    const theme = useTheme();
    const workbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const panelRef = usePanelRef();
    const [panelState, setPanelState] = useState<SidePanelState>(initialState);

    const spacerId = `${side}Spacer`;
    const collapsedSize: number = 0;
    const collapsedPanelSize: string = theme.spacing(3.5);

    const selectedContributions: WorkbenchViewContribution[] = contributions.filter((contribution) =>
      panelState.selectedContributionIds.includes(contribution.id)
    );

    useImperativeHandle(
      ref,
      () => ({
        getPanelState: () => panelState,
        getWorkbenchViewHandles: () =>
          Array.from(workbenchViewRef.current.values()).filter((handle) =>
            panelState.selectedContributionIds.includes(handle.id)
          ),
        getSelectedContributionIds: () => panelState.selectedContributionIds,
        handleContributionClicked: (id: string) => handleContributionClicked(id),
      }),
      [panelState, contributions]
    );

    useEffect(() => {
      onSelectedContributionIdsChange(panelState.selectedContributionIds);
    }, [panelState.selectedContributionIds]);

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
          return {
            ...prevState,
            selectedContributionIds: [...prevState.selectedContributionIds, id],
          };
        }
      });
    };

    const togglePanel = (isOpen: boolean) => {
      setPanelState((prevState) => ({ ...prevState, isOpen }));
    };

    const handleCollapseChange = (id: string, collapsed: boolean) => {
      if (collapsed) {
        setPanelState((prevState) => ({
          ...prevState,
          collapsedContributionIds: [...prevState.collapsedContributionIds, id],
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
      const panelIndex = panelState.selectedContributionIds.indexOf(panelId);

      if (panelIndex >= panelState.selectedContributionIds.length - 1) {
        return false;
      }

      const hasExpandedPanelBefore = panelState.selectedContributionIds
        .slice(0, panelIndex + 1)
        .some((id) => !panelState.collapsedContributionIds.includes(id));

      const hasExpandedPanelAfter = panelState.selectedContributionIds
        .slice(panelIndex + 1)
        .some((id) => !panelState.collapsedContributionIds.includes(id));

      return hasExpandedPanelBefore && hasExpandedPanelAfter;
    };

    const changeCollapsePanelState = () => {
      const collapsedCount = panelState.collapsedContributionIds.length;
      const selectedCount = panelState.selectedContributionIds.length;
      const expandedCount = selectedCount - collapsedCount;
      const expandedSize = expandedCount > 0 ? 100 / expandedCount : 0;
      const spacerSize = expandedCount === 0 ? '100%' : '0%';
      panelState.selectedContributionIds.forEach((contributionId) => {
        const collapsed = panelState.collapsedContributionIds.includes(contributionId);
        const targetSize = collapsed ? collapsedPanelSize : `${expandedSize}%`;
        viewPanelRefs.current.get(contributionId)?.resize(targetSize);
      });
      viewPanelRefs.current.get(spacerId)?.resize(spacerSize);
    };

    useEffect(() => {
      changeCollapsePanelState();
    }, [panelState.collapsedContributionIds]);

    return (
      <PanelCollapseContextProvider onCollapseChange={handleCollapseChange}>
        <Panel
          id={side}
          className={classes.panel}
          defaultSize={panelState.isOpen ? `${panelInitialSize}%` : `${collapsedSize}%`}
          collapsible
          collapsedSize={`${collapsedSize}%`}
          minSize="10%"
          onResize={(size, prevSize) => {
            if (prevSize !== undefined) {
              const wasOpen = prevSize !== 0;
              const isOpen = size.asPercentage !== 0;
              if (isOpen !== wasOpen) {
                togglePanel(isOpen);
              }
            }
          }}
          panelRef={panelRef}>
          {panelState.isOpen ? (
            <Group orientation="vertical">
              {selectedContributions.map((contribution) => (
                <React.Fragment key={contribution.id}>
                  <Panel
                    id={contribution.id}
                    className={classes.panel}
                    collapsedSize={collapsedPanelSize}
                    minSize={panelState.collapsedContributionIds.includes(contribution.id) ? collapsedPanelSize : '10%'}
                    maxSize={
                      panelState.collapsedContributionIds.includes(contribution.id) ? collapsedPanelSize : '100%'
                    }
                    disabled={panelState.collapsedContributionIds.includes(contribution.id)}
                    panelRef={(panelHandle) => {
                      if (panelHandle) {
                        viewPanelRefs.current.set(contribution.id, panelHandle);
                      }
                      return () => {
                        viewPanelRefs.current.delete(contribution.id);
                      };
                    }}>
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
                </React.Fragment>
              ))}
              <Panel id={spacerId} key={spacerId} minSize="0%" defaultSize="0%" maxSize="100%" disabled />
            </Group>
          ) : null}
        </Panel>
      </PanelCollapseContextProvider>
    );
  }
);
