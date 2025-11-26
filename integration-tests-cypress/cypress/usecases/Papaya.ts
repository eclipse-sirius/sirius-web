/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { CreatedProjectData } from './Papaya.types';

export class Papaya {
  static readonly PAPAYA_NATURE = 'siriusComponents://nature?kind=papaya';

  public createPapayaBlankProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject('Papaya - Blank', 'papaya-empty', []).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project papaya blank has not been created`);
      }
    });
  }

  public createPapayaTableExempleProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.createProject('PapayaTable', 'papaya-table-template', []).then((res) => {
      const payload = res.body.data.createProject;
      if (isCreateProjectSuccessPayload(payload)) {
        const projectId = payload.project.id;
        const data: CreatedProjectData = { projectId };
        return cy.wrap(data);
      } else {
        throw new Error(`The project papaya table exemple has not been created`);
      }
    });
  }

  public createPapayaLibraryExampleProject(): Cypress.Chainable<CreatedProjectData> {
    return cy.getLibraryId('papaya', 'java', '0.0.3').then((res) => {
      return cy
        .createProject('PapayaLibrary', 'papaya-library-template', [res.body.data.viewer.library.id])
        .then((res) => {
          const payload = res.body.data.createProject;
          if (isCreateProjectSuccessPayload(payload)) {
            const projectId = payload.project.id;
            const data: CreatedProjectData = { projectId };
            return cy.wrap(data);
          } else {
            throw new Error(`The project papaya library example has not been created`);
          }
        });
    });
  }
}
