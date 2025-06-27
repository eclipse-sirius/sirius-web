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
import {
  GQLGetProjectsCapabilitiesQueryData,
  GQLGetProjectsCapabilitiesQueryVariables,
  UseProjectsCapabilitiesValue,
} from './useProjectsCapabilities.types';

const getProjectsCapabilitiesQuery = gql`
  query getProjectsCapabilities {
    viewer {
      capabilities {
        projects {
          canCreate
          canUpload
        }
      }
    }
  }
`;

export const useProjectsCapabilities = (): UseProjectsCapabilitiesValue => {
  const { data, loading, error } = useQuery<
    GQLGetProjectsCapabilitiesQueryData,
    GQLGetProjectsCapabilitiesQueryVariables
  >(getProjectsCapabilitiesQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    data,
    loading,
  };
};
