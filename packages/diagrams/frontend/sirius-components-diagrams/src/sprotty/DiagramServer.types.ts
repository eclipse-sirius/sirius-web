/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { SModelElement } from 'sprotty';
import { Action, Point } from 'sprotty-protocol';
import {
  DiagramDescription,
  GQLDiagram,
  Position,
  SingleClickOnTwoDiagramElementsTool,
  Tool,
} from '../representation/DiagramRepresentation.types';
import { BorderNode, Diagram, Edge, Node } from './Diagram.types';

export interface SiriusUpdateModelAction extends Action {
  kind: 'siriusUpdateModel';
  diagram: GQLDiagram;
  diagramDescription: DiagramDescription;
  readOnly: boolean;
}

export interface SetActiveToolAction extends Action {
  kind: 'activeTool';
  tool: Tool | null;
}

export interface SiriusSelectAction extends Action {
  kind: 'siriusSelectElement';
  selection: Selection;
}

export interface SprottySelectAction extends Action {
  kind: 'sprottySelectElement';
  element: Diagram | Node | BorderNode | Edge | null;
}

export interface ZoomToAction extends Action {
  kind: 'zoomTo';
  level: string;
}

export interface SourceElementAction extends Action {
  kind: 'sourceElement';
  sourceElement: SourceElement | null;
}

export interface SourceElement {
  element: any;
  position: Position;
}

export interface SetActiveConnectorToolsAction extends Action {
  kind: 'activeConnectorTools';
  tools: SingleClickOnTwoDiagramElementsTool[];
}

export interface ShowContextualMenuAction extends Action {
  kind: 'showContextualMenu';
  element: SModelElement | null;
  tools: Tool[];
  startPosition: Position | null;
  endPosition: Position | null;
}

export interface ShowContextualToolbarAction extends Action {
  kind: 'showContextualToolbar';
  element: Diagram | Node | BorderNode | Edge | null;
  position: Point;
}
