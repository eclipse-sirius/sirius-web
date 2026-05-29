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
import { ForwardedRef, forwardRef, useImperativeHandle, useRef } from 'react';
import { Group, Panel, Separator } from 'react-resizable-panels';
import { makeStyles } from 'tss-react/mui';
import { PanelsProps } from './Panels.types';
import { SidePanel } from './SidePanel';
import { WorkbenchPanelsHandle } from './Workbench.types';

/**
 * React Resizable Panels based panels
 */

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

    const leftSidePanelRef = useRef<WorkbenchPanelsHandle | null>(null);
    const rightSidePanelRef = useRef<WorkbenchPanelsHandle | null>(null);

    useImperativeHandle(
      refPanelsHandle,
      () => {
        return {
          getWorkbenchPanelConfigurations: () => [
            ...(leftSidePanelRef.current?.getWorkbenchPanelConfigurations() ?? []),
            ...(rightSidePanelRef.current?.getWorkbenchPanelConfigurations() ?? []),
          ],
          getWorkbenchPanelHandles: () => [
            ...(leftSidePanelRef.current?.getWorkbenchPanelHandles() ?? []),
            ...(rightSidePanelRef.current?.getWorkbenchPanelHandles() ?? []),
          ],
        };
      },
      []
    );

    return (
      <div style={{ display: 'flex' }}>
        <Group orientation="horizontal" resizeTargetMinimumSize={{ coarse: 15, fine: 5 }}>
          <SidePanel
            ref={leftSidePanelRef}
            editingContextId={editingContextId}
            readOnly={readOnly}
            contributions={leftContributions}
            panelConfiguration={leftPanelConfiguration}
            side="left"
            initialSize={leftPanelInitialSize}
          />
          <Separator className={classes.verticalResizer} data-testid="left-resizer" />
          <Panel id="mainArea" minSize="30%">
            <div className={classes.mainArea}>{mainArea}</div>
          </Panel>
          <Separator className={classes.verticalResizer} data-testid="right-resizer" />
          <SidePanel
            ref={rightSidePanelRef}
            editingContextId={editingContextId}
            readOnly={readOnly}
            contributions={rightContributions}
            panelConfiguration={rightPanelConfiguration}
            side="right"
            initialSize={rightPanelInitialSize}
          />
        </Group>
      </div>
    );
  }
);
