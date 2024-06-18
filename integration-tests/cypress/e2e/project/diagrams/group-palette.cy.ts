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
import { Explorer } from '../../../workbench/Explorer';
import { Diagram } from '../../../workbench/Diagram';
import { Flow } from '../../../usecases/Flow';
import { Papaya } from '../../../usecases/Papaya';
import { Details } from '../../../workbench/Details';

const projectName = 'Cypress - group palette';
describe('Diagram - group palette', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography', 'diagram');
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then the group palette is displayed when using multi selection', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('not.exist');
      diagram.getGroupPalette().should('exist');

      diagram.getNodes('diagram', 'DSP').click();
      diagram.getPalette().should('exist');
      diagram.getGroupPalette().should('not.exist');
    });

    it('Then the default tools are available', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Hide elements').should('exist');
      diagram.getGroupPalette().findByTestId('Fade elements').should('exist');
      diagram.getGroupPalette().findByTestId('Pin elements').should('exist');
    });

    it('Then the distribute elements tools are available', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('expand').click();
      diagram.getGroupPalette().findByTestId('Align left').should('exist');
      diagram.getGroupPalette().findByTestId('Align center').should('exist');
      diagram.getGroupPalette().findByTestId('Align right').should('exist');
      diagram.getGroupPalette().findByTestId('Align bottom').should('exist');
      diagram.getGroupPalette().findByTestId('Align top').should('exist');
      diagram.getGroupPalette().findByTestId('Justify horizontally').should('exist');
      diagram.getGroupPalette().findByTestId('Justify vertically').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in row').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in column').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in grid').should('exist');
      diagram.getGroupPalette().findByTestId('Distribute horizontal spacing').should('exist');
      diagram.getGroupPalette().findByTestId('Distribute vertical spacing').should('exist');
      diagram.getGroupPalette().findByTestId('Make same size').should('exist');
    });

    it('Then the last distribute elements tool used is memorized', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Align left').should('exist');
      diagram.getGroupPalette().findByTestId('expand').click();
      diagram.getGroupPalette().findByTestId('Arrange in column').click();
      diagram.fitToScreen();
      diagram.getGroupPalette().should('not.exist');
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Align left').should('not.exist');
      diagram.getGroupPalette().findByTestId('Arrange in column').should('exist');
    });

    it('Then during multi selection the connection handles are not compute', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.fitToScreen();
      explorer.select('Wifi');
      cy.getByTestId('creationhandle-top').should('exist');
      explorer.select('Central_Unit', true);
      cy.getByTestId('creationhandle-top').should('not.exist');
    });
  });

  context('Given a papaya studio', () => {
    let studioId: string = '';
    before(() => {
      new Papaya().createPapayaStudioProject().then((createdProjectData) => {
        studioId = createdProjectData.projectId;
      });
    });
    after(() => cy.deleteProject(studioId));
    context('When we create a Papaya instance', () => {
      let instanceId: string = '';
      beforeEach(() => {
        new Papaya().createPapayaInstanceProject(projectName).then((createdProjectData) => {
          instanceId = createdProjectData.projectId;
          new Project().visit(instanceId);
        });
        const explorer = new Explorer();
        explorer.expand('Others...');
      });
      afterEach(() => cy.deleteProject(instanceId));

      it('Then distribute tool are not displayed on list child element', () => {
        const explorer = new Explorer();
        const details = new Details();
        const diagram = new Diagram();
        explorer.createObject('Root', 'Components Component');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('component{enter}');
        explorer.createObject('component', 'Packages Package');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('package{enter}');
        explorer.createObject('component', 'Packages Package');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('package_bis{enter}');
        explorer.createObject('package', 'Types Class');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('class{enter}');
        explorer.createObject('class', 'Attributes Attribute');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('attribute{enter}');
        explorer.createObject('class', 'Operations Operation');
        details.getTextField('Name').invoke('text').should('eq', '');
        details.getTextField('Name').type('operation{enter}');
        explorer.createRepresentation('Root', 'Diagram', 'diagram');
        diagram.fitToScreen();
        explorer.select('attribute');
        explorer.select('operation', true);
        diagram.getNodes('diagram', 'attribute').click();
        diagram.getGroupPalette().should('exist');
        diagram.getGroupPalette().findByTestId('Align left').should('not.exist');
        explorer.select('class');
        explorer.select('attribute', true);
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'attribute').click();
        diagram.getGroupPalette().should('exist');
        diagram.getGroupPalette().findByTestId('Align left').should('not.exist');
        explorer.select('package');
        explorer.select('package_bis', true);
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'package_bis').click();
        diagram.getGroupPalette().should('exist');
        diagram.getGroupPalette().findByTestId('Align left').should('exist');
        explorer.select('attribute');
        explorer.select('package', true);
        diagram.fitToScreen();
        diagram.getNodes('diagram', 'attribute').click();
        diagram.getGroupPalette().should('exist');
        diagram.getGroupPalette().findByTestId('Align left').should('not.exist');
      });
    });
  });
});
