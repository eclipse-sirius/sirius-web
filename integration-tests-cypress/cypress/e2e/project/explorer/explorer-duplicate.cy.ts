/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Project } from '../../../pages/Project';
import { isCreateProjectSuccessPayload } from '../../../support/server/createProjectCommand';
import { Explorer } from '../../../workbench/Explorer';

describe('Explorer - duplicate object', () => {
  let studioProjectId: string = '';
  let domainName: string = '';
  context('Given a studio with a domain', () => {
    beforeEach(() => {
      cy.createProject('Studio', 'studio-template').then((res) => {
        const payload = res.body.data.createProject;
        if (isCreateProjectSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;

          const project = new Project();
          project.visit(projectId);

          const explorer = new Explorer();
          explorer.expandWithDoubleClick('DomainNewModel');
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expandWithDoubleClick(domainName);
            explorer.expandWithDoubleClick('Entity1');
            explorer.expandWithDoubleClick('Entity2');
          });
        }
      });
    });

    afterEach(() => cy.deleteProject(studioProjectId));

    context('When we duplicate an object using the contextual menu', () => {
      it('Then the object is twice in the explorer', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('attribute2').should('have.length', 1);
        cy.getByTestId('attribute2-more').click();
        cy.getByTestId('duplicate-object').click();
        cy.getByTestId('duplicate-object-dialog').find(`[data-treeitemlabel="DomainNewModel"]`).dblclick();
        cy.getByTestId('duplicate-object-dialog').find(`[data-treeitemlabel="${domainName}"]`).dblclick();
        cy.getByTestId('duplicate-object-dialog').find(`[data-treeitemlabel="Entity2"]`).click();
        cy.getByTestId('containment-feature-name')
          .children('[role="combobox"]')
          .invoke('text')
          .should('eq', 'Add in attributes');
        cy.getByTestId('duplicate-object-button').click();
        explorer.getTreeItemByLabel('attribute2').should('have.length', 2);
      });
    });
  });
});
