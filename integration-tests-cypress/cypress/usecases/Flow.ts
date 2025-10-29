/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { isCreateProjectSuccessPayload } from '../support/server/createProjectCommand';
import { isCreateProjectFromTemplateSuccessPayload } from '../support/server/createProjectFromTemplateCommand';
import { CreatedProjectData } from './Flow.types';

export class Flow {
  static readonly FLOW_NATURE = 'siriusWeb://nature?kind=flow';

  public createRobotProject(name: string): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject(name, [Flow.FLOW_NATURE]).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;

        cy.getCurrentEditingContextId(projectId).then((res) => {
          const editingContextId = res.body.data.viewer.project.currentEditingContext.id;
          cy.createDocument(editingContextId, 'robot_flow', 'robot');
        });

        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project "${name}" has not been created`);
      }
    });
  }

  public createFlowProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProjectFromTemplate('Flow', 'flow-template', [Flow.FLOW_NATURE]).then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project flow has not been created`);
      }
    });
  }
}
