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

describe('Diagram - node style', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(createdProjectData.projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          const details = new Details();
          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);
          explorer.expand('Entity1 Node');
          explorer.select('RectangularNodeStyleDescription');
          details.openReferenceWidgetOptions('Border Color');
          details.selectReferenceWidgetOption('orange 500');
          explorer.collapse('Entity1 Node');
          explorer.expand('Entity2 Node');
          explorer.delete('RectangularNodeStyleDescription');
          explorer.createObject('Entity2 Node', 'Style Image');
          explorer.select('ImageNodeStyleDescription');
          details.selectValue('Shape', 'fan');
          details.openReferenceWidgetOptions('Border Color');
          details.selectReferenceWidgetOption('cyan 500');
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

      it('Then check color border is properly applied', () => {
        const explorer = new Explorer();
        const details = new Details();

        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Root', 'Entity2s Entity2');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity2{Enter}');
        explorer.select('diagram');
        cy.getByTestId('FreeForm - Entity1').invoke('css', 'border-color').should('eq', 'rgb(255, 152, 0)');
        cy.getByTestId('FreeForm - Entity2').invoke('css', 'border-color').should('eq', 'rgb(0, 188, 212)');
      });
    });
  });
});
