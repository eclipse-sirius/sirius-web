/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Project } from '../pages/Project';
import { isCreateProjectSuccessPayload } from '../support/server/createProjectCommand';
import { isCreateProjectFromTemplateSuccessPayload } from '../support/server/createProjectFromTemplateCommand';
import { Explorer } from '../workbench/Explorer';
import { Workbench } from '../workbench/Workbench';
import { CreatedProjectData } from './Studio.types';

export class Studio {
  static readonly STUDIO_NATURE = 'siriusComponents://nature?kind=studio';

  public createStudioProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProjectFromTemplate('studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project studio has not been created`);
      }
    });
  }

  public createBlankStudioProjectWithView(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const projectId = payload.project.id;
        new Project().visit(projectId);
        const view_id = 'view';
        cy.createDocument(projectId, view_id, 'ViewDocument');
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project blank studio has not been created`);
      }
    });
  }

  public createProjectFromDomain(name: string, domain: string, entity: string): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject(name, [Studio.STUDIO_NATURE]).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;

        new Project().visit(projectId);

        const workbench = new Workbench();
        workbench.performAction('Others...');

        const explorer = new Explorer();
        explorer.createRootObject('Others...', domain, entity);

        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project "${name}" has not been created`);
      }
    });
  }
}
