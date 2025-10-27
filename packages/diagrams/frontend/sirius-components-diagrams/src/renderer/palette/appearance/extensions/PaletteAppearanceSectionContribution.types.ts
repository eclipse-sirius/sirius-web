/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Node, Edge } from '@xyflow/react';
import { NodeData, EdgeData } from '../../../DiagramRenderer.types';

export interface PaletteAppearanceSectionContributionProps {
  canHandle: (nodeElement: Node<NodeData> | undefined, edgeElement: Edge<EdgeData> | undefined) => boolean;
  component: React.ComponentType<PaletteAppearanceSectionContributionComponentProps>;
}

export interface PaletteAppearanceSectionContributionComponentProps {
  elementId: string;
}
