/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import {
  GQLCreateProjectFromTemplateMutationData,
  GQLCreateProjectFromTemplateMutationVariables,
  GQLCreateProjectFromTemplatePayload,
  GQLCreateProjectFromTemplateSuccessPayload,
  GQLErrorPayload,
  UseCreateProjectFromTemplateValue,
} from './useCreateProjectFromTemplate.types';

export const createProjectFromTemplateMutation = gql`
  mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
    createProjectFromTemplate(input: $input) {
      __typename
      ... on CreateProjectFromTemplateSuccessPayload {
        project {
          id
        }
        representationToOpen {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLCreateProjectFromTemplatePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isCreateProjectFromTemplateSuccessPayload = (
  payload: GQLCreateProjectFromTemplatePayload
): payload is GQLCreateProjectFromTemplateSuccessPayload =>
  payload.__typename === 'CreateProjectFromTemplateSuccessPayload';

export const useCreateProjectFromTemplate = (): UseCreateProjectFromTemplateValue => {
  const [performProjectCreationFromTemplate, { loading, data, error }] = useMutation<
    GQLCreateProjectFromTemplateMutationData,
    GQLCreateProjectFromTemplateMutationVariables
  >(createProjectFromTemplateMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  const { t: coreT } = useTranslation('siriusComponentsCore');

  useEffect(() => {
    if (error) {
      addErrorMessage(coreT('errors.unexpected'));
    }
    if (data) {
      const { createProjectFromTemplate } = data;
      if (isErrorPayload(createProjectFromTemplate)) {
        addMessages(createProjectFromTemplate.messages);
      }
    }
  }, [coreT, data, error]);

  const createProjectFromTemplate = (templateId: string) => {
    const variables: GQLCreateProjectFromTemplateMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        templateId,
      },
    };

    performProjectCreationFromTemplate({ variables });
  };

  let projectCreatedFromTemplate: GQLCreateProjectFromTemplateSuccessPayload | null = null;
  if (data && isCreateProjectFromTemplateSuccessPayload(data.createProjectFromTemplate)) {
    projectCreatedFromTemplate = data.createProjectFromTemplate;
  }

  return {
    createProjectFromTemplate,
    loading,
    projectCreatedFromTemplate,
  };
};
