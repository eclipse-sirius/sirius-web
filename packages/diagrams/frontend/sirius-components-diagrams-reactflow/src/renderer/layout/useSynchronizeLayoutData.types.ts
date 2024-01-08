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

import { GQLMessage } from '../Tool.types';
import { RawDiagram } from './layout.types';

export interface UseSynchronizeLayoutDataValue {
  synchronizeLayoutData: (id: string, diagram: RawDiagram) => void;
}

export interface GQLDiagramLayoutData {
  nodeLayoutData: GQLNodeLayoutData[];
}

export interface GQLNodeLayoutData {
  id: string;
  position: GQLPosition;
  size: GQLSize;
  resizedByUser: boolean;
}

export interface GQLSize {
  width: number;
  height: number;
}

export interface GQLPosition {
  x: number;
  y: number;
}

export interface GQLLayoutDiagramData {
  layoutDiagram: GQLLayoutDiagramPayload;
}

export interface GQLLayoutDiagramPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLLayoutDiagramPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLLayoutDiagramPayload {
  messages: GQLMessage[];
}

export interface GQLLayoutDiagramVariables {
  input: GQLLayoutDiagramInput;
}

export interface GQLLayoutDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramLayoutData: GQLDiagramLayoutData;
}
