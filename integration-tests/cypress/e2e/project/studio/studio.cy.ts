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
import { Studio } from '../../../usecases/Studio';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';

describe('/projects/:projectId/edit - Studio', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    beforeEach(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expand(`${domainName}`);
        });
        project.disableDeletionConfirmationDialog();
      })
    );

    afterEach(() => cy.deleteProject(studioProjectId));

    it.skip('Check the label position of an empty edge', () => {
      cy.getByTestId('onboard-open-Domain').dblclick();
      // clear the edge label value
      cy.getByTestId('Label - entity2s [0..*]').click();
      cy.getByTestId('Close - Tool').click();
      cy.getByTestId('Name').type('{selectAll}{backspace}');
      cy.getByTestId('Optional').click();
      cy.getByTestId('Many').click();
      // arrange all
      cy.getByTestId('arrange-all').click();
      // wait for the arrange all to finish
      cy.wait(3000).then(() => {
        // the label should be translated 2 times
        cy.getByTestId('Label - ')
          .should('have.attr', 'transform')
          .then((value) => expect(value).to.match(/translate\(.*, .*\) translate\(.*, .*\)/));
      });
    });

    it('Check the new object domain list', () => {
      cy.getByTestId('DomainNewModel-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('domain').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
      cy.getByTestId('domain').click();
      cy.get('[data-value="http://www.eclipse.org/sirius-web/domain"]').should('exist');
      cy.getByTestId('domain').get('[data-value="http://www.eclipse.org/sirius-web/view"]').should('exist');
      cy.getByTestId('domain').get('[data-value="http://www.obeo.fr/dsl/designer/sample/flow"]').should('exist');
    });

    it('Check the DiagramPalette toolSection creation', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.getByTestId('DiagramPalette').should('exist');
      cy.getByTestId('DiagramPalette-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Diagram Tool Section"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Tool Section').should('exist');
      cy.getByTestId('Tool Section-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Node Tool"]').should('exist');
      cy.get('[data-value="Edge Tool"]').should('not.exist');
    });

    it('Check the NodePalette toolSection creation', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.getByTestId('Entity1 Node').dblclick();
      cy.getByTestId('NodePalette-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Node Tool Section"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Tool Section').should('exist');
      cy.getByTestId('Tool Section-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Node Tool"]').should('exist');
      cy.get('[data-value="Edge Tool"]').should('exist');
      cy.get('[data-value="Source Edge End Reconnection Tool"]').should('not.exist');
    });

    it('Check the EdgePalette toolSection creation', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.getByTestId('LinkedTo Edge').dblclick();
      cy.getByTestId('EdgePalette-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Edge Tool Section"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Tool Section').should('exist');
      cy.getByTestId('Tool Section-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
      cy.getByTestId('childCreationDescription').click();
      cy.get('[data-value="Node Tool"]').should('exist');
      cy.get('[data-value="Edge Tool"]').should('not.exist');
      cy.get('[data-value="Source Edge End Reconnection Tool"]').should('not.exist');
      cy.get('[data-value="Target Edge End Reconnection Tool"]').should('not.exist');
    });

    it.skip('Check the precondition on tools', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.getByTestId('DiagramPalette-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Node Tool"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Precondition Expression').should('exist');
      cy.getByTestId('Precondition Expression').type('aql:self.eAllContents()->size()>0');
      cy.getByTestId('Name').clear();
      cy.getByTestId('Name').type('TestTool');

      cy.get('[title="Back to the homepage"]').click();
      cy.url().should('eq', Cypress.config().baseUrl + '/projects');
      cy.get('[title="Blank Studio"]').should('be.visible');
      cy.getByTestId('create').click();

      cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
      cy.getByTestId('name').should('be.visible');
      cy.getByTestId('name').type('Instance');
      cy.getByTestId('create-project').click();

      cy.getByTestId('empty').click();
      cy.getByTestId('Others...-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('domain').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
      cy.getByTestId('domain').click();
      cy.getByTestId('domain').get('[data-value^="domain://"]').should('exist').click();
      cy.getByTestId('create-object').click();

      cy.getByTestId('Root-more').click();
      cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
      cy.getByTestId('representationDescription')
        .children('[role="button"]')
        .invoke('text')
        .should('have.length.gt', 1);
      cy.getByTestId('representationDescription').click();
      cy.get('[data-testid$=" Diagram Description"]').should('exist').click();
      cy.getByTestId('name').clear();
      cy.getByTestId('name').type('Diagram');
      cy.getByTestId('create-representation').click();
      cy.getByTestId('Diagram').should('exist');
      cy.getByTestId('Diagram')
        .click('left')
        .then(() => {
          cy.getByTestId('New Entity1 - Tool').should('exist');
          cy.getByTestId('New Entity2 - Tool').should('exist');
          cy.getByTestId('TestTool - Tool').should('not.exist');
          cy.getByTestId('New Entity1 - Tool').click();
        });
      cy.getByTestId('Diagram')
        .click('center')
        .then(() => {
          cy.getByTestId('New Entity1 - Tool').should('exist');
          cy.getByTestId('New Entity2 - Tool').should('exist');
          cy.getByTestId('TestTool - Tool').should('exist');
        });
    });

    it('Check node style description has default colors', () => {
      const explorer = new Explorer();
      const details = new Details();
      explorer.expand('ViewNewModel');
      explorer.expand('View');
      explorer.expand(`${domainName} Diagram Description`);
      explorer.createObject(`${domainName} Diagram Description`, 'Node Description');
      explorer.expand('Node');
      explorer.select('RectangularNodeStyleDescription');
      details.getReferenceWidgetSelectedValue('Background', 'white').should('exist');
      details.getReferenceWidgetSelectedValue('Border Color', 'black').should('exist');
      explorer.delete('RectangularNodeStyleDescription');
      explorer.createObject('Node', 'Style Icon-Label');
      details.getReferenceWidgetSelectedValue('Background', 'white').should('exist');
      details.getReferenceWidgetSelectedValue('Border Color', 'black').should('exist');
    });

    it('Check new sub-node description has default colors', () => {
      cy.getByTestId('ViewNewModel-toggle').click();
      cy.getByTestId('View-toggle').click();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.get('[data-testid$=" Diagram Description-more"]').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Node Description"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Node-toggle').click();
      cy.getByTestId('Node-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Sub-node"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Sub-node-toggle').click();
      cy.getByTestId('RectangularNodeStyleDescription').eq(1).click();
      cy.getByTestId('Background').findByTestId('reference-value-white').should('exist');
      cy.getByTestId('Border Color').findByTestId('reference-value-black').should('exist');
    });

    it('Check new border-node description has default colors', () => {
      cy.getByTestId('ViewNewModel-toggle').click();
      cy.getByTestId('View-toggle').click();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.get('[data-testid$=" Diagram Description-more"]').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Node Description"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Node-toggle').click();
      cy.getByTestId('Node-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Border node"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Border node-toggle').click();
      cy.getByTestId('RectangularNodeStyleDescription').eq(1).click();
      cy.getByTestId('Background').findByTestId('reference-value-white').should('exist');
      cy.getByTestId('Border Color').findByTestId('reference-value-black').should('exist');
    });

    it('Check new style description on existing node has default colors', () => {
      cy.getByTestId('ViewNewModel-toggle').click();
      cy.getByTestId('View-toggle').click();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.getByTestId('Entity1 Node-toggle').click();
      cy.getByTestId('RectangularNodeStyleDescription-more').click();
      cy.getByTestId('delete').click();
      cy.getByTestId('Entity1 Node-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Style Rectangular"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('RectangularNodeStyleDescription').click();
      cy.getByTestId('Background').findByTestId('reference-value-white').should('exist');
      cy.getByTestId('Border Color').findByTestId('reference-value-black').should('exist');
    });

    it('Check edge style description has default colors', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.get('[data-testid$=" Diagram Description-more"]').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Edge Description"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Edge').dblclick();
      cy.getByTestId('EdgeStyle').click();

      cy.getByTestId('Color').findByTestId('reference-value-black').should('exist');
    });

    it('Check textfield widget style description has default colors', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Form Description"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('New Form Description').dblclick();
      cy.getByTestId('PageDescription').dblclick();
      cy.getByTestId('GroupDescription').dblclick();
      cy.getByTestId('GroupDescription-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription')
        .get('[data-value="Widgets Textfield Description"]')
        .should('exist')
        .click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('TextfieldDescription-more').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription')
        .get('[data-value="Style Textfield Description Style"]')
        .should('exist')
        .click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('TextfieldDescriptionStyle').click();

      cy.getByTestId('Background Color').findByTestId('reference-value-transparent').should('exist');
      cy.getByTestId('Foreground Color').findByTestId('reference-value-theme.palette.text.primary').should('exist');
      cy.getByTestId('Foreground Color').getByTestId('Foreground Color-clear').click();
      cy.getByTestId('Foreground Color').type('amber 500{downarrow}{enter}');
      cy.getByTestId('Foreground Color').findByTestId('reference-value-amber 500').should('exist');
    });

    it('Check node description has ratio related properties', () => {
      cy.getByTestId('ViewNewModel').dblclick();
      cy.getByTestId('View').dblclick();
      cy.get('[data-testid$=" Diagram Description"]').dblclick();
      cy.get('[data-testid$=" Diagram Description-more"]').click();
      cy.getByTestId('new-object').click();
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value="Node Description"]').should('exist').click();
      cy.getByTestId('create-object').click();
      cy.getByTestId('Node').click();
      cy.getByTestId('Default Width Expression').should('exist');
      cy.getByTestId('Default Height Expression').should('exist');
      cy.getByTestId('Keep Aspect Ratio').should('exist');
    });

    it('Check node description has arrange layout direction properties with undefined as default value', () => {
      const explorer = new Explorer();
      const details = new Details();
      explorer.expand('ViewNewModel');
      explorer.expand('View');
      explorer.select(`${domainName} Diagram Description`);
      details.getRadioOption('Arrange Layout Direction', 'UNDEFINED').should('be.checked');
      details.getRadioOption('Arrange Layout Direction', 'RIGHT').should('not.be.checked');
      details.getRadioOption('Arrange Layout Direction', 'DOWN').should('not.be.checked');
      details.getRadioOption('Arrange Layout Direction', 'LEFT').should('not.be.checked');
      details.getRadioOption('Arrange Layout Direction', 'UP').should('not.be.checked');
    });
  });
});
