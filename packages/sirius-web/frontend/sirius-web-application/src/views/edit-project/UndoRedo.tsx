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
  GQLSuccessPayload,
  GQLUndoData,
  GQLUndoRedoInput,
  GQLUndoRedoItemPayload,
  GQLUndoVariables,
} from './UndoRedo.types';

const undoMutation = gql`
  mutation undo($input: UndoRedoInput!) {
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
  mutation redo($input: UndoRedoInput!) {
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
    var storedArray = sessionStorage.getItem('undoStack');
    if (storedArray) {
      var arr = JSON.parse(storedArray);
      if (arr[0]) {
        const input: GQLUndoRedoInput = {
          id: crypto.randomUUID(),
          editingContextId: projectId,
          mutationId: arr[0],
        };
        undo({ variables: { input } });
      }
    }
  };

  const redoLastAction = () => {
    var storedArray = sessionStorage.getItem('redoStack');
    if (storedArray) {
      var arr = JSON.parse(storedArray);
      if (arr[0]) {
        const input: GQLUndoRedoInput = {
          id: crypto.randomUUID(),
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
        var storedUndoStack = sessionStorage.getItem('undoStack');
        var storedRedoStack = sessionStorage.getItem('redoStack');

        //Remove first element of undo stack
        var undoStack = JSON.parse(storedUndoStack);
        var lastElement = undoStack.shift();
        sessionStorage.setItem('undoStack', JSON.stringify(undoStack));

        //Put the element in the 1st position of the redo stack
        var redoStack = JSON.parse(storedRedoStack);
        sessionStorage.setItem('redoStack', JSON.stringify([lastElement, ...redoStack]));
      }
    }
  }, [undoData]);

  useEffect(() => {
    if (redoData) {
      const { redo } = redoData;
      if (isSuccessPayload(redo)) {
        var storedUndoStack = sessionStorage.getItem('undoStack');
        var storedRedoStack = sessionStorage.getItem('redoStack');

        //Remove first element of redo stack
        var redoStack = JSON.parse(storedRedoStack);
        var lastElement = redoStack.shift();
        sessionStorage.setItem('redoStack', JSON.stringify(redoStack));

        //Put the element in the 1st position of the undo stack
        var undoStack = JSON.parse(storedUndoStack);
        sessionStorage.setItem('undoStack', JSON.stringify([lastElement, ...undoStack]));
      }
    }
  }, [redoData]);

  const undoKeyPressHandler = (e) => {
    if (e.ctrlKey && e.key === 'z') {
      undoLastAction();
    }
  };

  const redoKeyPressHandler = (e) => {
    if (e.ctrlKey && e.key === 'y') {
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
