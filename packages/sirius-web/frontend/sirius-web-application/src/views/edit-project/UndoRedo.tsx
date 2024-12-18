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

import { gql, useMutation } from '@apollo/client';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { EditProjectViewParams } from './EditProjectView.types';
import {
  GQLRedoData,
  GQLRedoInput,
  GQLSuccessPayload,
  GQLUndoData,
  GQLUndoInput,
  GQLUndoRedoItemPayload,
  GQLUndoVariables,
} from './UndoRedo.types';

const undoMutation = gql`
  mutation undo($input: UndoInput!) {
    undo(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const redoMutation = gql`
  mutation redo($input: RedoInput!) {
    redo(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isSuccessPayload = (payload: GQLUndoRedoItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const UndoRedo = ({ children }: { children: React.ReactNode }) => {
  const [undo, { data: undoData }] = useMutation<GQLUndoData, GQLUndoVariables>(undoMutation);
  const [redo, { data: redoData }] = useMutation<GQLRedoData, GQLUndoVariables>(redoMutation);
  const { projectId } = useParams<EditProjectViewParams>();

  useEffect(() => {
    sessionStorage.setItem('undoStack', JSON.stringify([]));
    sessionStorage.setItem('redoStack', JSON.stringify([]));
  }, []);

  const undoLastAction = () => {
    const storedArray = sessionStorage.getItem('undoStack');
    if (storedArray) {
      const arr: string[] = JSON.parse(storedArray);
      if (arr[0]) {
        const input: GQLUndoInput = {
          id: arr[0],
          editingContextId: projectId,
          mutationId: arr[0],
        };
        undo({ variables: { input } });
      }
    }
  };

  const redoLastAction = () => {
    const storedArray = sessionStorage.getItem('redoStack');
    if (storedArray) {
      const arr: string[] = JSON.parse(storedArray);
      if (arr[0]) {
        const input: GQLRedoInput = {
          id: arr[0],
          editingContextId: projectId,
          mutationId: arr[0],
        };
        redo({ variables: { input } });
      }
    }
  };

  useEffect(() => {
    if (undoData) {
      const { undo } = undoData;
      if (isSuccessPayload(undo)) {
        const storedUndoStack = sessionStorage.getItem('undoStack');
        const storedRedoStack = sessionStorage.getItem('redoStack');

        // Remove first element of undo stack
        const undoStack: string[] = JSON.parse(storedUndoStack);
        const lastElement = undoStack.shift();
        sessionStorage.setItem('undoStack', JSON.stringify(undoStack));

        // Put the element in the 1st position of the redo stack
        const redoStack: string[] = JSON.parse(storedRedoStack);
        if (!redoStack.find((id) => id === lastElement)) {
          sessionStorage.setItem('redoStack', JSON.stringify([lastElement, ...redoStack]));
        }
      } else {
        // Clear stack if there is an error
        sessionStorage.setItem('undoStack', JSON.stringify([]));
        sessionStorage.setItem('redoStack', JSON.stringify([]));
      }
    }
  }, [undoData]);

  useEffect(() => {
    if (redoData) {
      const { redo } = redoData;
      if (isSuccessPayload(redo)) {
        const storedUndoStack = sessionStorage.getItem('undoStack');
        const storedRedoStack = sessionStorage.getItem('redoStack');

        // Remove first element of redo stack
        const redoStack: string[] = JSON.parse(storedRedoStack);
        const lastElement = redoStack.shift();
        sessionStorage.setItem('redoStack', JSON.stringify(redoStack));

        // Put the element in the 1st position of the undo stack
        const undoStack: string[] = JSON.parse(storedUndoStack);
        if (!undoStack.find((id) => id === lastElement)) {
          sessionStorage.setItem('undoStack', JSON.stringify([lastElement, ...undoStack]));
        }
      }
    }
  }, [redoData]);

  const undoKeyPressHandler = (e: KeyboardEvent) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'z') {
      undoLastAction();
    }
  };

  const redoKeyPressHandler = (e: KeyboardEvent) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'y') {
      redoLastAction();
    }
  };

  useEffect(() => {
    window.addEventListener('keydown', undoKeyPressHandler);
    return () => window.removeEventListener('keydown', undoKeyPressHandler);
  }, []);

  useEffect(() => {
    window.addEventListener('keydown', redoKeyPressHandler);
    return () => window.removeEventListener('keydown', redoKeyPressHandler);
  }, []);

  return children;
};
