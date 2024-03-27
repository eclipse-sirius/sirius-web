/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { CreatedProjectData } from './Papaya.types';
import { isCreateProjectFromTemplateSuccessPayload } from '../support/server/createProjectFromTemplateCommand';
import { isCreateProjectSuccessPayload } from '../support/server/createProjectCommand';
import { Project } from '../pages/Project';
import { Workbench } from '../workbench/Workbench';
import { Explorer } from '../workbench/Explorer';

export class Papaya {
  public createPapayaStudioProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProjectFromTemplate('papaya-studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project papaya studio has not been created`);
      }
    });
  }

  public createPapayaInstanceProject(name: string): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject(name).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;

        new Project().visit(projectId);

        const workbench = new Workbench();
        workbench.performAction('Others...');

        const explorer = new Explorer();
        explorer.createRootObject('Others...', 'papaya_core', 'Root');

        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project "${name}" has not been created`);
      }
    });
  }
}
