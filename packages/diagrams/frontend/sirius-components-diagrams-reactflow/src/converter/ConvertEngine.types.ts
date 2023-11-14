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
import { Node } from 'reactflow';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLNode, GQLNodeStyle } from '../graphql/subscription/nodeFragment.types';

export interface IConvertEngine {
  convertNodes(
    gqlNodesToConvert: GQLNode<GQLNodeStyle>[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    nodes: Node[],
    nodeDescriptions: GQLNodeDescription[]
  ): void;
}

export interface INodeConverterHandler {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>): boolean;

  handle(
    convertEngine: IConvertEngine,
    gqlNode: GQLNode<GQLNodeStyle>,
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    nodeDescriptions: GQLNodeDescription[]
  ): void;
}
