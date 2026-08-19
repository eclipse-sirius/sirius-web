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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { useImpactAnalysisDialog } from '@eclipse-sirius/sirius-components-impactanalysis';
import { useEffect, useState } from 'react';
import { useInvokeImpactAnalysis } from './impact-analysis/useTreeImpactAnalysis';
import { GQLSingleClickTreeItemContextMenuEntry } from './useContextMenuEntries.types';
import {
  GQLInvokeSingleClickTreeItemContextMenuEntryData,
  GQLInvokeSingleClickTreeItemContextMenuEntryInput,
  GQLInvokeSingleClickTreeItemContextMenuEntryVariables,
  UseInvokeSingleClickContextMenuEntryState,
  UseInvokeSingleClickContextMenuEntryValue,
} from './useInvokeSingleClickContextMenuEntry.types';

const invokeSingleClickTreeItemContextMenuEntryMutation = gql`
  mutation invokeSingleClickTreeItemContextMenuEntry($input: InvokeSingleClickTreeItemContextMenuEntryInput!) {
    invokeSingleClickTreeItemContextMenuEntry(input: $input) {
      __typename
      ... on SuccessPayload {
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

export const useInvokeSingleClickContextMenuEntry = (): UseInvokeSingleClickContextMenuEntryValue => {
  const [state, setState] = useState<UseInvokeSingleClickContextMenuEntryState>({
    currentEntry: null,
    onEntryExecution: () => {},
  });

  const [invokeSingleClickTreeItemContextMenuEntry, result] = useMutation<
    GQLInvokeSingleClickTreeItemContextMenuEntryData,
    GQLInvokeSingleClickTreeItemContextMenuEntryVariables
  >(invokeSingleClickTreeItemContextMenuEntryMutation);
  useReporting(result, (payload) => payload.invokeSingleClickTreeItemContextMenuEntry);

  const invokeEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntryId: string,
    onClick: () => void
  ) => {
    const input: GQLInvokeSingleClickTreeItemContextMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      treeItemId,
      menuEntryId,
    };

    invokeSingleClickTreeItemContextMenuEntry({ variables: { input } });
    onClick();
  };

  const { showImpactAnalysisDialog } = useImpactAnalysisDialog();

  const {
    getImpactAnalysisReport,
    loading: impactAnalysisReportLoading,
    impactAnalysisReport,
  } = useInvokeImpactAnalysis();

  useEffect(() => {
    if (state.currentEntry && (impactAnalysisReport || impactAnalysisReportLoading)) {
      showImpactAnalysisDialog(
        impactAnalysisReport,
        impactAnalysisReportLoading,
        state.currentEntry.label,
        state.onEntryExecution
      );
    }
  }, [impactAnalysisReportLoading, impactAnalysisReport]);

  const invokeSingleClickContextMenuEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLSingleClickTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    if (menuEntry.withImpactAnalysis) {
      setState((prevState) => ({
        ...prevState,
        currentEntry: menuEntry,
        onEntryExecution: () => invokeEntry(editingContextId, treeId, treeItemId, menuEntry.id, onClick),
      }));
      getImpactAnalysisReport({
        variables: {
          editingContextId,
          representationId: treeId,
          treeItemId,
          menuEntryId: menuEntry.id,
        },
      });
    } else {
      invokeEntry(editingContextId, treeId, treeItemId, menuEntry.id, onClick);
      onClick();
    }
  };

  return { invokeSingleClickContextMenuEntry };
};
