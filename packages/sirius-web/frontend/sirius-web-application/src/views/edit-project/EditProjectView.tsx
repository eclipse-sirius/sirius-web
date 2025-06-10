/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import {
  RepresentationMetadata,
  RepresentationPathContext,
  Selection,
  SelectionContextProvider,
  SelectionEntry,
  useData,
  Workbench,
} from '@eclipse-sirius/sirius-components-core';
import { OmniboxProvider } from '@eclipse-sirius/sirius-components-omnibox';
import {
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeToolBarContribution,
} from '@eclipse-sirius/sirius-components-trees';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Navigate, useParams, useSearchParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { EditProjectNavbar } from './EditProjectNavbar/EditProjectNavbar';
import { EditProjectViewParams, TreeToolBarProviderProps } from './EditProjectView.types';
import { editProjectViewReadOnlyPredicateExtensionPoint } from './EditProjectViewExtensionPoints';
import {
  EditProjectViewContext,
  EditProjectViewEvent,
  editProjectViewMachine,
  EditProjectViewStateSchema,
  HandleFetchedProjectEvent,
  SelectRepresentationEvent,
} from './EditProjectViewMachine';
import { ProjectContext } from './ProjectContext';
import { SelectionSynchronizer } from './SelectionSynchronizer';
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { UndoRedo } from './UndoRedo';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';
import { useSynchronizeSelectionAndURL } from './useSynchronizeSelectionAndURL';

const PROJECT_ID_SEPARATOR = '@';

const useEditProjectViewStyles = makeStyles()((_) => ({
  editProjectView: {
    display: 'grid',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

export const EditProjectView = () => {
  const { projectId: rawProjectId, representationId } = useParams<EditProjectViewParams>();
  const { classes } = useEditProjectViewStyles();

  const [{ value, context }, dispatch] =
    useMachine<StateMachine<EditProjectViewContext, EditProjectViewStateSchema, EditProjectViewEvent>>(
      editProjectViewMachine
    );

  const separatorIndex = rawProjectId.indexOf(PROJECT_ID_SEPARATOR);
  const projectId: string = separatorIndex !== -1 ? rawProjectId.substring(0, separatorIndex) : rawProjectId;
  const name: string | null =
    separatorIndex !== -1 ? rawProjectId.substring(separatorIndex + 1, rawProjectId.length) : null;

  const { data } = useProjectAndRepresentationMetadata(projectId, name, representationId);
  useEffect(() => {
    if (data) {
      const fetchProjectEvent: HandleFetchedProjectEvent = { type: 'HANDLE_FETCHED_PROJECT', data };
      dispatch(fetchProjectEvent);
    }
  }, [data]);

  const onRepresentationSelected = (representationMetadata: RepresentationMetadata) => {
    const selectRepresentationEvent: SelectRepresentationEvent = {
      type: 'SELECT_REPRESENTATION',
      representation: representationMetadata,
    };
    dispatch(selectRepresentationEvent);
  };

  useSynchronizeSelectionAndURL(
    projectId,
    name,
    representationId,
    context.project?.id ?? null,
    context.representation?.id ?? null,
    value !== 'loaded'
  );

  const getRepresentationPath = (representationId: string) => {
    // Note that this should match the corresponding route configuration
    return `/projects/${projectId}/edit/${representationId}`;
  };

  const { data: readOnlyPredicate } = useData(editProjectViewReadOnlyPredicateExtensionPoint);

  const [urlSearchParams] = useSearchParams();

  let content: React.ReactNode = null;
  if (value === 'loading') {
    content = <NavigationBar />;
  }

  if (value === 'missing') {
    return <Navigate to="/errors/404" replace />;
  }

  if (value === 'loaded' && context.project && context.project.currentEditingContext) {
    const urlSelectionValue: string = urlSearchParams.get('selection') ?? '';
    const entries: SelectionEntry[] =
      urlSelectionValue.trim().length > 0 ? urlSelectionValue.split(',').map((id) => ({ id })) : [];
    const initialSelection: Selection = { entries };

    const readOnly = readOnlyPredicate(context.project);
    content = (
      <ProjectContext.Provider value={{ project: context.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
          <SelectionSynchronizer>
            <RepresentationPathContext.Provider value={{ getRepresentationPath }}>
              <OmniboxProvider editingContextId={context.project.currentEditingContext.id}>
                <UndoRedo>
                  <EditProjectNavbar readOnly={readOnly} />
                  <TreeToolBarProvider>
                    <Workbench
                      editingContextId={context.project.currentEditingContext.id}
                      initialRepresentationSelected={context.representation}
                      onRepresentationSelected={onRepresentationSelected}
                      readOnly={readOnly}
                    />
                  </TreeToolBarProvider>
                </UndoRedo>
              </OmniboxProvider>
            </RepresentationPathContext.Provider>
          </SelectionSynchronizer>
        </SelectionContextProvider>
      </ProjectContext.Provider>
    );
  }

  return <div className={classes.editProjectView}>{content}</div>;
};

const TreeToolBarProvider = ({ children }: TreeToolBarProviderProps) => {
  const treeToolBarContributions: TreeToolBarContextValue = [
    <TreeToolBarContribution component={NewDocumentModalContribution} />,
    <TreeToolBarContribution component={UploadDocumentModalContribution} />,
  ];

  return <TreeToolBarContext.Provider value={treeToolBarContributions}>{children}</TreeToolBarContext.Provider>;
};
