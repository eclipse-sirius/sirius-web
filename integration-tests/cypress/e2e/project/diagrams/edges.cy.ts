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
import { Studio } from '../../../usecases/Studio';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';

describe('Diagram - edges', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expand(`${domainName}`);
          explorer.createObject('Entity1', 'Relation');
          const details = new Details();
          details.getCheckBox('Containment').check();
          details.openReferenceWidgetOptions('Target Type');
          details.selectReferenceWidgetOption('Entity2');

          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);
          explorer.expand('Entity1 Node');
          details.openReferenceWidgetOptions('Reused Child Node Descriptions');
          details.selectReferenceWidgetOption('Entity2 Node');
          details.getTextField('Default Width Expression').type('300{enter}');
          details.getTextField('Default Height Expression').type('300{enter}');
        });
      })
    );

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

      it('Then check edge do not cross child node', () => {
        const explorer = new Explorer();
        const details = new Details();
        const diagram = new Diagram();

        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Entity1', 'Relation Entity2');

        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity2{Enter}');
        explorer.select('Entity1');
        details.openReferenceWidgetOptions('Linked To');
        details.selectReferenceWidgetOption('Entity2');
        diagram.fitToScreen();
        diagram.getEdgePaths('diagram').should('have.length', 1);
        diagram
          .getEdgePaths('diagram')
          .eq(0)
          .invoke('attr', 'd')
          .then((dValue) => {
            expect(diagram.roundSvgPathData(dValue ?? '')).to.equal('M150.00L150.00Q150.00L88.00Q83.00L83.00L83.00');
          });
      });
    });
  });
  context('Given a studio template with an ellipse style', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(createdProjectData.projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        const details = new Details();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;

          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);
          explorer.expand('Entity1 Node');
          details.getTextField('Default Width Expression').type('300{enter}');
          details.getTextField('Default Height Expression').type('50{enter}');
          explorer.delete('RectangularNodeStyleDescription');
          explorer.createObject('Entity1 Node', 'Ellipse Node Style Description');
        });
      })
    );

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

      it('Then check edge do not cross child node', () => {
        const explorer = new Explorer();
        const details = new Details();
        const diagram = new Diagram();

        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Root', 'Entity2s Entity2');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity2{Enter}');
        explorer.createObject('Root', 'Entity2s Entity2');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity2.bis{Enter}');
        explorer.select('Entity1');
        details.openReferenceWidgetOptions('Linked To');
        details.selectReferenceWidgetOption('Entity2');
        details.getReferenceWidgetSelectedValue('Linked To', 'Entity2').should('exist');
        details.openReferenceWidgetOptions('Linked To');
        details.selectReferenceWidgetOption('Entity2.bis');
        details.getReferenceWidgetSelectedValue('Linked To', 'Entity2.bis').should('exist');
        diagram.arrangeAll();
        diagram.fitToScreen();
        diagram.getEdgePaths('diagram').should('have.length', 2);
        diagram
          .getEdgePaths('diagram')
          .eq(0)
          .invoke('attr', 'd')
          .then((dValue) => {
            expect(diagram.roundSvgPathData(dValue ?? '')).to.equal(
              'M300.13L320.13L354.89Q355.56L355.56Q355.56L391.00L411.00'
            );
          });
      });
    });
  });
});
