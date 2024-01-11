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

import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { RawDiagram } from './layout.types';

export interface UseLayoutValue {
  layout: (
    previousLaidoutDiagram: RawDiagram | null,
    diagramToLayout: RawDiagram,
    referencePosition: GQLReferencePosition | null,
    callback: (laidoutDiagram: RawDiagram) => void
  ) => void;
}

export type Step = 'INITIAL_STEP' | 'BEFORE_LAYOUT' | 'LAYOUT' | 'AFTER_LAYOUT';

export interface UseLayoutState {
  hiddenContainer: HTMLDivElement | null;
  currentStep: Step;
  previousDiagram: RawDiagram | null;
  diagramToLayout: RawDiagram | null;
  laidoutDiagram: RawDiagram | null;
  referencePosition: GQLReferencePosition | null;
  onLaidoutDiagram: (laidoutDiagram: RawDiagram) => void;
}
