/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Flow } from '../../../usecases/Flow';
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Explorer } from '../../../workbench/Explorer';
import { Form } from '../../../workbench/Form';

const createFormWithWidgetRef = (domainType: string, name: string, reference: string) => {
  const explorer = new Explorer();
  const details = new Details();
  explorer.createObject('View', 'descriptions-FormDescription');
  explorer.select('New Form Description');
  details.getTextField('Domain Type').type(domainType);
  details.getTextField('Name').type(`{selectall}${name}{enter}`);
  details.getTextField('Title Expression').type(`{selectall}${name}{enter}`);
  explorer.expandWithDoubleClick(name);
  explorer.expandWithDoubleClick('PageDescription');
  explorer.createObject('GroupDescription', 'children-ReferenceWidgetDescription');
  details.getTextField('Reference Name Expression').should('exist');
  details.getTextField('Label Expression').type('Test Widget Reference');
  details.getTextField('Reference Name Expression').type(`${reference}{enter}`);
  explorer.collapseWithDoubleClick(name);
};
describe('Forms Widget-reference', () => {
  context('Given a blank studio template', () => {
    let studioProjectId: string = '';
    let flowProjectId: string = '';

    before(() =>
      new Studio().createBlankStudioProjectWithView().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('ViewDocument');
        createFormWithWidgetRef('flow::DataFlow', 'WidgetRefMonoValue', 'target');
        createFormWithWidgetRef('flow::CompositeProcessor', 'WidgetRefMultiValueNonContainment', 'incomingFlows');
        createFormWithWidgetRef('flow::System', 'WidgetRefContainment', 'powerOutputs');
      })
    );
    after(() => cy.deleteProject(studioProjectId));

    context('When we interact with the widget reference in flow', () => {
      beforeEach(() =>
        new Flow().createFlowProject().then((createdProjectData) => {
          flowProjectId = createdProjectData.projectId;
          new Project().visit(flowProjectId);
        })
      );

      afterEach(() => cy.deleteProject(flowProjectId));
      it('Then widget reference mono-valued is available', () => {
        const explorer = new Explorer();
        const form = new Form();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.expandWithDoubleClick('DataSource1');
        explorer.createRepresentation('standard', 'WidgetRefMonoValue', 'WidgetRefMonoValue');
        form.getForm().should('exist');
        form.getWidget('Test Widget Reference').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').click();

        cy.getByTestId('browse-modal').findByTestId('tree-root-elements').should('exist');
        cy.getByTestId('browse-modal').findByTestId('CompositeProcessor1').should('exist');
        cy.getByTestId('browse-modal').findByTestId('selected').findByTestId('Processor1').should('exist');

        cy.getByTestId('browse-modal').findByTestId('CompositeProcessor1').click();
        cy.getByTestId('select-value').click();

        cy.getByTestId('reference-value-CompositeProcessor1').should('exist');
        cy.getByTestId('reference-value-Processor1').should('not.exist');

        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-CompositeProcessor1').should('not.exist');
        const dataTransferCompositeProcessor = new DataTransfer();
        explorer
          .getTreeItemByLabel('CompositeProcessor1')
          .trigger('dragstart', { dataTransfer: dataTransferCompositeProcessor });
        form.getWidget('Test Widget Reference').trigger('drop', { dataTransfer: dataTransferCompositeProcessor });
        form.getWidgetElement('Test Widget Reference', 'reference-value-CompositeProcessor1').should('exist');
        form.getWidgetElement('Test Widget Reference', 'reference-value-none').should('not.exist');
        form.getWidget('Test Widget Reference').click();
        cy.getByTestId('option-Processor1').should('exist');
        cy.getByTestId('option-Processor1').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-CompositeProcessor1').should('not.exist');
        form.getWidgetElement('Test Widget Reference', 'reference-value-Processor1').should('exist');
      });

      it('Then widget reference multi-valued is available', () => {
        const explorer = new Explorer();
        const form = new Form();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.createRepresentation(
          'CompositeProcessor1',
          'WidgetRefMultiValueNonContainment',
          'WidgetRefMultiValueNonContainment'
        );
        form.getForm().should('exist');
        form.getWidget('Test Widget Reference').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').click();
        cy.getByTestId('transfer-modal').findByTestId('tree-root-elements').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('Flow').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('selected-items-list').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('Flow').click();

        cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
        cy.getByTestId('transfer-modal').findByTestId('standard').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('standard').click();

        cy.getByTestId('transfer-modal').findByTestId('move-right').click();
        cy.getByTestId('selected-items-list').findByTestId('standard').should('exist');
        cy.getByTestId('selected-items-list').findByTestId('standard').click();

        const dataTransferStandard = new DataTransfer();
        cy.getByTestId('transfer-modal')
          .findByTestId('tree-root-elements')
          .findByTestId('standard')
          .trigger('dragstart', { dataTransfer: dataTransferStandard });
        cy.getByTestId('transfer-modal')
          .findByTestId('selected-items-list')
          .trigger('drop', { dataTransfer: dataTransferStandard });
        cy.getByTestId('transfer-modal').findByTestId('selected-items-list').findByTestId('standard').should('exist');
        cy.getByTestId('close-transfer-modal').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-standard').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-standard').should('not.exist');

        explorer.expandWithDoubleClick('DataSource1');
        const dataTransferStandardExplorer = new DataTransfer();
        explorer.getTreeItemByLabel('standard').trigger('dragstart', { dataTransfer: dataTransferStandardExplorer });
        form.getWidget('Test Widget Reference').trigger('drop', { dataTransfer: dataTransferStandardExplorer });

        form.getWidgetElement('Test Widget Reference', 'reference-value-standard').should('exist');
        explorer.createObject('DataSource1', 'outgoingFlows-DataFlow');

        form.getWidget('Test Widget Reference').click();
        cy.getByTestId('option-standard').should('not.exist');
        cy.getByTestId('option-unused').should('exist');

        cy.getByTestId('option-unused').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-standard').should('exist');
        form.getWidgetElement('Test Widget Reference', 'reference-value-unused').should('exist');
      });

      it('Then widget reference containment is available', () => {
        const explorer = new Explorer();
        const form = new Form();
        explorer.expandWithDoubleClick('Flow');
        explorer.createRepresentation('NewSystem', 'WidgetRefContainment', 'WidgetRefContainment');
        form.getForm().should('exist');
        form.getWidget('Test Widget Reference').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').should('exist');
        explorer.getTreeItemByLabel('1000').should('not.exist');

        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').click();
        cy.getByTestId('create-modal').should('exist');
        cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
        cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
        cy.getByTestId('childCreationDescription')
          .children('[role="combobox"]')
          .invoke('text')
          .should('have.length.gt', 1);
        cy.getByTestId('childCreationDescription').click();
        cy.getByTestId('childCreationDescription').get('[data-value]').should('have.length', 1);
        cy.getByTestId('childCreationDescription')
          .get('[data-value="powerOutputs-PowerOutput"]')
          .should('exist')
          .click();
        cy.getByTestId('create-modal').findByTestId('create-object').click();
        cy.getByTestId('reference-value-1000').should('exist');
        explorer.getTreeItemByLabel('1000').should('exist');

        // click on the delete icon on the Chip
        cy.get('.MuiChip-deleteIcon').should('exist').click();

        cy.getByTestId('confirmation-dialog').should('be.visible');
        cy.getByTestId('confirmation-dialog-button-cancel').click();
        cy.getByTestId('confirmation-dialog').should('not.exist');

        cy.getByTestId('reference-value-1000').should('exist');
        cy.get('.MuiChip-deleteIcon').should('exist').click();

        cy.getByTestId('confirmation-dialog').should('be.visible');
        cy.getByTestId('confirmation-dialog-button-ok').click();
        cy.getByTestId('confirmation-dialog').should('not.exist');

        cy.getByTestId('reference-value-1000').should('not.exist');
        explorer.getTreeItemByLabel('1000').should('not.exist');

        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').click();
        cy.getByTestId('create-modal').should('exist');
        cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
        cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
        cy.getByTestId('childCreationDescription')
          .children('[role="combobox"]')
          .invoke('text')
          .should('have.length.gt', 1);
        cy.getByTestId('childCreationDescription').click();
        cy.getByTestId('childCreationDescription').get('[data-value]').should('have.length', 1);
        cy.getByTestId('childCreationDescription')
          .get('[data-value="powerOutputs-PowerOutput"]')
          .should('exist')
          .click();
        cy.getByTestId('create-modal').findByTestId('create-object').click();
        cy.getByTestId('reference-value-1000').should('exist');
        explorer.getTreeItemByLabel('1000').should('exist');

        // click on the delete icon on the Widget Reference
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').click();

        cy.getByTestId('confirmation-dialog').should('be.visible');
        cy.getByTestId('confirmation-dialog-button-cancel').click();
        cy.getByTestId('confirmation-dialog').should('not.exist');

        cy.getByTestId('reference-value-1000').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').click();

        cy.getByTestId('confirmation-dialog').should('be.visible');
        cy.getByTestId('confirmation-dialog-button-ok').click();
        cy.getByTestId('confirmation-dialog').should('not.exist');

        cy.getByTestId('reference-value-1000').should('not.exist');
        explorer.getTreeItemByLabel('1000').should('not.exist');
      });

      it('Then widget reference non containment is available', () => {
        const explorer = new Explorer();
        const form = new Form();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.createRepresentation(
          'CompositeProcessor1',
          'WidgetRefMultiValueNonContainment',
          'WidgetRefMultiValueNonContainment'
        );
        form.getForm().should('exist');
        form.getWidget('Test Widget Reference').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').should('exist').click();
        cy.getByTestId('create-modal').should('exist');
        cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('exist');
        cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
        cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('exist');

        cy.getByTestId('create-modal').findByTestId('Flow').should('exist');
        cy.getByTestId('create-modal').findByTestId('Flow').click();
        cy.getByTestId('create-modal').findByTestId('expand-all').click();

        cy.getByTestId('create-modal').findByTestId('NewSystem').should('exist');
        cy.getByTestId('create-modal').findByTestId('DataSource1').should('exist');
        cy.getByTestId('create-modal').findByTestId('CompositeProcessor1').should('exist');
        cy.getByTestId('create-modal').findByTestId('CompositeProcessor1').click();

        cy.getByTestId('childCreationDescription')
          .children('[role="combobox"]')
          .invoke('text')
          .should('have.length.gt', 1);
        cy.getByTestId('childCreationDescription').click();
        cy.getByTestId('childCreationDescription')
          .get('[data-value]')
          .should('have.length', 2)
          .get('[data-value="elements-DataFlow"]')
          .should('exist')
          .click();
        cy.getByTestId('create-modal').findByTestId('create-object').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-unused').should('exist');
        explorer.expandWithDoubleClick('DataSource1');
        explorer.getTreeItemByLabel('unused').should('exist');
      });
    });
  });
});
