/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import { Projects } from '../../pages/Projects';

const projectName = `Cypress - projects`;

describe('/projects', () => {
  beforeEach(() => {
    cy.createProject(projectName).then(() => new Projects().visit());
  });

  afterEach(() => {
    new Projects().deleteProject(projectName);
  });

  it('contains a link to the new project page', () => {
    new Projects().getCreateProjectLink().should('be.visible');
  });

  it('contains a link to the upload project page', () => {
    new Projects().getUploadProjectLink().should('be.visible');
  });

  it('contains the list of projects', () => {
    new Projects().getProjectLink(projectName).should('be.visible');
  });
});
