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

import { Studio } from '../../../usecases/Studio';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';
import { Flow } from '../../../usecases/Flow';
import { Project } from '../../../pages/Project';
import { Form } from '../../../workbench/Form';
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';

const createFormWithWidgetRef = (domainType: string, name: string, reference: string) => {
  const explorer = new Explorer();
  const details = new Details();
  explorer.createObject('View', 'Form Description');
  explorer.select('New Form Description');
  details.getTextField('Domain Type').type(domainType);
  details.getTextField('Name').type(`{selectall}${name}{enter}`);
  details.getTextField('Title Expression').type(`{selectall}${name}{enter}`);
  explorer.expand(name);
  explorer.expand('PageDescription');
  explorer.createObject('GroupDescription', 'Widgets Reference Widget Description');
  details.getTextField('Reference Name Expression').should('exist');
  details.getTextField('Label Expression').type('Test Widget Reference');
  details.getTextField('Reference Name Expression').type(`${reference}{enter}`);
  explorer.collapse(name);
};
describe('Forms Widget-reference', () => {
  context('Given a blank studio template', () => {
    let studioProjectId: string = '';
    let flowProjectId: string = '';

    before(() =>
      new Studio().createBlankStudioProjectWithView().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        const explorer = new Explorer();
        explorer.expand('ViewDocument');
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
        explorer.expand('FlowNewModel');
        explorer.expand('NewSystem');
        explorer.expand('DataSource1');
        explorer.createRepresentation('standard', 'WidgetRefMonoValue', 'WidgetRefMonoValue');
        form.getForm().should('exist');
        form.getWidget('Test Widget Reference').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-clear').should('exist');
        form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-more').click();
        cy.getByTestId('browse-modal').findByTestId('tree-root-elements').should('exist');
        cy.getByTestId('browse-modal').findByTestId('CompositeProcessor1').should('exist');
        cy.getByTestId('browse-modal').findByTestId('DataSource1').should('not.exist');
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
        explorer.expand('FlowNewModel');
        explorer.expand('NewSystem');
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
        cy.getByTestId('transfer-modal').findByTestId('FlowNewModel').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('selected-items-list').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('FlowNewModel').click();
        cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
        cy.getByTestId('transfer-modal').findByTestId('standard').should('exist');
        cy.getByTestId('transfer-modal').findByTestId('standard').click();
        cy.getByTestId('transfer-modal').findByTestId('move-right').click();
        cy.getByTestId('selected-items-list').findByTestId('standard').should('exist');
        cy.getByTestId('selected-items-list').findByTestId('standard').click();
        cy.getByTestId('transfer-modal').findByTestId('move-left').click();
        cy.getByTestId('selected-items-list').findByTestId('standard').should('not.exist');
        const dataTransferStandard = new DataTransfer();
        cy.getByTestId('transfer-modal')
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
        explorer.expand('DataSource1');
        const dataTransferStandardExplorer = new DataTransfer();
        explorer.getTreeItemByLabel('standard').trigger('dragstart', { dataTransfer: dataTransferStandardExplorer });
        form.getWidget('Test Widget Reference').trigger('drop', { dataTransfer: dataTransferStandardExplorer });
        form.getWidgetElement('Test Widget Reference', 'reference-value-standard').should('exist');
        explorer.createObject('DataSource1', 'Data Flow');
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
        explorer.expand('FlowNewModel');
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
          .children('[role="button"]')
          .invoke('text')
          .should('have.length.gt', 1);
        cy.getByTestId('childCreationDescription').click();
        cy.getByTestId('childCreationDescription').get('[data-value]').should('have.length', 1);
        cy.getByTestId('childCreationDescription').get('[data-value="Power Output"]').should('exist').click();
        cy.getByTestId('create-modal').findByTestId('create-object').click();
        cy.getByTestId('reference-value-1000').should('exist');
        explorer.getTreeItemByLabel('1000').should('exist');
      });

      it('Then widget reference non containment is available', () => {
        const explorer = new Explorer();
        const form = new Form();
        explorer.expand('FlowNewModel');
        explorer.expand('NewSystem');
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
        cy.getByTestId('create-modal').findByTestId('FlowNewModel').should('exist');
        cy.getByTestId('create-modal').findByTestId('FlowNewModel').click();
        cy.getByTestId('create-modal').findByTestId('expand-all').click();
        cy.getByTestId('create-modal').findByTestId('NewSystem').should('exist');
        cy.getByTestId('create-modal').findByTestId('DataSource1').should('exist');
        cy.getByTestId('create-modal').findByTestId('CompositeProcessor1').should('exist');
        cy.getByTestId('create-modal').findByTestId('standard').should('not.exist');
        cy.getByTestId('create-modal').findByTestId('CompositeProcessor1').click();
        cy.getByTestId('childCreationDescription')
          .children('[role="button"]')
          .invoke('text')
          .should('have.length.gt', 1);
        cy.getByTestId('childCreationDescription').click();
        cy.getByTestId('childCreationDescription')
          .get('[data-value]')
          .should('have.length', 2)
          .get('[data-value="Elements Data Flow"]')
          .should('exist')
          .click();
        cy.getByTestId('create-modal').findByTestId('create-object').click();
        form.getWidgetElement('Test Widget Reference', 'reference-value-unused').should('exist');
        explorer.getTreeItemByLabel('unused').should('exist');
      });
    });
  });

  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';
    let instanceProjectId: string = '';

    beforeEach(() => {
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

    afterEach(() => {
      cy.deleteProject(studioProjectId);
      cy.deleteProject(instanceProjectId);
    });
    it('Then I can create element action on a mono valued and containment reference', () => {
      const explorer = new Explorer();
      const details = new Details();
      const form = new Form();
      explorer.createObject(domainName, 'Entity');

      details.getTextField('Name').should('have.value', 'NewEntity');
      details.getTextField('Name').type('{selectall}SuperEntity1{enter}');
      details.getCheckBox('Abstract').check();

      explorer.createObject(domainName, 'Entity');
      details.getTextField('Name').should('have.value', 'NewEntity');
      details.getTextField('Name').type('{selectall}MonoValuedContainment{enter}');

      explorer.createObject('MonoValuedContainment', 'Attribute');
      details.getTextField('Name').should('have.value', 'newString');
      details.getTextField('Name').type('{selectall}Name{enter}');

      explorer.createObject('SuperEntity1', 'Relation');
      details.getCheckBox('Containment').check();
      details.getCheckBox('Many').uncheck();
      details.openReferenceWidgetOptions('Target Type');
      details.selectReferenceWidgetOption('MonoValuedContainment');

      explorer.select('Entity1');
      details.getTextField('Name').should('have.value', 'Entity1');
      details.openReferenceWidgetOptions('Super Type');
      details.selectReferenceWidgetOption('SuperEntity1');

      explorer.expand('ViewNewModel');
      createFormWithWidgetRef(`${domainName}::Entity1`, 'WidgetRefRepresentation', 'relation');

      new Studio().createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
        instanceProjectId = res.projectId;
      });

      explorer.createObject('Root', 'Entity1s Entity1');
      explorer.select('Entity1');
      details.getTextField('Name').type('{selectall}Entity1{enter}');

      explorer.createRepresentation('Entity1', 'WidgetRefRepresentation', 'WidgetRefRepresentation');

      form.getWidgetElement('Test Widget Reference', 'reference-value-').should('not.exist');

      form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').click();
      cy.getByTestId('create-modal').should('exist');
      cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
      cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value]').should('have.length', 1);
      cy.getByTestId('childCreationDescription')
        .get('[data-value="Relation Mono Valued Containment"]')
        .should('exist')
        .click();
      cy.getByTestId('create-modal').findByTestId('create-object').click();

      cy.getByTestId('reference-value-').should('exist');

      explorer.select('MonoValuedContainment');
      details.getTextField('Name').should('not.have.value', 'Entity1');
      details.getTextField('Name').type('Test{enter}');
      form.getWidgetElement('Test Widget Reference', 'reference-value-Test').should('exist');

      form.getWidgetElement('Test Widget Reference', 'Test Widget Reference-add').click();
      cy.getByTestId('create-modal').should('exist');
      cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
      cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
      cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
      cy.getByTestId('childCreationDescription').click();
      cy.getByTestId('childCreationDescription').get('[data-value]').should('have.length', 1);
      cy.getByTestId('childCreationDescription')
        .get('[data-value="Relation Mono Valued Containment"]')
        .should('exist')
        .click();
      cy.getByTestId('create-modal').findByTestId('create-object').click();
      form.getWidgetElement('Test Widget Reference', 'reference-value-Test').should('exist');
    });

    it('Then widget reference filters ancestor only for containment reference', () => {
      const explorer = new Explorer();
      const details = new Details();
      const form = new Form();

      explorer.expand(domainName);
      explorer.createObject('Entity1', 'Relation');
      details.getCheckBox('Containment').check();
      details.openReferenceWidgetOptions('Target Type');
      details.selectReferenceWidgetOption('Entity2');
      explorer.createObject('Entity2', 'Relation');
      details.getCheckBox('Containment').check();
      details.openReferenceWidgetOptions('Target Type');
      details.selectReferenceWidgetOption('Entity1');

      explorer.expand('ViewNewModel');
      explorer.createObject('View', 'Form Description');
      explorer.select('New Form Description');
      details.getTextField('Domain Type').type(`${domainName}::Entity1`);
      details.getTextField('Name').type(`{selectall}WidgetRefRepresentation{enter}`);
      details.getTextField('Title Expression').type(`{selectall}WidgetRefRepresentation{enter}`);
      explorer.expand('WidgetRefRepresentation');
      explorer.expand('PageDescription');
      explorer.createObject('GroupDescription', 'Widgets Reference Widget Description');
      details.getTextField('Reference Name Expression').should('exist');
      details.getTextField('Label Expression').type('Test Widget Reference linkedTo');
      details.getTextField('Reference Name Expression').type(`linkedTo{enter}`);
      explorer.createObject('GroupDescription', 'Widgets Reference Widget Description');
      details.getTextField('Reference Name Expression').should('exist');
      details.getTextField('Label Expression').type('Test Widget Reference relation');
      details.getTextField('Reference Name Expression').type(`relation{enter}`);

      new Studio().createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
        instanceProjectId = res.projectId;
      });

      explorer.createObject('Root', 'Entity2s Entity2');
      explorer.select('Entity2');
      details.getTextField('Name').type('{selectall}Entity2{enter}');
      explorer.createObject('Entity2', 'Relation Entity1');
      explorer.createRepresentation('Entity1', 'WidgetRefRepresentation', 'WidgetRefRepresentation');

      form.getWidget('Test Widget Reference linkedTo').click();
      cy.getByTestId('option-Entity2').should('exist');
      form.getWidgetElement('Test Widget Reference linkedTo', 'Test Widget Reference linkedTo-more').click();
      cy.getByTestId('transfer-modal').findByTestId('tree-root-elements').click();
      cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
      cy.getByTestId('transfer-modal').findByTestId('Entity2').should('exist');
      cy.getByTestId('close-transfer-modal').click();

      form.getWidget('Test Widget Reference relation').click();
      cy.getByTestId('option-Entity2').should('not.exist');
      form.getWidgetElement('Test Widget Reference relation', 'Test Widget Reference relation-more').click();
      cy.getByTestId('transfer-modal').findByTestId('tree-root-elements').click();
      cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
      cy.getByTestId('transfer-modal').findByTestId('Entity2').should('not.exist');
      cy.getByTestId('close-transfer-modal').click();
    });
  });
});
