/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { ApolloLink, NextLink, Operation } from '@apollo/client';
import { Kind, OperationTypeNode } from 'graphql/language';

// TODO: This class can be removed because looking at the ApolloLink API,
// TODO: it is possible to give the behavior of the request method in the contribuctor of ApolloLink
// TODO: As you can see, it is the only method we are overriding.
// TODO: The purpose of that removal, is to extract all sessionStorage use for undo and redo into a single hook (with maybe a context)
export class ApolloLinkUndoRedoStack extends ApolloLink {
  constructor() {
    super();
    sessionStorage.setItem('undoStack', JSON.stringify([]));
    sessionStorage.setItem('redoStack', JSON.stringify([]));
  }
  override request(operation: Operation, forward: NextLink) {
    if (
      operation.query.definitions[0].kind === Kind.OPERATION_DEFINITION &&
      operation.query.definitions[0].operation === OperationTypeNode.MUTATION &&
      operation.variables.input.id &&
      !(
        operation.operationName === 'undo' ||
        operation.operationName === 'redo' ||
        operation.operationName === 'layoutDiagram' ||
        operation.operationName === 'layoutPortal'
      )
    ) {
      var storedUndoStack = sessionStorage.getItem('undoStack');
      if (!!storedUndoStack) {
        var undoStack = JSON.parse(storedUndoStack);

        sessionStorage.setItem('undoStack', JSON.stringify([operation.variables.input.id, ...undoStack]));
        sessionStorage.setItem('redoStack', JSON.stringify([]));
      }
    }

    return forward(operation);
  }
}
