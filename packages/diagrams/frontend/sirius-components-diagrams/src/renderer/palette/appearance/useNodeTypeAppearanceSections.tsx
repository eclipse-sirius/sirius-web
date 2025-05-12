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
import React, { NamedExoticComponent, useContext } from 'react';
import { NodeTypeContext } from '../../../contexts/NodeContext';
import { NodeTypeAppearanceSectionProps, NodeTypeContextValue } from '../../../contexts/NodeContext.types';
import { useNodeTypeAppearanceSectionsValue } from './useNodeTypeAppearanceSections.types';

export const useNodeTypeAppearanceSections = (): useNodeTypeAppearanceSectionsValue => {
  const { nodeTypeAppearanceSectionContributions } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const nodeTypeAppearanceSectionMap: Record<
    string,
    NamedExoticComponent<NodeTypeAppearanceSectionProps>
  > = nodeTypeAppearanceSectionContributions.reduce((map, contrib) => {
    map[contrib.props.type] = React.memo(contrib.props.component);
    return map;
  }, {});

  return { nodeTypeAppearanceSectionMap };
};
