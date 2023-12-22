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

import { NodeTypeContextValue } from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { DiagramRepresentationConfigurationProps } from './DiagramRepresentationConfiguration.types';

export const defaultNodeTypeRegistry: NodeTypeContextValue = {
  nodeConverters: [],
  nodeLayoutHandlers: [],
  graphQLNodeStyleFragments: [],
  nodeTypeContributions: [],
};

export const DiagramRepresentationConfiguration = ({}: DiagramRepresentationConfigurationProps) => {
  return null;
};
