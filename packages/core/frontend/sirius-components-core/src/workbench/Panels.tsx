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
import { ForwardedRef, forwardRef, useCallback, useImperativeHandle, useRef, useState } from 'react';
import { Group, Panel, PanelImperativeHandle, Separator } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelsProps, WorkbenchPanelHandle } from './Panels.types';
import { SidePanel } from './SidePanel';
import { SidePanelHandle } from './SidePanel.types';
import { Sidebar } from './Sidebar';
import { WorkbenchPanelsHandle, WorkbenchViewConfiguration } from './Workbench.types';

const usePanelStyles = makeStyles()((theme) => ({
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
    const { classes } = usePanelStyles();
    const viewPanelRefs = useRef<Map<string, PanelImperativeHandle>>(new Map());
    const leftSidePanelRef = useRef<SidePanelHandle>(null);
    const rightSidePanelRef = useRef<SidePanelHandle>(null);

    const [leftSelectedIds, setLeftSelectedIds] = useState<string[]>([]);
    const [rightSelectedIds, setRightSelectedIds] = useState<string[]>([]);

    const handleLeftSelectedIdsChange = useCallback((ids: string[]) => {
      setLeftSelectedIds(ids);
    }, []);

    const handleRightSelectedIdsChange = useCallback((ids: string[]) => {
      setRightSelectedIds(ids);
    }, []);

    useImperativeHandle(
      refPanelsHandle,
      () => {
        return {
          getWorkbenchPanelConfigurations: () => {
            const leftState = leftSidePanelRef.current?.getPanelState();
            const rightState = rightSidePanelRef.current?.getPanelState();

            const leftViewConfigurations: WorkbenchViewConfiguration[] = leftContributions.map((contribution) => {
              const handles = leftSidePanelRef.current?.getWorkbenchViewHandles() ?? [];
              const handle = handles.find((h) => h.id === contribution.id);
              const data: Record<string, unknown> = handle?.getWorkbenchViewConfiguration() ?? {};
              return {
                id: contribution.id,
                isActive: leftState?.selectedContributionIds.includes(contribution.id) ?? false,
                ...data,
              };
            });
            const rightViewConfigurations: WorkbenchViewConfiguration[] = rightContributions.map((contribution) => {
              const handles = rightSidePanelRef.current?.getWorkbenchViewHandles() ?? [];
              const handle = handles.find((h) => h.id === contribution.id);
              const data: Record<string, unknown> = handle?.getWorkbenchViewConfiguration() ?? {};
              return {
                id: contribution.id,
                isActive: rightState?.selectedContributionIds.includes(contribution.id) ?? false,
                ...data,
              };
            });
            return [
              { id: 'left', isOpen: leftState?.isOpen ?? false, views: leftViewConfigurations },
              { id: 'right', isOpen: rightState?.isOpen ?? false, views: rightViewConfigurations },
            ];
          },
          getWorkbenchPanelHandles: () => {
            const panelHandles: WorkbenchPanelHandle[] = [];
            panelHandles.push({
              side: 'left',
              getWorkbenchViewHandles: () => leftSidePanelRef.current?.getWorkbenchViewHandles() ?? [],
            });
            panelHandles.push({
              side: 'right',
              getWorkbenchViewHandles: () => rightSidePanelRef.current?.getWorkbenchViewHandles() ?? [],
            });
            return panelHandles;
          },
        };
      },
      [leftContributions, rightContributions, leftSelectedIds, rightSelectedIds]
    );

    const handleLeftContributionClicked = (id: string) => {
      leftSidePanelRef.current?.handleContributionClicked(id);
    };

    const handleRightContributionClicked = (id: string) => {
      rightSidePanelRef.current?.handleContributionClicked(id);
    };

    return (
      <div style={{ display: 'flex' }}>
        <Sidebar
          side="left"
          contributions={leftContributions}
          selectedContributionIds={leftSelectedIds}
          onContributionClick={handleLeftContributionClicked}
        />
        <Group orientation="horizontal">
          <SidePanel
            ref={leftSidePanelRef}
            side="left"
            editingContextId={editingContextId}
            readOnly={readOnly}
            contributions={leftContributions}
            panelConfiguration={leftPanelConfiguration}
            panelInitialSize={leftPanelInitialSize}
            viewPanelRefs={viewPanelRefs}
            onSelectedContributionIdsChange={handleLeftSelectedIdsChange}
          />
          <Separator className={classes.verticalResizer} data-testid="left-resizer" />
          <Panel id="mainArea" minSize="30%">
            <div className={classes.mainArea}>{mainArea}</div>
          </Panel>
          <Separator className={classes.verticalResizer} data-testid="right-resizer" />
          <SidePanel
            ref={rightSidePanelRef}
            side="right"
            editingContextId={editingContextId}
            readOnly={readOnly}
            contributions={rightContributions}
            panelConfiguration={rightPanelConfiguration}
            panelInitialSize={rightPanelInitialSize}
            viewPanelRefs={viewPanelRefs}
            onSelectedContributionIdsChange={handleRightSelectedIdsChange}
          />
        </Group>
        <Sidebar
          side="right"
          contributions={rightContributions}
          selectedContributionIds={rightSelectedIds}
          onContributionClick={handleRightContributionClicked}
        />
      </div>
    );
  }
);
