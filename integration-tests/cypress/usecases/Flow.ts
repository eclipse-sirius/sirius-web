/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { isCreateProjectSuccessPayload } from '../support/server/createProjectCommand';
import { CreatedProjectData } from './Flow.types';

export class Flow {
  public createRobotProject(name: string): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject(name).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';

        cy.createDocument(projectId, robot_flow_id, 'robot');
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project "${name}" has not been created`);
      }
    });
  }
}
