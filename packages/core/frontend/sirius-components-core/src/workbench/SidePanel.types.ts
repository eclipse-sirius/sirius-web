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

import { MutableRefObject } from 'react';
import { PanelImperativeHandle } from 'react-resizable-panels';
import {
  WorkbenchSidePanelConfiguration,
  WorkbenchViewContribution,
  WorkbenchViewHandle,
  WorkbenchViewSide,
} from './Workbench.types';

export interface SidePanelProps {
  side: WorkbenchViewSide;
  editingContextId: string;
  readOnly: boolean;
  contributions: WorkbenchViewContribution[];
  panelConfiguration: WorkbenchSidePanelConfiguration | null;
  panelInitialSize: number;
  viewPanelRefs: MutableRefObject<Map<string, PanelImperativeHandle>>;
  onSelectedContributionIdsChange: (ids: string[]) => void;
}

export interface SidePanelHandle {
  getPanelState: () => SidePanelState;
  getWorkbenchViewHandles: () => WorkbenchViewHandle[];
  getSelectedContributionIds: () => string[];
  handleContributionClicked: (id: string) => void;
}

export type SidePanelState = {
  selectedContributionIds: string[];
  collapsedContributionIds: string[];
  isOpen: boolean;
};
