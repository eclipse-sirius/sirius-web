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
import React, { useContext, useMemo } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { nodeTypes as initialNodeType } from './NodeTypes';
import { UseNodeTypeValue } from './useNodeType.types';

export const useNodeType = (): UseNodeTypeValue => {
  const { nodeTypeContributions } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const nodeTypeMap = nodeTypeContributions.reduce((map, contrib) => {
    map[contrib.props.type] = React.memo(contrib.props.component);
    return map;
  }, {});

  const contributedNodeTypes = useMemo(() => {
    return nodeTypeMap;
  }, []);

  const nodeTypes = useMemo(() => {
    return { ...initialNodeType, ...contributedNodeTypes };
  }, [contributedNodeTypes]);

  return { getNodeTypes: () => nodeTypes };
};
