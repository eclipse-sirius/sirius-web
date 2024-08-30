/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import {
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeToolBarContribution,
} from '@eclipse-sirius/sirius-components-trees';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { generatePath, useHistory, useParams, useRouteMatch } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { EditProjectNavbar } from './EditProjectNavbar/EditProjectNavbar';
import { EditProjectViewParams, TreeToolBarProviderProps } from './EditProjectView.types';
import { editProjectViewReadOnlyPredicateExtensionPoint } from './EditProjectViewExtensionPoints';
import {
  EditProjectViewContext,
  EditProjectViewEvent,
  editProjectViewMachine,
  HandleFetchedProjectEvent,
  SelectRepresentationEvent,
} from './EditProjectViewMachine';
import { ProjectContext } from './ProjectContext';
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';

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
  const history = useHistory();
  const routeMatch = useRouteMatch();
  const { projectId, representationId } = useParams<EditProjectViewParams>();
  const { classes } = useEditProjectViewStyles();

  const [{ value, context }, dispatch] = useMachine<EditProjectViewContext, EditProjectViewEvent>(
    editProjectViewMachine
  );

  const { data } = useProjectAndRepresentationMetadata(projectId, representationId);
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
    if (context.representation && context.representation.id !== representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: context.representation.id });
      history.push({ pathname });
    } else if (value === 'loaded' && context.representation === null && representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: null });
      history.push({ pathname });
    }
  }, [value, projectId, routeMatch, history, context.representation, representationId]);

  let content: React.ReactNode = null;

  if (value === 'loading') {
    content = <NavigationBar />;
  }

  if (value === 'missing') {
    content = (
      <>
        <NavigationBar />
        <Grid container justifyContent="center" alignItems="center">
          <Typography variant="h4" align="center" gutterBottom>
            The project does not exist
          </Typography>
        </Grid>
      </>
    );
  }

  const { data: readOnlyPredicate } = useData(editProjectViewReadOnlyPredicateExtensionPoint);

  if (value === 'loaded' && context.project) {
    const initialSelection: Selection = {
      entries: context.representation
        ? [
            {
              id: context.representation.id,
              kind: context.representation.kind,
            },
          ]
        : [],
    };
    content = (
      <ProjectContext.Provider value={{ project: context.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
          <EditProjectNavbar />
          <TreeToolBarProvider>
            <Workbench
              editingContextId={context.project.currentEditingContext.id}
              initialRepresentationSelected={context.representation}
              onRepresentationSelected={onRepresentationSelected}
              readOnly={readOnlyPredicate(context.project)}
            />
          </TreeToolBarProvider>
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
