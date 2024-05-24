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

      it('Then check edges start on the ellipse layout', () => {
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
        diagram.getEdgePaths('diagram').should('have.length', 2);
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
  context('Given a studio template with border node', () => {
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
          explorer.createObject(domainName, 'Entity');
          details.getTextField('Name').should('have.value', 'NewEntity');
          details.getTextField('Name').type('{selectAll}Border{enter}');
          explorer.createObject('Entity1', 'Relation');
          details.getCheckBox('Containment').check();
          details.getTextField('Name').type('{selectAll}borders{enter}');
          details.openReferenceWidgetOptions('Target Type');
          details.selectReferenceWidgetOption('Border');
          explorer.createObject('Entity1', 'Relation');
          details.getCheckBox('Containment').check();
          details.getTextField('Name').type('{selectAll}entity2s{enter}');
          details.openReferenceWidgetOptions('Target Type');
          details.selectReferenceWidgetOption('Entity2');
          explorer.createObject('Border', 'Relation');
          details.openReferenceWidgetOptions('Target Type');
          details.selectReferenceWidgetOption('Entity2');
          details.getTextField('Name').type('{selectAll}entity2{enter}');

          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);
          explorer.expand('Entity1 Node');
          details.openReferenceWidgetOptions('Reused Child Node Descriptions');
          details.selectReferenceWidgetOption('Entity2 Node');
          details.getTextField('Default Width Expression').type('300{enter}');
          details.getTextField('Default Height Expression').type('300{enter}');
          explorer.createObject('Entity1 Node', 'Border node');
          details.getTextField('Domain Type').should('have.value', '');
          details.getTextField('Domain Type').type(`{selectAll}${domainName}::Border{enter}`);
          details.getTextField('Default Width Expression').type('25{enter}');
          details.getTextField('Default Height Expression').type('25{enter}');
          explorer.createObject(`${domainName} Diagram Description`, 'Edge Description');
          details.openReferenceWidgetOptions('Source Node Descriptions');
          details.selectReferenceWidgetOption('Border node');
          details.openReferenceWidgetOptions('Target Node Descriptions');
          details.selectReferenceWidgetOption('Entity2 Node');
          details.getTextField('Source Nodes Expression').type('aql:self');
          details.getTextField('Target Nodes Expression').type('{selectAll}aql:self.entity2');
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
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it.skip('Then check edge handles for border node are correctly positioned', () => {
        const explorer = new Explorer();
        const details = new Details();
        const diagram = new Diagram();

        explorer.createObject('Root', 'Entity2s Entity2');
        details.getTextField('Name').type('Entity2{Enter}');
        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Entity1', 'Borders Border');
        explorer.select('Border');
        details.openReferenceWidgetOptions('Entity2');
        details.selectReferenceWidgetOption('Entity2');
        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        diagram.arrangeAll();
        diagram.fitToScreen();
        diagram.getEdgePaths('diagram').should('have.length', 1);
        diagram
          .getEdgePaths('diagram')
          .eq(0)
          .invoke('attr', 'd')
          .then((dValue) => {
            expect(diagram.roundSvgPathData(dValue ?? '')).to.equal(
              'M490.00L505.00Q510.00L510.00Q510.00L171.00L151.00'
            );
          });
        explorer.delete('diagram');
        explorer.createObject('Entity1', 'Entity2s Entity2');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Child{Enter}');
        explorer.select('Border');
        details.deleteReferenceWidgetOption('Entity2', 'Entity2');
        details.openReferenceWidgetOptions('Entity2');
        details.selectReferenceWidgetOption('Child');
        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        diagram.arrangeAll();
        diagram.fitToScreen();
        diagram.getEdgePaths('diagram').should('have.length', 1);
        diagram
          .getEdgePaths('diagram')
          .eq(0)
          .invoke('attr', 'd')
          .then((dValue) => {
            expect(diagram.roundSvgPathData(dValue ?? '')).to.equal(
              'M465.00L445.00L402.00Q397.00L397.00Q397.00L349.00L329.00'
            );
          });
      });
    });
  });
});
