/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node } from 'reactflow';

export interface DiagramRendererProps {
  diagram: Diagram;
  selection: Selection;
  setSelection: (selection: Selection) => void;
}

export interface DiagramRendererState {
  fullscreen: boolean;
  snapToGrid: boolean;
}

export interface Diagram {
  metadata: DiagramMetadata;
  nodes: Node[];
  edges: Edge[];
}

export interface DiagramMetadata {
  id: string;
  kind: string;
  label: string;
}

export interface NodeData {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  label: Label | null;
}

export interface Label {
  id: string;
  text: string;
  style: React.CSSProperties;
}
