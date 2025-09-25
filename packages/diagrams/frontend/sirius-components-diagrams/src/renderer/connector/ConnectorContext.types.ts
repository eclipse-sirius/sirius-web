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

import { Connection, XYPosition } from '@xyflow/react';
import { GQLConnectorTool } from './useConnector.types';

export interface ConnectorContextValue {
  connection: Connection | null;
  position: XYPosition | null;
  toolCandidates: GQLConnectorTool[];
  setConnection: (connection: Connection | null) => void;
  setPosition: (position: XYPosition) => void;
  setToolCandidates: (toolCandidates: GQLConnectorTool[]) => void;
}

export interface ConnectorContextProviderProps {
  children: React.ReactNode;
}

export interface ConnectorContextProviderState {
  connection: Connection | null;
  position: XYPosition | null;
  toolCandidates: GQLConnectorTool[];
}
