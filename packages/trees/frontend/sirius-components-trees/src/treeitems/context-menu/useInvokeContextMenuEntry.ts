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
import { gql, useLazyQuery, useMutation } from '@apollo/client';
import { useData, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useImpactAnalysisDialog } from '@eclipse-sirius/sirius-components-impactanalysis';
import { useEffect, useState } from 'react';
import { useInvokeImpactAnalysis } from './impact-analysis/useTreeImpactAnalysis';
import { treeItemContextMenuEntryOverrideExtensionPoint } from './TreeItemContextMenuEntryExtensionPoints';
import { TreeItemContextMenuOverrideContribution } from './TreeItemContextMenuEntryExtensionPoints.types';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';
import {
  GQLErrorPayload,
  GQLFetchTreeItemContextEntryDataData,
  GQLGetFetchTreeItemContextMenuEntryDataQueryVariables,
  GQLInvokeSingleClickTreeItemContextMenuEntryData,
  GQLInvokeSingleClickTreeItemContextMenuEntryInput,
  GQLInvokeSingleClickTreeItemContextMenuEntryPayload,
  GQLInvokeSingleClickTreeItemContextMenuEntryVariables,
  UseInvokeContextMenuEntryState,
  UseInvokeContextMenuEntryValue,
} from './useInvokeContextMenuEntry.types';

const invokeSingleClickTreeItemContextMenuEntryMutation = gql`
  mutation invokeSingleClickTreeItemContextMenuEntry($input: InvokeSingleClickTreeItemContextMenuEntryInput!) {
    invokeSingleClickTreeItemContextMenuEntry(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getFetchTreeItemContextMenuEntryDataQuery = gql`
  query getFetchTreeItemContextMenuEntryDataQuery(
    $editingContextId: ID!
    $representationId: ID!
    $treeItemId: ID!
    $menuEntryId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              fetchTreeItemContextMenuEntryData(treeItemId: $treeItemId, menuEntryId: $menuEntryId) {
                urlToFetch
                fetchKind
              }
            }
          }
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeSingleClickTreeItemContextMenuEntryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useInvokeContextMenuEntry = (): UseInvokeContextMenuEntryValue => {
  const { addErrorMessage } = useMultiToast();

  const { data: treeItemContextMenuOverrideContributions } = useData<TreeItemContextMenuOverrideContribution[]>(
    treeItemContextMenuEntryOverrideExtensionPoint
  );

  const [state, setState] = useState<UseInvokeContextMenuEntryState>({
    currentEntry: null,
    onEntryExecution: () => {},
    onClick: () => {},
  });

  const [getFetchData, { data, error }] = useLazyQuery<
    GQLFetchTreeItemContextEntryDataData,
    GQLGetFetchTreeItemContextMenuEntryDataQueryVariables
  >(getFetchTreeItemContextMenuEntryDataQuery);
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }

    if (data) {
      const { urlToFetch, fetchKind } =
        data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData;
      if (fetchKind === 'DOWNLOAD') {
        window.location.href = urlToFetch;
      } else if (fetchKind === 'OPEN') {
        window.open(urlToFetch, '_blank', 'noopener,noreferrer');
      }
      state.onClick();
    }
  }, [data, error]);

  const [invokeSingleClickTreeItemContextMenuEntry, { data: invokeSingleClickData, error: invokeSingleClickError }] =
    useMutation<
      GQLInvokeSingleClickTreeItemContextMenuEntryData,
      GQLInvokeSingleClickTreeItemContextMenuEntryVariables
    >(invokeSingleClickTreeItemContextMenuEntryMutation);

  useEffect(() => {
    if (invokeSingleClickError) {
      addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
    }
    if (invokeSingleClickData) {
      const { invokeSingleClickTreeItemContextMenuEntry } = invokeSingleClickData;
      if (isErrorPayload(invokeSingleClickTreeItemContextMenuEntry)) {
        addErrorMessage(invokeSingleClickTreeItemContextMenuEntry.message);
      }
    }
  }, [invokeSingleClickError, invokeSingleClickData]);

  const invokeEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    if (menuEntry.__typename === 'FetchTreeItemContextMenuEntry') {
      const variables: GQLGetFetchTreeItemContextMenuEntryDataQueryVariables = {
        editingContextId,
        representationId: treeId,
        treeItemId,
        menuEntryId: menuEntry.id,
      };
      getFetchData({ variables });
    } else if (menuEntry.__typename === 'SingleClickTreeItemContextMenuEntry') {
      const input: GQLInvokeSingleClickTreeItemContextMenuEntryInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: treeId,
        treeItemId,
        menuEntryId: menuEntry.id,
      };
      invokeSingleClickTreeItemContextMenuEntry({ variables: { input } });
      onClick();
    }
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

  const invokeContextMenuEntry = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => {
    const menuEntryIsOverridden = treeItemContextMenuOverrideContributions.some((contribution) =>
      contribution.canHandle(menuEntry)
    );

    if (menuEntryIsOverridden) {
      // Do not attempt to invoke an overridden menu entry with the regular entry invocation mutations:
      // overridden entries define their own behavior and we cannot assume they rely on these mutations.
      return;
    }

    const executeMenuEntry = () => invokeEntry(editingContextId, treeId, treeItemId, menuEntry, onClick);

    if (menuEntry.withImpactAnalysis) {
      setState((prevState) => ({
        ...prevState,
        currentEntry: menuEntry,
        onEntryExecution: () => executeMenuEntry(),
        onClick,
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
      setState((prevState) => ({
        ...prevState,
        onClick,
      }));
      executeMenuEntry();
    }
  };
  return { invokeContextMenuEntry };
};
