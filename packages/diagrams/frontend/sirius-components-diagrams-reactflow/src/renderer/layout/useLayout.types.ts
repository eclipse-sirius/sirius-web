/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Edge, Node } from 'reactflow';
import { Diagram } from '../DiagramRenderer.types';

export interface UseLayoutValue {
  layout: (
    previousLaidoutDiagram: Diagram | null,
    diagramToLayout: Diagram,
    callback: (laidoutDiagram: Diagram) => void
  ) => void;
  autoLayout: (nodes: Node[], edges: Edge[]) => Promise<{ nodes: Node[] }>;
}

export type Step = 'INITIAL_STEP' | 'BEFORE_LAYOUT' | 'LAYOUT' | 'AFTER_LAYOUT';

export interface UseLayoutState {
  hiddenContainer: HTMLDivElement | null;
  currentStep: Step;
  diagramToLayout: Diagram | null;
  laidoutDiagram: Diagram | null;
  onLaidoutDiagram: ((laidoutDiagram: Diagram) => void) | null;
}
