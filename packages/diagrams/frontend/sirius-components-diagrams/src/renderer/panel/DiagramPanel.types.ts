/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { DiagramEdgeType } from '../edge/EdgeTypes.types';

export interface DiagramPanelProps {
  snapToGrid: boolean;
  onSnapToGrid: (snapToGrid: boolean) => void;
  helperLines: boolean;
  onHelperLines: (helperLines: boolean) => void;
  reactFlowWrapper: React.MutableRefObject<HTMLDivElement | null>;
  edgeType: DiagramEdgeType;
  onEdgeType: (edgeType: DiagramEdgeType) => void;
}

export interface DiagramPanelState {
  dialogOpen: DiagramPanelDialog | null;
  arrangeAllDone: boolean;
  arrangeAllInProgress: boolean;
}

export type DiagramPanelDialog = 'Share';

export interface DiagramPanelActionProps {
  editingContextId: string;
  diagramId: string;
}
