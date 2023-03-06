/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo and others.
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
import { GQLDiagram, Subscriber } from '../representation/DiagramRepresentation.types';

export interface ToolbarProps {
  editingContextId: string;
  representationId: string;
  diagram: GQLDiagram;
  onZoomIn: () => void;
  onZoomOut: () => void;
  onFitToScreen: () => void;
  onArrangeAll: () => void;
  setZoomLevel: (zoomLevel: string) => void;
  autoLayout: boolean;
  zoomLevel: string;
  readOnly: boolean;
  subscribers: Subscriber[];
}

export interface GQLHideDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  hide: boolean;
}

export interface GQLHideDiagramElementVariables {
  input: GQLHideDiagramElementInput;
}

export interface GQLHideDiagramElementPayload {
  __typename: string;
}
export interface GQLHideDiagramElementData {
  hideDiagramElement: GQLHideDiagramElementPayload;
}

export interface GQLFadeDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  fade: boolean;
}

export interface GQLFadeDiagramElementVariables {
  input: GQLFadeDiagramElementInput;
}

export interface GQLFadeDiagramElementPayload {
  __typename: string;
}
export interface GQLFadeDiagramElementData {
  fadeDiagramElement: GQLFadeDiagramElementPayload;
}

export interface GQLErrorPayload extends GQLFadeDiagramElementPayload, GQLHideDiagramElementPayload {
  message: string;
}
