/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLRichText } from '../form/FormEventFragments.types';
import { RichTextEditor } from '../richtexteditor/RichTextEditor';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditRichTextInput,
  GQLEditRichTextMutationData,
  GQLEditRichTextMutationVariables,
  GQLEditRichTextPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
} from './RichTextPropertySection.types';

export const editRichTextMutation = gql`
  mutation editRichText($input: EditRichTextInput!) {
    editRichText(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEditRichTextPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLEditRichTextPayload | GQLUpdateWidgetFocusPayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

/**
 * Defines the content of a Rich Text property section.
 * The content is submitted when the focus is lost.
 */
export const RichTextPropertySection: PropertySectionComponent<GQLRichText> = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLRichText>) => {
  const [editRichText, { loading: updateRichTextLoading, data: updateRichTextData, error: updateRichTextError }] =
    useMutation<GQLEditRichTextMutationData, GQLEditRichTextMutationVariables>(editRichTextMutation);
  const sendEditedValue = (newValue) => {
    const input: GQLEditRichTextInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      richTextId: widget.id,
      newValue: newValue,
    };
    const variables: GQLEditRichTextMutationVariables = { input };
    editRichText({ variables });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!updateRichTextLoading) {
      if (updateRichTextError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateRichTextData) {
        const { editRichText } = updateRichTextData;
        if (isErrorPayload(editRichText) || isSuccessPayload(editRichText)) {
          addMessages(editRichText.messages);
        }
      }
    }
  }, [updateRichTextLoading, updateRichTextData, updateRichTextError]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);
  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = (currentText: string) => {
    sendUpdateWidgetFocus(false);
    if (currentText !== widget.stringValue) {
      sendEditedValue(currentText);
    }
  };

  return (
    <div>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
      />
      <div data-testid={widget.label}>
        <RichTextEditor
          value={widget.stringValue}
          placeholder={widget.label}
          onFocus={onFocus}
          onBlur={onBlur}
          readOnly={readOnly || widget.readOnly}
        />
      </div>
    </div>
  );
};
