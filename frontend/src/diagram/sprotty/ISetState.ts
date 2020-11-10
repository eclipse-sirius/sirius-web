/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { SModelRoot, SModelElement } from 'sprotty';

/**
 * This class stores setState functions that comes from DiagramWebSocketContainer.
 * We use inversify to inject an instance in sprotty handlers.
 * @hmarchadour
 */
export interface ISetState {
  setCurrentRoot?: (currentRoot: SModelRoot) => void;
  setSourceElement?: (sourceElement: SModelElement) => void;
  setContextualPalette?: (contextualPalette: any) => void;
  setActiveTool?: (activeTool: any) => void;
}
