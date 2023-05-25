/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { Representation, Toast, Workbench, WorkbenchViewContribution } from '@eclipse-sirius/sirius-components-core';
import { DetailsView, RelatedElementsView, RepresentationsView } from '@eclipse-sirius/sirius-components-forms';
import {
  ExplorerView,
  GQLTreeItem,
  TreeItemContextMenuContext,
  TreeItemContextMenuContribution,
} from '@eclipse-sirius/sirius-components-trees';
import { ValidationView } from '@eclipse-sirius/sirius-components-validation';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import Filter from '@material-ui/icons/Filter';
import LinkIcon from '@material-ui/icons/Link';
import MenuIcon from '@material-ui/icons/Menu';
import WarningIcon from '@material-ui/icons/Warning';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { generatePath, useHistory, useParams, useRouteMatch } from 'react-router-dom';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { OnboardArea } from '../../onboarding/OnboardArea';
import { DiagramTreeItemContextMenuContribution } from './DiagramTreeItemContextMenuContribution';
import { DocumentTreeItemContextMenuContribution } from './DocumentTreeItemContextMenuContribution';
import { EditProjectNavbar } from './EditProjectNavbar/EditProjectNavbar';
import { EditProjectViewParams, GQLGetProjectQueryData, GQLGetProjectQueryVariables } from './EditProjectView.types';
import {
  EditProjectViewContext,
  EditProjectViewEvent,
  editProjectViewMachine,
  HandleFetchedProjectEvent,
  HideToastEvent,
  SchemaValue,
  SelectRepresentationEvent,
  ShowToastEvent,
} from './EditProjectViewMachine';
import { ObjectTreeItemContextMenuContribution } from './ObjectTreeItemContextMenuContribution';

const getProjectQuery = gql`
  query getRepresentation($projectId: ID!, $representationId: ID!, $includeRepresentation: Boolean!) {
    viewer {
      project(projectId: $projectId) {
        id
        name
        currentEditingContext {
          id
          representation(representationId: $representationId) @include(if: $includeRepresentation) {
            id
            label
            kind
          }
        }
      }
    }
  }
`;

const useEditProjectViewStyles = makeStyles((theme) => ({
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
  const classes = useEditProjectViewStyles();
  const [{ value, context }, dispatch] = useMachine<EditProjectViewContext, EditProjectViewEvent>(
    editProjectViewMachine
  );
  const { toast, editProjectView } = value as SchemaValue;
  const { project, representation, message } = context;

  const { loading, data, error } = useQuery<GQLGetProjectQueryData, GQLGetProjectQueryVariables>(getProjectQuery, {
    variables: {
      projectId,
      representationId: representationId ?? '',
      includeRepresentation: !!representationId,
    },
  });
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const fetchProjectEvent: HandleFetchedProjectEvent = { type: 'HANDLE_FETCHED_PROJECT', data };
        dispatch(fetchProjectEvent);
      }
    }
  }, [loading, data, error, dispatch]);

  useEffect(() => {
    if (representation && representation.id !== representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: representation.id });
      history.push({ pathname });
    } else if (editProjectView === 'loaded' && representation === null && representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: null });
      history.push({ pathname });
    }
  }, [editProjectView, projectId, routeMatch, history, representation, representationId]);

  let main = null;
  if (editProjectView === 'loaded' && project) {
    const onRepresentationSelected = (representationSelected: Representation) => {
      const selectRepresentationEvent: SelectRepresentationEvent = {
        type: 'SELECT_REPRESENTATION',
        representation: representationSelected,
      };
      dispatch(selectRepresentationEvent);
    };

    const treeItemContextMenuContributions = [
      <TreeItemContextMenuContribution
        canHandle={(item: GQLTreeItem) => item.kind.startsWith('siriusWeb://document')}
        component={DocumentTreeItemContextMenuContribution}
      />,
      <TreeItemContextMenuContribution
        canHandle={(item: GQLTreeItem) => item.kind.startsWith('siriusComponents://semantic')}
        component={ObjectTreeItemContextMenuContribution}
      />,
      <TreeItemContextMenuContribution
        canHandle={(item: GQLTreeItem) => item.kind === 'siriusComponents://representation?type=Diagram'}
        component={DiagramTreeItemContextMenuContribution}
      />,
    ];

    main = (
      <TreeItemContextMenuContext.Provider value={treeItemContextMenuContributions}>
        <Workbench
          editingContextId={project.currentEditingContext.id}
          initialRepresentationSelected={representation}
          onRepresentationSelected={onRepresentationSelected}
          mainAreaComponent={OnboardArea}
          readOnly={false}>
          <WorkbenchViewContribution side="left" title="Explorer" icon={<AccountTreeIcon />} component={ExplorerView} />
          <WorkbenchViewContribution side="left" title="Validation" icon={<WarningIcon />} component={ValidationView} />
          <WorkbenchViewContribution side="right" title="Details" icon={<MenuIcon />} component={DetailsView} />
          <WorkbenchViewContribution
            side="right"
            title="Representations"
            icon={<Filter />}
            component={RepresentationsView}
          />
          <WorkbenchViewContribution
            side="right"
            title="Related Elements"
            icon={<LinkIcon />}
            component={RelatedElementsView}
          />
        </Workbench>
      </TreeItemContextMenuContext.Provider>
    );
  } else if (editProjectView === 'missing') {
    main = (
      <Grid container justifyContent="center" alignItems="center">
        <Typography variant="h4" align="center" gutterBottom>
          The project does not exist
        </Typography>
      </Grid>
    );
  }

  let navbar = null;
  if (editProjectView === 'missing' || editProjectView === 'loading') {
    navbar = <NavigationBar />;
  } else if (editProjectView === 'loaded') {
    navbar = <EditProjectNavbar project={project} />;
  }

  return (
    <>
      <div className={classes.editProjectView}>
        {navbar}
        {main}
      </div>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
