/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { GQLDiagramToolbar } from '../../representation/DiagramRepresentation.types';

export interface DiagramToolbarProps {
  diagramToolbar: GQLDiagramToolbar;
}

export interface DiagramToolbarState {
  dialogOpen: DiagramToolbarDialog | null;
  arrangeAllDone: boolean;
  arrangeAllInProgress: boolean;
  expanded: boolean;
  contentWidth: number | null;
}

export type DiagramToolbarDialog = 'Share';

export interface DiagramToolbarActionProps {
  editingContextId: string;
  diagramId: string;
}
