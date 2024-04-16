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
import { MutationResult } from '@apollo/client';
import { useEffect } from 'react';
import { GQLErrorPayload, GQLSuccessPayload } from '../graphql/GQLTypes.types';
import { useMultiToast } from './MultiToast';

export const isErrorPayload = (payload: GQLSuccessPayload | GQLErrorPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
export const isSuccessPayload = (payload: GQLSuccessPayload | GQLErrorPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useReporting = <T>(
  result: MutationResult<T>,
  extractPayload: (data: T) => GQLSuccessPayload | GQLErrorPayload
) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    const { loading, data, error } = result;
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      if (data) {
        const payload: GQLErrorPayload | GQLSuccessPayload = extractPayload(data);
        if (isSuccessPayload(payload) || isErrorPayload(payload)) {
          const { messages } = payload;
          if (messages) {
            addMessages(messages);
          }
        }
      }
    }
  }, [result.loading, result.data, result.error]);
};
