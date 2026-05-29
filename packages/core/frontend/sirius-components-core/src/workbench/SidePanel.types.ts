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

import { WorkbenchSidePanelConfiguration, WorkbenchViewContribution } from './Workbench.types';

export interface SidePanelProps {
  editingContextId: string;
  readOnly: boolean;
  contributions: WorkbenchViewContribution[];
  panelConfiguration: WorkbenchSidePanelConfiguration | null;
  side: 'left' | 'right';
  initialSize: number;
}

export interface SidePanelState {
  selectedContributionIds: string[];
  isOpen: boolean;
}
