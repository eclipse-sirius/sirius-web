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
import { Node } from 'reactflow';

export interface DiagramPanelProps {
  snapToGrid: boolean;
  onSnapToGrid: (snapToGrid: boolean) => void;
  helperLines: boolean;
  onHelperLines: (helperLines: boolean) => void;
  nodeOverlap: boolean;
  onNodeOverlap: (nodeOverlapping: boolean) => void;
  resolveNodeOverlap: (nodes: Node[], direction: 'horizontal' | 'vertical') => Node[];
}

export interface DiagramPanelState {
  dialogOpen: DiagramPanelDialog | null;
}

export type DiagramPanelDialog = 'Share';
