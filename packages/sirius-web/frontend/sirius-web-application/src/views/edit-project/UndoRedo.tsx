/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLRedoData,
  GQLRedoInput,
  GQLRedoVariables,
  GQLSuccessPayload,
  GQLUndoData,
  GQLUndoInput,
  GQLUndoRedoItemPayload,
  GQLUndoVariables,
} from './UndoRedo.types';
import { useCurrentProject } from './useCurrentProject';

const undoMutation = gql`
  mutation undo($input: UndoInput!) {
    undo(input: $input) {
      __typename
      ... on SuccessPayload {
        id
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        id
        messages {
          body
          level
        }
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
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        id
        messages {
          body
          level
        }
      }
    }
  }
`;

const isSuccessPayload = (payload: GQLUndoRedoItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';
const isErrorPayload = (payload: GQLUndoRedoItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const UndoRedo = ({ children }: { children: React.ReactNode }) => {
  const [undo, { data: undoData, error: undoError }] = useMutation<GQLUndoData, GQLUndoVariables>(undoMutation);
  const [redo, { data: redoData, error: redoError }] = useMutation<GQLRedoData, GQLRedoVariables>(redoMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (undoError) {
      addErrorMessage(undoError.message);
    }
  }, [undoError]);

  useEffect(() => {
    if (redoError) {
      addErrorMessage(redoError.message);
    }
  }, [redoError]);

  const { project } = useCurrentProject();

  useEffect(() => {
    sessionStorage.setItem('undoStack', JSON.stringify([]));
    sessionStorage.setItem('redoStack', JSON.stringify([]));
  }, []);

  const undoLastAction = () => {
    var storedArray = sessionStorage.getItem('undoStack');
    if (storedArray) {
      var arr = JSON.parse(storedArray);
      if (arr[0]) {
        const input: GQLUndoInput = {
          id: crypto.randomUUID(),
          editingContextId: project.currentEditingContext.id,
          inputId: arr[0],
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
        const input: GQLRedoInput = {
          id: crypto.randomUUID(),
          editingContextId: project.currentEditingContext.id,
          inputId: arr[0],
        };
        redo({ variables: { input } });
      }
    }
  };

  useEffect(() => {
    if (undoData) {
      const { undo } = undoData;
      if (isSuccessPayload(undo)) {
        addMessages(undo.messages);

        var storedUndoStack = sessionStorage.getItem('undoStack');
        var storedRedoStack = sessionStorage.getItem('redoStack');

        //Remove first element of undo stack
        var undoStack = JSON.parse(storedUndoStack);
        var lastElement = undoStack.shift();
        sessionStorage.setItem('undoStack', JSON.stringify(undoStack));

        //Put the element in the 1st position of the redo stack
        var redoStack = JSON.parse(storedRedoStack);
        sessionStorage.setItem('redoStack', JSON.stringify([lastElement, ...redoStack]));
      } else if (isErrorPayload(undo)) {
        addMessages(undo.messages);
      }
    }
  }, [undoData]);

  useEffect(() => {
    if (redoData) {
      const { redo } = redoData;
      if (isSuccessPayload(redo)) {
        addMessages(redo.messages);

        var storedUndoStack = sessionStorage.getItem('undoStack');
        var storedRedoStack = sessionStorage.getItem('redoStack');

        //Remove first element of redo stack
        var redoStack = JSON.parse(storedRedoStack);
        var lastElement = redoStack.shift();
        sessionStorage.setItem('redoStack', JSON.stringify(redoStack));

        //Put the element in the 1st position of the undo stack
        var undoStack = JSON.parse(storedUndoStack);
        sessionStorage.setItem('undoStack', JSON.stringify([lastElement, ...undoStack]));
      } else if (isErrorPayload(redo)) {
        addMessages(redo.messages);
      }
    }
  }, [redoData]);

  const undoKeyPressHandler = (e) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'z') {
      undoLastAction();
    }
  };

  const redoKeyPressHandler = (e) => {
    if ((e.ctrlKey || e.metaKey) && (e.key === 'y' || (e.shiftKey && e.key === 'Z'))) {
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
