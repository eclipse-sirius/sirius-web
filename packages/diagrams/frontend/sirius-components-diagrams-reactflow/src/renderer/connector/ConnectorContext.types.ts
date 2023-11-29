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

import { XYPosition } from 'reactflow';
import { GQLNodeDescription } from './useConnector.types';

export interface ConnectorContextValue {
  position: XYPosition | null;
  candidates: GQLNodeDescription[];
  isNewConnection: boolean;
  isFrozen: boolean;
  setPosition: (position: XYPosition) => void;
  setCandidates: (candidates: GQLNodeDescription[]) => void;
  setIsNewConnection: (isNewConnection: boolean) => void;
  resetConnection: () => void;
  setFrozen: (isFrozen: boolean) => void;
}

export interface ConnectorContextProviderProps {
  children: React.ReactNode;
}

export interface ConnectorContextProviderState {
  position: XYPosition | null;
  candidates: GQLNodeDescription[];
  isNewConnection: boolean;
  isFrozen: boolean;
}
