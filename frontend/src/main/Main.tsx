/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { withErrorBoundary } from '@eclipse-sirius/sirius-components';
import { Redirect, Route, Switch } from 'react-router-dom';

import { NewProjectView } from 'views/new-project/NewProjectView';
import { UploadProjectView } from 'views/upload-project/UploadProjectView';
import { ProjectsView } from 'views/projects/ProjectsView';
import { EditProjectView } from 'views/edit-project/EditProjectView';

/**
 * Defines the content of the main part of the user interface.
 *
 * @author sbegaudeau
 */
export const Main = () => {
  return (
    <Switch>
      <Route exact path="/new/project" component={withErrorBoundary(NewProjectView)} />
      <Route exact path="/upload/project" component={withErrorBoundary(UploadProjectView)} />
      <Route exact path="/projects" component={withErrorBoundary(ProjectsView)} />
      <Route exact path="/projects/:projectId/edit/:representationId?" component={withErrorBoundary(EditProjectView)} />
      <Redirect to="/projects" />
    </Switch>
  );
};
