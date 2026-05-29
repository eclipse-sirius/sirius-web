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

import { ForwardedRef, forwardRef, Fragment, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, Separator, usePanelRef } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
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
      isOpen: panelConfiguration?.isOpen ?? false,
    };

    const { classes } = usePanelStyles();
    const workbenchViewRef = useRef<Map<string, WorkbenchViewHandle>>(new Map());
    const panelRef = usePanelRef();
    const [panelState, setPanelState] = useState<SidePanelState>(initialState);
    const selectedContributions: WorkbenchViewContribution[] = contributions.filter((contribution) =>
      panelState.selectedContributionIds.includes(contribution.id)
    );

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
      setPanelState((prevState) => (prevState.isOpen === isOpen ? prevState : { ...prevState, isOpen }));
    };

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
            <Group orientation="vertical" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }}>
              {selectedContributions.map((contribution, index) => (
                <Fragment key={contribution.id}>
                  <Panel id={`view-${contribution.id}`} className={classes.panel} minSize="10%">
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
                  {index < selectedContributions.length - 1 ? (
                    <Separator className={classes.horizontalResizer} data-testid="view-resizer" />
                  ) : null}
                </Fragment>
              ))}
            </Group>
          ) : null}
        </Panel>
        {side === 'right' ? sidebar : null}
      </>
    );
  }
);
