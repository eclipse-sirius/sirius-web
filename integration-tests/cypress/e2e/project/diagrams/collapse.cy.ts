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

import { Project } from '../../../pages/Project';
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - collapsible node', () => {
  context('Given a studio with a collapsible node', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() => {
      cy.createProjectFromTemplate('studio-template').then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;

          const project = new Project();
          project.visit(projectId);
          project.disableDeletionConfirmationDialog();

          const explorer = new Explorer();
          const details = new Details();
          explorer.expand('DomainNewModel');
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand(domainName);
            explorer.createObject('Entity1', 'Relation');
            details.getCheckBox('Containment').check();
            details.openReferenceWidgetOptions('Target Type');
            details.selectReferenceWidgetOption('Entity2');
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.select('Entity1 Node');
            details.getCheckBox('Collapsible').check();
            details.openReferenceWidgetOptions('Reused Child Node Descriptions');
            details.selectReferenceWidgetOption('Entity2 Node');
            explorer.expand('Entity1 Node');
            explorer.expand('aql:self.name');
            explorer.select('InsideLabelStyle');
            details.getCheckBox('With Header').check();
            details.getCheckBox('Display Header Separator').check();
          });
        }
      });
    });

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then a collapsed node does not display its header line', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Entity1', 'Relation Entity2');
        explorer.select('Entity1');
        details.getTextField('Name').type('Entity1{enter}');
        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        diagram.fitToScreen();
        diagram
          .getNodes('diagram', 'Entity1')
          .findByTestId('Label - Entity1')
          .should('have.css', 'border-bottom', '1px solid rgb(51, 176, 195)');
        diagram.getNodes('diagram', 'Entity1').findByTestId('Label - Entity1').click();
        diagram.getPalette().should('exist');
        diagram.getPalette().findByTestId('Collapse - Tool').click();
        diagram
          .getNodes('diagram', 'Entity1')
          .findByTestId('Label - Entity1')
          .should('have.css', 'border-bottom-width', '0px');
      });
    });
  });
});
