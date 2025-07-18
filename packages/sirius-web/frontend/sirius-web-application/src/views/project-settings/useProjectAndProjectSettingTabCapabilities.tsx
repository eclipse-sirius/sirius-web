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
  GQLGetProjectAndProjectSettingsTabsCapabilitiesData,
  GQLGetProjectAndProjectSettingsTabsCapabilitiesVariables,
  UseProjectAndProjectSettingTabCapabilitiesValue,
} from './useProjectAndProjectSettingTabCapabilities.types';

const getProjectAndProjectSettingsTabsCapabilitiesQuery = gql`
  query getProjectAndProjectSettingsTabsCapabilities($projectId: ID!, $tabIds: [ID!]!) {
    viewer {
      project(projectId: $projectId) {
        id
        name
        capabilities {
          settings {
            canView
            tabs(tabIds: $tabIds) {
              tabId
              canView
            }
          }
        }
      }
    }
  }
`;

export const useProjectAndProjectSettingTabCapabilities = (
  projectId: string,
  tabIds: string[]
): UseProjectAndProjectSettingTabCapabilitiesValue => {
  const { loading, data, error } = useQuery<
    GQLGetProjectAndProjectSettingsTabsCapabilitiesData,
    GQLGetProjectAndProjectSettingsTabsCapabilitiesVariables
  >(getProjectAndProjectSettingsTabsCapabilitiesQuery, {
    variables: {
      projectId,
      tabIds,
    },
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    loading,
    data: data ?? null,
  };
};
