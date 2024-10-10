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
import { useEffect, useState } from 'react';
import { generatePath, Navigate, useNavigate, useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { EditProjectNavbar } from './EditProjectNavbar/EditProjectNavbar';
import { EditProjectViewParams, TreeToolBarProviderProps } from './EditProjectView.types';
import { editProjectViewReadOnlyPredicateExtensionPoint } from './EditProjectViewExtensionPoints';
import { ProjectContext } from './ProjectContext';
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';
import { GQLProject } from './useProjectAndRepresentationMetadata.types';

const useEditProjectViewStyles = makeStyles()((_) => ({
  editProjectView: {
    display: 'grid',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

interface EditProjectViewState {
  status: 'loading' | 'loaded' | 'missing';
  project: GQLProject | null;
  representationMetadata: RepresentationMetadata | null;
}

const initialState: EditProjectViewState = {
  status: 'loading',
  project: null,
  representationMetadata: null,
};

export const EditProjectView = () => {
  const navigate = useNavigate();
  const { classes } = useEditProjectViewStyles();
  const { projectId, representationId } = useParams<EditProjectViewParams>();
  const [viewState, setViewState] = useState<EditProjectViewState>(initialState);
  const { data: readOnlyPredicate } = useData(editProjectViewReadOnlyPredicateExtensionPoint);

  // Fetch the project and optionally initial representation metadata
  const { loading, data } = useProjectAndRepresentationMetadata(projectId, representationId);
  useEffect(() => {
    if (!loading && data && viewState.status !== 'loaded') {
      const { project } = data.viewer;
      setViewState((prevState) => ({
        ...prevState,
        status: project ? 'loaded' : 'missing',
        project,
        representationMetadata: project?.currentEditingContext.representation,
      }));
    }
  }, [loading, data]);

  const onRepresentationSelected = (representationMetadata: RepresentationMetadata | null) => {
    setViewState((prevState) => ({
      ...prevState,
      representationMetadata,
    }));
    if (representationMetadata && representationMetadata.id !== representationId) {
      // Switch to the URL of the new, different representation
      const pathname = generatePath('/projects/:projectId/edit/:representationId', {
        projectId,
        representationId: representationMetadata.id,
      });
      navigate(pathname);
    } else if (representationMetadata === null && representationId) {
      // Switch back to the plain project URL
      const pathname = generatePath('/projects/:projectId/edit/', { projectId });
      navigate(pathname);
    }
  };

  if (viewState.status === 'missing') {
    return <Navigate to="/errors/404" replace />;
  }

  let content: React.ReactNode = null;

  if (viewState.status === 'loading') {
    content = <NavigationBar />;
  } else if (viewState.status === 'loaded') {
    const initialSelection: Selection = {
      entries: viewState.representationMetadata
        ? [
            {
              id: viewState.representationMetadata.id,
              kind: viewState.representationMetadata.kind,
            },
          ]
        : [],
    };
    const readOnly = readOnlyPredicate(viewState.project);
    content = (
      <ProjectContext.Provider value={{ project: viewState.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
          <EditProjectNavbar readOnly={readOnly} />
          <TreeToolBarProvider>
            <Workbench
              editingContextId={viewState.project.currentEditingContext.id}
              initialRepresentationSelected={viewState.representationMetadata}
              onRepresentationSelected={onRepresentationSelected}
              readOnly={readOnly}
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
