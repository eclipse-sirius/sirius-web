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

import { ComponentType } from 'react';
import { EdgeProps } from 'reactflow';

export type DiagramEdgeType = 'smartStepEdge' | 'smoothStepEdge';

export type DiagramEdgeTypes = {
  [key in DiagramEdgeType]: ComponentType<EdgeProps>;
};
