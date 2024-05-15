/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLRichText } from '../form/FormEventFragments.types';
import { RichTextEditor } from '../richtexteditor/RichTextEditor';
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditRichTextInput,
  GQLEditRichTextMutationData,
  GQLEditRichTextMutationVariables,
  GQLEditRichTextPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
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

const isErrorPayload = (payload: GQLEditRichTextPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditRichTextPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

const useRichTextSectionStyles = makeStyles()((theme) => ({
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    gap: theme.spacing(2),
    alignItems: 'center',
  },
}));

/**
 * Defines the content of a Rich Text property section.
 * The content is submitted when the focus is lost.
 */
export const RichTextPropertySection: PropertySectionComponent<GQLRichText> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLRichText>) => {
  const { classes } = useRichTextSectionStyles();
  const { t } = useTranslation('sirius-components-forms');

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
    if (updateRichTextError) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (updateRichTextData) {
      const { editRichText } = updateRichTextData;
      if (isErrorPayload(editRichText) || isSuccessPayload(editRichText)) {
        addMessages(editRichText.messages);
      }
    }
  }, [updateRichTextError, updateRichTextData]);

  const onBlur = (currentText: string) => {
    if (currentText !== widget.stringValue) {
      sendEditedValue(currentText);
    }
  };

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={updateRichTextLoading} />
      </div>
      <div data-testid={widget.label}>
        <RichTextEditor
          value={widget.stringValue}
          placeholder={widget.label}
          onBlur={onBlur}
          readOnly={readOnly || widget.readOnly}
        />
      </div>
    </div>
  );
};
