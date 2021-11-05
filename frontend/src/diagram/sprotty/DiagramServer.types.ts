/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { GQLDiagram, Tool } from 'diagram/DiagramWebSocketContainer.types';
import { Action } from 'sprotty';
import { Selection } from 'workbench/Workbench.types';

export interface SiriusUpdateModelAction extends Action {
  kind: 'siriusUpdateModel';
  diagram: GQLDiagram;
  readOnly: boolean;
}

export interface SetActiveToolAction extends Action {
  kind: 'activeTool';
  tool: Tool;
}

export interface SiriusSelectAction extends Action {
  kind: 'siriusSelectElement';
  selection: Selection;
}

export interface ZoomToAction extends Action {
  kind: 'zoomTo';
  level: string;
}

export interface SourceElementaction extends Action {
  kind: 'sourceElement';
  element: any;
}
