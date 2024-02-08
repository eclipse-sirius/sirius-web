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

import { Project } from '../../../pages/Project';
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - Direct edit label', () => {
  context('Given a view with only one node with a direct edit tool and one edge with direct edit tool', () => {
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
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.expand('Entity1 Node');
            explorer.expand('NodePalette');
            explorer.delete('Edit Label');
            explorer.select('LinkedTo Edge');
            new Details().getTextField('Center Label Expression').type('Edge center{enter}');
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

          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then we cannot perform the direct edition of the node without the direct edit tool', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.getTreeItemByLabel('Entity1').click();

        const details = new Details();
        details.getTextField('Name').type('Entity1{Enter}');

        const diagram = new Diagram();
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'Entity1').click();
        diagram.getPalette().should('exist');
        cy.getByTestId('Edit - Tool').should('not.exist');

        diagram.getNodes('diagram', 'Entity1').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
        cy.getByTestId('name-edit').should('not.exist');
      });

      it('Then we can perform the edition of the label of the node with the direct edit tool', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity2s Entity2');
        explorer.getTreeItemByLabel('Entity2').click();

        const details = new Details();
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'Entity2').click();
        diagram.getPalette().should('exist');
        cy.getByTestId('Edit - Tool').should('exist');

        diagram.getNodes('diagram', 'Entity2').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
        cy.getByTestId('name-edit').should('exist');
      });

      it('Then during edit triggering escape cancelled the current edition', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity2s Entity2');
        explorer.getTreeItemByLabel('Entity2').click();

        const details = new Details();
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'Entity2').click();
        diagram.getPalette().should('exist');

        cy.getByTestId('Edit - Tool').click();
        cy.getByTestId('name-edit').should('exist').type('test{esc}');
        diagram.getNodes('diagram', 'Entity2').should('exist');
        diagram.getNodes('diagram', 'test').should('not.exist');
      });

      it('Then we can set the correct name when typing directly', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity2s Entity2');
        const details = new Details();
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.getNodes('diagram', 'Entity2').type('NewName{enter}');
        diagram.getNodes('diagram', 'NewName').should('exist');
      });

      it('Then we can use direct edit on edge', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Root', 'Entity2s Entity2');
        explorer.select('Entity2');
        const details = new Details();
        details.getTextField('Name').type('Entity2');
        explorer.select('Entity1');
        details.openReferenceWidgetOptions('Linked To');
        details.selectReferenceWidgetOption('Entity2');
        const diagram = new Diagram();
        explorer.select('Root');
        diagram.fitToScreen();
        cy.getByTestId('Label - Edge center').click();
        diagram.getPalette().should('exist');
        cy.getByTestId('Edit - Tool').should('exist');
        cy.getByTestId('Label - Edge center').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
        cy.getByTestId('name-edit').should('exist');
      });

      it('Then during the direct edition, the palette is hidden', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity2s Entity2');
        explorer.getTreeItemByLabel('Entity2').click();

        const details = new Details();
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'Entity2').click();
        diagram.getPalette().should('exist');
        cy.getByTestId('Edit - Tool').should('exist').click();
        cy.getByTestId('name-edit').should('exist');
        diagram.getPalette().should('not.exist');
        cy.getByTestId('name-edit').type('Entity Entity2{enter}');
        cy.getByTestId('name-edit').should('not.exist');
      });
    });
  });
  context('Given a view without direct edit tool', () => {
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
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.select('LinkedTo Edge');
            new Details().getTextField('Center Label Expression').type('Edge center{enter}');
            explorer.expand('LinkedTo Edge');
            explorer.expand('EdgePalette');
            explorer.delete('Edit Label');
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

          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then we can not use direct edit on edge', () => {
        const explorer = new Explorer();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Root', 'Entity2s Entity2');
        explorer.select('Entity2');
        const details = new Details();
        details.getTextField('Name').type('Entity2');
        explorer.select('Entity1');
        details.openReferenceWidgetOptions('Linked To');
        details.selectReferenceWidgetOption('Entity2');
        const diagram = new Diagram();
        explorer.select('Root');
        diagram.fitToScreen();
        cy.getByTestId('Label - Edge center').click();
        diagram.getPalette().should('exist');
        cy.getByTestId('Edit - Tool').should('not.exist');
        cy.getByTestId('Label - Edge center').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
        cy.getByTestId('name-edit').should('not.exist');
      });
    });
  });
});
