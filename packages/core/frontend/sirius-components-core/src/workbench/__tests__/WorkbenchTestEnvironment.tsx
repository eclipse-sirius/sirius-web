/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { MockedProvider } from '@apollo/client/testing';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { ThemeProvider } from '@mui/material/styles';
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react';
import { ExtensionProvider } from '../../extension/ExtensionProvider';
import { ExtensionRegistry } from '../../extension/ExtensionRegistry';
import { SelectionContextProvider } from '../../selection/SelectionContext';
import { SelectionEntry } from '../../selection/SelectionContext.types';
import { useSelection } from '../../selection/useSelection';
import { theme } from '../../theme';
import { WorkbenchPanelHandle } from '../Panels.types';
import { useWorkbench } from '../useWorkbench';
import {
  MainAreaComponentProps,
  RepresentationComponentProps,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '../Workbench.types';
import {
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from '../WorkbenchExtensionPoints';
import { representationMetadataMock } from './Workbench.data';
import { WorkbenchTestEnvironmentProps } from './WorkbenchTestEnvironment.types';

export const WorkbenchTestEnvironment = ({ initialSelection, children }: WorkbenchTestEnvironmentProps) => {
  const extensionRegistry = new ExtensionRegistry();
  extensionRegistry.addComponent(workbenchMainAreaExtensionPoint, {
    identifier: `${workbenchMainAreaExtensionPoint.identifier}-main-area`,
    Component: MainArea,
  });
  extensionRegistry.putData(workbenchViewContributionExtensionPoint, {
    identifier: `${workbenchViewContributionExtensionPoint.identifier}-test-views`,
    data: [
      {
        id: 'explorer-view',
        title: 'Explorer View',
        side: 'left',
        icon: <ChevronLeftIcon />,
        component: ExplorerView,
      },
      {
        id: 'details-view',
        title: 'Details View',
        side: 'right',
        icon: <ChevronRightIcon />,
        component: DetailsView,
      },
    ],
  });
  extensionRegistry.putData(representationFactoryExtensionPoint, {
    identifier: `${representationFactoryExtensionPoint.identifier}-representation`,
    data: [() => Representation],
  });

  return (
    <MockedProvider mocks={[representationMetadataMock]} addTypename>
      <ThemeProvider theme={theme}>
        <ExtensionProvider registry={extensionRegistry}>
          <SelectionContextProvider initialSelection={initialSelection}>{children}</SelectionContextProvider>
        </ExtensionProvider>
      </ThemeProvider>
    </MockedProvider>
  );
};

const MainArea = ({}: MainAreaComponentProps) => {
  return (
    <div data-testid="main-area">
      <div>Main Area</div>
    </div>
  );
};

const ExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    useImperativeHandle(
      ref,
      () => {
        return {
          id,
          getWorkbenchViewConfiguration: () => {
            return {
              key: 'Value from the explorer',
            };
          },
          applySelection: null,
        };
      },
      []
    );

    const { setSelection } = useSelection();

    const firstRepresentationSelectionEntry: SelectionEntry = {
      id: 'first-representation',
    };
    const secondRepresentationSelectionEntry: SelectionEntry = {
      id: 'second-representation',
    };

    return (
      <div data-testid="explorer-view">
        <div>Explorer View</div>
        <ul>
          <li
            data-testid="explorer-first-representation"
            onClick={() => setSelection({ entries: [firstRepresentationSelectionEntry] })}>
            First representation
          </li>
          <li
            data-testid="explorer-second-representation"
            onClick={() => setSelection({ entries: [secondRepresentationSelectionEntry] })}>
            Second representation
          </li>
        </ul>
      </div>
    );
  }
);

const DetailsView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const [state, setState] = useState<string>('');

    useImperativeHandle(
      ref,
      () => {
        return {
          id,
          getWorkbenchViewConfiguration: () => {
            return {};
          },
          applySelection: null,
        };
      },
      []
    );

    const { getWorkbenchPanelHandles } = useWorkbench();
    const handleRefresh = () => {
      const workbenchPanelHandle: WorkbenchPanelHandle | null =
        getWorkbenchPanelHandles().find((panelHandle) => panelHandle.side === 'left') ?? null;
      if (workbenchPanelHandle) {
        const workbenchViewHandle: WorkbenchViewHandle | null =
          workbenchPanelHandle
            .getWorkbenchViewHandles()
            .find((workbenchViewHandle) => workbenchViewHandle.id === 'explorer-view') ?? null;
        if (workbenchViewHandle) {
          const data = (workbenchViewHandle.getWorkbenchViewConfiguration()['key'] ?? '').toString();
          setState(data);
        }
      }
    };

    return (
      <div data-testid="details-view">
        <div>Details View</div>
        <div data-testid="details-view-content">{state}</div>
        <button data-testid="details-view-refresh" onClick={handleRefresh}>
          Refresh
        </button>
      </div>
    );
  }
);

const Representation = ({ representationId }: RepresentationComponentProps) => {
  return <div data-testid={representationId}>{representationId}</div>;
};
