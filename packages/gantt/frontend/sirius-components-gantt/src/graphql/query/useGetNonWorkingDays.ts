/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
  GetNonWorkingDaysData,
  UseGetNonWorkingDaysProps,
  UseGetNonWorkingDaysValue,
  UseGetNonWorkingDaysVariables,
} from './useGetNonWorkingDays.types';

const getNonWorkingDays = gql`
  query getNonWorkingDays($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        getNonWorkingDays(representationId: $representationId) {
          holidays
          weekends
        }
      }
    }
  }
`;

export const useGetNonWorkingDays = ({
  editingContextId,
  ganttDescriptionId,
}: UseGetNonWorkingDaysProps): UseGetNonWorkingDaysValue => {
  const variables: UseGetNonWorkingDaysVariables = {
    editingContextId,
    representationId: ganttDescriptionId,
  };

  const { data, error, loading } = useQuery<GetNonWorkingDaysData, UseGetNonWorkingDaysVariables>(getNonWorkingDays, {
    variables,
  });

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  return {
    holidays: data?.viewer.editingContext?.getNonWorkingDays?.holidays ?? [],
    weekends: data?.viewer.editingContext?.getNonWorkingDays?.weekends ?? [],
    loading,
  };
};
