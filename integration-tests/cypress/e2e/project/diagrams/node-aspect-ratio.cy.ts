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
import { Studio } from '../../../usecases/Studio';
import { Project } from '../../../pages/Project';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';

describe('Diagram - Node aspect ratio', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    beforeEach(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expand(`${domainName}`);
        });
      })
    );

    afterEach(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then node default size is used for node creation', () => {
        const explorer = new Explorer();
        explorer.expand('ViewNewModel');
        explorer.expand('View');
        explorer.expand(`${domainName} Diagram Description`);
        explorer.select('Entity1 Node');
        const details = new Details();
        details.getTextField('Default Width Expression').type('200');
        details.getTextField('Default Height Expression').type('200');
        details.getCheckBox('Keep Aspect Ratio').check();

        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });

        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.select('Entity1');
        details.getTextField('Name').type('Node{enter}');

        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400); // wait for automatique zoom to apply

        const diagram = new Diagram();
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'Node', 'width').then((nodeWidth) => {
            expect(nodeWidth / scale).to.approximately(200, 2);
          });
          diagram.getNodeCssValue('diagram', 'Node', 'height').then((nodeHeight) => {
            expect(nodeHeight / scale).to.approximately(200, 2);
          });
        });

        explorer.select('Node');
        details.getTextField('Name').type(' with larger label but should keep aspect ratio{enter}');
        diagram.getNodes('diagram', 'Node with larger label but should keep aspect ratio').then(($node) => {
          const nodeWidth = $node.width() ?? 1;
          const nodeHeight = $node.height() ?? 0;
          expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
        });
      });

      context('When we create a new sub node to domain', () => {
        beforeEach(() => {
          const explorer = new Explorer();
          const details = new Details();

          explorer.createObject(domainName, 'Entity');
          details.getTextField('Name').should('have.value', 'NewEntity');
          details.getTextField('Name').clear().type('SubNode{enter}');
          explorer.createObject('Entity1', 'Relation');
          details.getCheckBox('Containment').check();
          details.openReferenceWidgetOptions('Target Type');
          details.selectReferenceWidgetOption('SubNode');

          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);

          explorer.createObject(`${domainName} Diagram Description`, 'Node Description');

          details.getTextField('Domain Type').should('have.value', '');
          details.getTextField('Domain Type').type(`${domainName}::SubNode`);

          explorer.select('Entity1 Node');
          details.getTextField('Default Width Expression').type('200');
          details.getTextField('Default Height Expression').type('200');
          details.getCheckBox('Keep Aspect Ratio').check();
          details.openReferenceWidgetOptions('Reused Child Node Descriptions');
          details.selectReferenceWidgetOption('Node');
        });

        it('Then node keep aspect ratio with large sub node', () => {
          const explorer = new Explorer();
          const details = new Details();

          explorer.select('Node');

          details.getTextField('Default Width Expression').type('400');
          details.getTextField('Default Height Expression').type('20{enter}');

          const studio = new Studio();
          studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
            instanceProjectId = res.projectId;
          });

          explorer.createObject('Root', 'Entity1s Entity1');
          explorer.select('Entity1');
          details.getTextField('Name').type('Node{enter}');

          explorer.createObject('Node', 'Relation Sub Node');

          explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');

          new Diagram().getNodes('diagram', 'Node').then(($node) => {
            const nodeWidth = $node.width() ?? 1;
            const nodeHeight = $node.height() ?? 0;
            expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
          });
        });

        it('Then node keep aspect ratio with higher sub node', () => {
          const explorer = new Explorer();
          const details = new Details();

          explorer.select('Node');
          details.getTextField('Default Width Expression').type('20');
          details.getTextField('Default Height Expression').type('400{enter}');

          const studio = new Studio();
          studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
            instanceProjectId = res.projectId;
          });

          explorer.createObject('Root', 'Entity1s Entity1');
          explorer.select('Entity1');
          details.getTextField('Name').type('Node{enter}');

          explorer.createObject('Node', 'Relation Sub Node');

          explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');

          new Diagram().getNodes('diagram', 'Node').then(($node) => {
            const nodeWidth = $node.width() ?? 1;
            const nodeHeight = $node.height() ?? 0;
            expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
          });
        });
      });
    });
  });
});
