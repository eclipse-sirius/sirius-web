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
  Selection,
  SelectionContextProvider,
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
import { generatePath, Navigate, useNavigate, useParams } from 'react-router-dom';
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
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { UndoRedo } from './UndoRedo';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';

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
  const navigate = useNavigate();
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

  useEffect(() => {
    const projectIdToRedirect = name ? projectId + PROJECT_ID_SEPARATOR + name : projectId;
    if (context.representation && context.representation.id !== representationId && context.project.id === projectId) {
      const pathname = generatePath('/projects/:projectId/edit/:representationId', {
        projectId: projectIdToRedirect,
        representationId: context.representation.id,
      });
      navigate(pathname);
    } else if (value === 'loaded' && context.representation === null && representationId) {
      const pathname = generatePath('/projects/:projectId/edit/', { projectId: projectIdToRedirect });
      navigate(pathname);
    }
  }, [value, projectId, context.representation, representationId]);

  let content: React.ReactNode = null;

  if (value === 'loading') {
    content = <NavigationBar />;
  }

  if (value === 'missing') {
    return <Navigate to="/errors/404" replace />;
  }

  const { data: readOnlyPredicate } = useData(editProjectViewReadOnlyPredicateExtensionPoint);

  if (value === 'loaded' && context.project && context.project.currentEditingContext) {
    const initialSelection: Selection = {
      entries: context.representation
        ? [
            {
              id: context.representation.id,
            },
          ]
        : [],
    };

    const readOnly = readOnlyPredicate(context.project);
    content = (
      <ProjectContext.Provider value={{ project: context.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
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
