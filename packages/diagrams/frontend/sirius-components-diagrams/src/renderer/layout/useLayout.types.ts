/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Root } from 'react-dom/client';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { RawDiagram } from './layout.types';
import { GQLArrangeLayoutDirection } from '../../representation/DiagramRepresentation.types';

export interface UseLayoutValue {
  layout: (
    previousLaidoutDiagram: RawDiagram | null,
    diagramToLayout: RawDiagram,
    referencePosition: GQLReferencePosition | null,
    layoutDirection: GQLArrangeLayoutDirection,
    callback: (laidoutDiagram: RawDiagram) => void
  ) => void;
}

export type Step = 'INITIAL_STEP' | 'BEFORE_LAYOUT' | 'LAYOUT' | 'AFTER_LAYOUT';

export interface UseLayoutState {
  hiddenContainer: HTMLDivElement | null;
  root: Root | null;
  currentStep: Step;
  previousDiagram: RawDiagram | null;
  diagramToLayout: RawDiagram | null;
  laidoutDiagram: RawDiagram | null;
  referencePosition: GQLReferencePosition | null;
  layoutDirection: GQLArrangeLayoutDirection;
  onLaidoutDiagram: (laidoutDiagram: RawDiagram) => void;
}
