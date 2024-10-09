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

import { Edge, EdgeProps } from '@xyflow/react';
import { FC } from 'react';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { SmartStepEdgeWrapper } from './SmartStepEdgeWrapper';
import { SmoothStepEdgeWrapper } from './SmoothStepEdgeWrapper';

export const edgeTypes: EdgeComponentsMap = {
  smartStepEdge: SmartStepEdgeWrapper,
  smoothStepEdge: SmoothStepEdgeWrapper,
};

export interface EdgeDataMap {
  smartStepEdge: MultiLabelEdgeData;
  smoothStepEdge: MultiLabelEdgeData;
}

export type EdgeComponentsMap = {
  [K in keyof EdgeDataMap]: FC<EdgeProps<Edge<EdgeDataMap[K], K>>>;
};

export type EdgePropsMap = {
  [K in keyof EdgeDataMap]: EdgeProps<Edge<EdgeDataMap[K], K>>;
};
