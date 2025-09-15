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

import { Selection } from '@eclipse-sirius/sirius-components-core';

export interface UseApplySelectionValue {
  /**
   * Applies the given selection to the diagram, selecting the nodes and edges
   * corresponding to the selected semantic elements.
   * If fitSelection is true, the view will adjust to fit the newly selected elements.
   *
   * @param selection the selection to apply (e.g. from the workbench or another view)
   * @param fitSelection whether to adjust the view to fit the selected elements
   */
  applySelection: (selection: Selection, fitSelection: boolean) => void;
}
