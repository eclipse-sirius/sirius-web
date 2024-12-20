/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { ApolloLink, FetchResult, NextLink, Observable, Operation } from '@apollo/client';
import { DefinitionNode, Kind, OperationDefinitionNode } from 'graphql/language';

const isOperationDefinitionNode = (definitionNode: DefinitionNode): definitionNode is OperationDefinitionNode =>
  definitionNode.kind === Kind.OPERATION_DEFINITION;

export class ApolloLoggerLink extends ApolloLink {
  override request(operation: Operation, forward: NextLink): Observable<FetchResult> | null {
    const node = operation.query.definitions.find((definitionNode) => isOperationDefinitionNode(definitionNode));

    const operationKind: string = isOperationDefinitionNode(node) ? node.operation : 'unknown kind';
    const operationName: string = isOperationDefinitionNode(node) ? node.name?.value : 'unknwown name';

    console.debug(`${operationKind} ${operationName}: request sent`);

    if ('subscription' === operationKind) {
      return forward(operation);
    }

    return forward(operation).map((fetchResult) => {
      console.debug(`${operationKind} ${operationName}: response received`);
      return fetchResult;
    });
  }
}
