/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

export type PanelChoice = 'FIRST_PANEL' | 'SECOND_PANEL';

export interface PanelsProps {
  firstPanel: JSX.Element;
  secondPanel: JSX.Element;
  resizablePanel?: PanelChoice;
  initialResizablePanelSize: number;
}
