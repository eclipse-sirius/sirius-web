/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { useContext, useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import {
  DiagramDirectEditInputProps,
  DiagramDirectEditInputState,
  GQLEditLabelData,
  GQLEditLabelVariables,
  GQLErrorPayload,
  GQLInitialDirectEditElementLabelData,
  GQLInitialDirectEditElementLabelInput,
  GQLRenameElementPayload,
  GQLSuccessPayload,
} from './DiagramDirectEditInput.types';

const initialDirectEditElementLabeQuery = gql`
  query initialDirectEditElementLabel($editingContextId: ID!, $diagramId: ID!, $labelId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              initialDirectEditElementLabel(labelId: $labelId)
            }
          }
        }
      }
    }
  }
`;

export const editLabelMutationOp = gql`
  mutation editLabel($input: EditLabelInput!) {
    editLabel(input: $input) {
      __typename
      ... on EditLabelSuccessPayload {
        diagram {
          id
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLRenameElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLRenameElementPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'EditLabelSuccessPayload';

export const DiagramDirectEditInput = ({ labelId, editingKey, width, onClose }: DiagramDirectEditInputProps) => {
  const { t } = useTranslation('siriusComponentsDiagrams', { keyPrefix: 'edit' });
  const { t: coreT } = useTranslation('siriusComponentsCore');
  const initialLabel = editingKey === null || editingKey === '' ? '' : editingKey;
  const [state, setState] = useState<DiagramDirectEditInputState>({
    newLabel: initialLabel,
  });

  const editionFinished = useRef<boolean>(false);

  const { addErrorMessage, addMessages } = useMultiToast();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const textInput = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);

  const { data: initialLabelItemData, error: initialLabelItemError } = useQuery<
    GQLInitialDirectEditElementLabelData,
    GQLInitialDirectEditElementLabelInput
  >(initialDirectEditElementLabeQuery, {
    variables: {
      editingContextId: editingContextId,
      diagramId: diagramId,
      labelId: labelId,
    },
  });

  useEffect(() => {
    // When opening the direct edit, close the current palette
    hideDiagramElementPalette();
  }, []);

  const [renameElement, { data: editLabelData, error: editLabelError }] = useMutation<
    GQLEditLabelData,
    GQLEditLabelVariables
  >(editLabelMutationOp);
  useEffect(() => {
    if (editLabelError) {
      addErrorMessage(coreT('errors.unexpected'));
    }
    if (editLabelData) {
      const { editLabel } = editLabelData;
      if (isErrorPayload(editLabel)) {
        addMessages(editLabel.messages);
      } else if (isSuccessPayload(editLabel)) {
        addMessages(editLabel.messages);
        if (editLabel.__typename === 'EditLabelSuccessPayload') {
          onClose();
        }
      }
    }
  }, [coreT, editLabelData, editLabelError]);

  useEffect(() => {
    let cleanup: (() => void) | undefined = undefined;
    if (initialLabelItemError) {
      addErrorMessage(coreT('errors.unexpected'));
    }
    if (initialLabelItemData?.viewer.editingContext.representation.description.initialDirectEditElementLabel) {
      const initialLabel =
        initialLabelItemData?.viewer.editingContext.representation.description.initialDirectEditElementLabel;
      if (!editingKey) {
        setState((prevState) => {
          return { ...prevState, newLabel: initialLabel };
        });
        const timeOutId = setTimeout(() => {
          textInput.current?.select();
        }, 0);
        cleanup = () => clearTimeout(timeOutId);
      }
    }
    return cleanup;
  }, [coreT, initialLabelItemError, initialLabelItemData]);

  useEffect(() => {
    if (textInput.current) {
      const text = textInput.current.value;
      textInput.current.setSelectionRange(text.length, text.length);
    }
  }, [textInput.current]);

  const doRename = () => {
    renameElement({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId: editingContextId,
          representationId: diagramId,
          labelId: labelId,
          newText: state.newLabel,
        },
      },
    });
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const newLabel = event.target.value;
    setState((prevState) => {
      return { ...prevState, newLabel: newLabel };
    });
  };

  const onBlur = () => {
    if (!editionFinished.current) {
      doRename();
    }
  };

  const onFinishEditing = (event: React.KeyboardEvent<HTMLDivElement>) => {
    const { key } = event;
    if (key === 'Enter' && !event.shiftKey) {
      editionFinished.current = true;
      event.preventDefault();
      doRename();
    } else if (key === 'Escape') {
      editionFinished.current = true;
      onClose();
    }
  };

  return (
    <Box width={width}>
      <TextField
        variant="standard"
        name="name"
        size="small"
        inputRef={textInput}
        placeholder={t('enterNewValue')}
        value={state.newLabel}
        multiline={true}
        onChange={handleChange}
        onKeyDown={onFinishEditing}
        onBlur={onBlur}
        autoFocus
        spellCheck={false}
        sx={{ width: width }}
        data-testid="name-edit"
        className="nodrag"
      />
    </Box>
  );
};
