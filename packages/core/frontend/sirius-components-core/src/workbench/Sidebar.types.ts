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
import { RefObject } from 'react';
import { ImperativePanelHandle } from 'react-resizable-panels';
import { WorkbenchViewContribution, WorkbenchViewSide } from './Workbench.types';

export interface SidebarProps {
  side: WorkbenchViewSide;
  panelRef: RefObject<ImperativePanelHandle>;
  contributions: WorkbenchViewContribution[];
  selectedContributionIndex: number;
  onContributionSelected: (index) => void;
}
