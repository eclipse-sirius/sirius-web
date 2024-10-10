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

import { HandleProps } from '@xyflow/react';

export interface ConnectionHandlesProps {
  connectionHandles: ConnectionHandle[];
}

export interface ConnectionHandle extends HandleProps {
  nodeId: string;
  index: number;
  hidden: boolean;
}
export interface ConnectionHandlesState {
  selectedHandles: string[];
}
