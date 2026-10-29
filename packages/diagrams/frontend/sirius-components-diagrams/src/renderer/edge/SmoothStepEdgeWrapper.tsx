/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { memo } from 'react';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

export const SmoothStepEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
  const geometry = props.data?.rectilinearGeometry;
  if (!geometry) {
    return null;
  }
  return <MultiLabelRectilinearEditableEdge {...props} geometry={geometry} />;
});
