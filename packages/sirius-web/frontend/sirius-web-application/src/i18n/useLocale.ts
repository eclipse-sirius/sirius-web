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
import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { GQLGetLocaleQueryData, GQLGetLocaleQueryVariables, UseLocaleValue } from './useLocale.types';

const getLocaleQuery = gql`
  query getLocale {
    viewer {
      language
      namespaces
    }
  }
`;

export const useLocale = (): UseLocaleValue => {
  const { data, loading, error } = useQuery<GQLGetLocaleQueryData, GQLGetLocaleQueryVariables>(getLocaleQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    language: data?.viewer.language ?? null,
    namespaces: data?.viewer.namespaces ?? [],
    loading,
  };
};
