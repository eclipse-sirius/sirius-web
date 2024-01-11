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

import { Explorer } from '../../../workbench/Explorer';
import { Flow } from '../../../usecases/Flow';
import { Project } from '../../../pages/Project';
import { Details } from '../../../workbench/Details';
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';

describe('Details Widget-reference', () => {
  context('Given a flow project', () => {
    let flowProjectId: string = '';

    before(() =>
      new Flow().createFlowProject().then((createdProjectData) => {
        flowProjectId = createdProjectData.projectId;
        new Project().visit(flowProjectId);
      })
    );
    after(() => cy.deleteProject(flowProjectId));

    it('then diagnostic messages are display on widget reference', () => {
      const explorer = new Explorer();
      explorer.expand('FlowNewModel');
      explorer.expand('NewSystem');
      explorer.expand('DataSource1');
      explorer.select('standard');
      const details = new Details();
      details.getReferenceWidget('Target').find('p[class*="Mui-error"]').should('not.exist');
      details.getReferenceWidget('Target').findByTestId('Target-clear').click();
      details.getReferenceWidget('Target').find('p[class*="Mui-error"]').should('exist');
    });
  });
  context('Given a studio', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() => {
      cy.createProjectFromTemplate('studio-template').then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;
          new Project().visit(projectId);
          const explorer = new Explorer();
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
          });
        }
      });
    });

    after(() => cy.deleteProject(studioProjectId));
    it('check widget reference click navigation to filter tree item', () => {
      const explorer = new Explorer();
      explorer.expand(domainName);
      explorer.expand('Entity1');
      explorer.expand('Entity2');
      explorer.select('linkedTo');
      cy.getByTestId('reference-value-Entity2').should('exist');

      explorer.getTreeItemByLabel('linkedTo').type('{ctrl+f}');
      cy.getByTestId('filterbar-textfield').type('Entity1');
      cy.getByTestId('filterbar-filter-button').click();
      explorer.getTreeItemByLabel('Entity2').should('not.exist');

      cy.getByTestId('reference-value-Entity2').click();
      cy.getByTestId('page-tab-Entity2').should('exist');
      cy.getByTestId('filterbar-close-button').click();
      explorer.getSelectedTreeItems().should('have.length', 1);
      explorer.getSelectedTreeItems().contains('Entity2').should('exist');
    });
  });
});
