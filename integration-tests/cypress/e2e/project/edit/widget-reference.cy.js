/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

describe('/projects/:projectId/edit - FormDescriptionEditor', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
  });

  it('check widget reference mono-valued', () => {
    // Create the view
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('Domain Type').type('flow::DataFlow');
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('target');

    cy.get('[title="Back to the homepage"]').click();

    // Create the instance
    cy.getByTestId('create-template-Flow').click();
    cy.getByTestId('NewSystem').click();
    cy.getByTestId('DataSource1').dblclick();
    cy.getByTestId('standard-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('reference-value-Processor1').should('exist');
    cy.getByTestId('Test Widget Reference-more').should('exist');
    cy.getByTestId('Test Widget Reference-clear').should('exist');
    cy.getByTestId('Test Widget Reference-more').click();
    cy.getByTestId('browse-modal').findByTestId('tree-root-elements').should('exist');
    cy.getByTestId('browse-modal').findByTestId('CompositeProcessor1').should('exist');
    cy.getByTestId('browse-modal').findByTestId('DataSource1').should('not.exist');
    cy.getByTestId('browse-modal').findByTestId('selected').findByTestId('Processor1').should('exist');
    cy.getByTestId('browse-modal').findByTestId('CompositeProcessor1').click();
    cy.getByTestId('select-value').click();
    cy.getByTestId('reference-value-CompositeProcessor1').should('exist');
    cy.getByTestId('reference-value-Processor1').should('not.exist');
    cy.getByTestId('Test Widget Reference-clear').click();
    cy.getByTestId('reference-value-CompositeProcessor1').should('not.exist');
    const dataTransferCompositeProcessor = new DataTransfer();
    cy.getByTestId('CompositeProcessor1').trigger('dragstart', { dataTransfer: dataTransferCompositeProcessor });
    cy.getByTestId('Test Widget Reference').trigger('drop', { dataTransfer: dataTransferCompositeProcessor });
    cy.getByTestId('reference-value-CompositeProcessor1').should('exist');
    cy.getByTestId('reference-value-none').should('not.exist');
    cy.getByTestId('Test Widget Reference').click();
    cy.getByTestId('option-Processor1').should('exist');
    cy.getByTestId('option-Processor1').click();
    cy.getByTestId('reference-value-CompositeProcessor1').should('not.exist');
    cy.getByTestId('reference-value-Processor1').should('exist');
  });

  it('check widget reference multi-valued', () => {
    // Create the view
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('Domain Type').type('flow::CompositeProcessor');
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('incomingFlows');

    cy.get('[title="Back to the homepage"]').click();

    // Create the instance
    cy.getByTestId('create-template-Flow').click();
    cy.getByTestId('NewSystem').click();
    cy.getByTestId('CompositeProcessor1-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Test Widget Reference-more').should('exist');
    cy.getByTestId('Test Widget Reference-clear').should('exist');
    cy.getByTestId('Test Widget Reference-more').click();
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
    cy.getByTestId('reference-value-standard').should('exist');
    cy.getByTestId('Test Widget Reference-clear').click();
    cy.getByTestId('reference-value-standard').should('not.exist');
    cy.getByTestId('DataSource1').dblclick();
    const dataTransferStandardExplorer = new DataTransfer();
    cy.getByTestId('view-Explorer')
      .findByTestId('standard')
      .trigger('dragstart', { dataTransfer: dataTransferStandardExplorer });
    cy.getByTestId('Test Widget Reference').trigger('drop', { dataTransfer: dataTransferStandardExplorer });
    cy.getByTestId('reference-value-standard').should('exist');
    cy.createChildObject('DataSource1', 'Data Flow');
    cy.getByTestId('Test Widget Reference').click();
    cy.getByTestId('option-standard').should('not.exist');
    cy.getByTestId('option-unused').should('exist');
    cy.getByTestId('option-unused').click();
    cy.getByTestId('reference-value-standard').should('exist');
    cy.getByTestId('reference-value-unused').should('exist');
  });

  it('check widget reference containment', () => {
    // Create the view
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('Domain Type').type('flow::System');
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('powerOutputs');

    cy.get('[title="Back to the homepage"]').click();

    // Create the instance
    cy.getByTestId('create-template-Flow').click();
    cy.getByTestId('NewSystem-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Test Widget Reference-add').should('exist');
    cy.getByTestId('1000').should('not.exist');
    cy.getByTestId('Test Widget Reference-add').click();
    cy.getByTestId('create-modal').should('exist');
    cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
    cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value]')
      .should('have.length', 1)
      .get('[data-value="Power Output"]')
      .should('exist')
      .click();
    cy.getByTestId('create-modal').findByTestId('create-object').click();
    cy.getByTestId('reference-value-1000').should('exist');
    cy.getByTestId('1000').should('exist');
  });

  it('check widget reference non containment', () => {
    // Create the view
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('Domain Type').type('flow::CompositeProcessor');
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('incomingFlows');

    cy.get('[title="Back to the homepage"]').click();

    // Create the instance
    cy.getByTestId('create-template-Flow').click();
    cy.getByTestId('NewSystem').click();
    cy.getByTestId('CompositeProcessor1-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Test Widget Reference-add').should('exist');
    cy.getByTestId('Test Widget Reference-add').click();
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
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription')
      .get('[data-value]')
      .should('have.length', 2)
      .get('[data-value="Elements Data Flow"]')
      .should('exist')
      .click();
    cy.getByTestId('create-modal').findByTestId('create-object').click();
    cy.getByTestId('reference-value-unused').should('exist');
    cy.getByTestId('unused').should('exist');
  });

  it('check diagnostic messages are display on widget reference', () => {
    cy.createProjectFromTemplate('flow-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });

    cy.getByTestId('explorer://').findByTestId('FlowNewModel').dblclick();
    cy.getByTestId('explorer://').findByTestId('NewSystem').dblclick();
    cy.getByTestId('explorer://').findByTestId('DataSource1').dblclick();
    cy.getByTestId('explorer://').findByTestId('standard').click();
    cy.getByTestId('Target').find('p[class*="Mui-error"]').should('not.exist');
    cy.getByTestId('Target-clear').click();
    cy.getByTestId('Target').find('p[class*="Mui-error"]').should('exist');
  });

  it('check widget reference create element action on a mono valued and containment reference', () => {
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });

    cy.getByTestId('DomainNewModel').dblclick();

    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });

    cy.get('@domainValue').then((domainValue) => {
      cy.createChildObject(domainValue, 'Entity');
    });

    cy.getByTestId('Name').find('input').should('have.value', 'NewEntity');
    cy.getByTestId('Name').find('input').clear().type('SuperEntity1{enter}');
    cy.getByTestId('Abstract').find('input').check();

    cy.get('@domainValue').then((domainValue) => {
      cy.createChildObject(domainValue, 'Entity');
    });
    cy.getByTestId('Name').find('input').should('have.value', 'NewEntity');
    cy.getByTestId('Name').find('input').clear().type('MonoValuedContainment{enter}');

    cy.createChildObject('MonoValuedContainment', 'Attribute');
    cy.getByTestId('Name').find('input').should('have.value', 'newString');
    cy.getByTestId('Name').find('input').clear().type('Name');
    cy.getByTestId('MonoValuedContainment').dblclick();

    cy.createChildObject('SuperEntity1', 'Relation');
    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Many').find('input').uncheck();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-MonoValuedContainment').should('exist');
    cy.getByTestId('option-MonoValuedContainment').click();

    cy.getByTestId('Entity1').click();
    cy.getByTestId('Name').find('input').should('have.value', 'Entity1');
    cy.getByTestId('Super Type').click();
    cy.getByTestId('option-SuperEntity1').should('exist');
    cy.getByTestId('option-SuperEntity1').click();

    cy.getByTestId('ViewNewModel').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('input-Domain Type').should('have.text', '');
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId('Domain Type').type(`${domainValue}::Entity1`);
    });
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('relation');

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
    cy.createChildObject('Root', 'Entity1s Entity1');

    cy.getByTestId('Entity1').click();
    cy.getByTestId('input-Name').clear().type('Entity1');
    cy.getByTestId('Entity1-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('reference-value-').should('not.exist');

    cy.getByTestId('Test Widget Reference-add').click();
    cy.getByTestId('create-modal').should('exist');
    cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
    cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value]')
      .should('have.length', 1)
      .get('[data-value="Relation Mono Valued Containment"]')
      .should('exist')
      .click();
    cy.getByTestId('create-modal').findByTestId('create-object').click();

    cy.getByTestId('reference-value-').should('exist');

    cy.getByTestId('MonoValuedContainment').click();
    cy.getByTestId('input-Name').should('not.have.value', 'Entity1');
    cy.getByTestId('input-Name').type('Test{enter}');
    cy.getByTestId('reference-value-Test').should('exist');

    cy.getByTestId('Test Widget Reference-add').click();
    cy.getByTestId('create-modal').should('exist');
    cy.getByTestId('create-modal').findByTestId('tree-root-elements').should('not.exist');
    cy.getByTestId('create-modal').findByTestId('childCreationDescription').should('exist');
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value]')
      .should('have.length', 1)
      .get('[data-value="Relation Mono Valued Containment"]')
      .should('exist')
      .click();
    cy.getByTestId('create-modal').findByTestId('create-object').click();
    cy.getByTestId('reference-value-Test').should('exist');
  });

  it('check widget reference click navigation to filter tree item', () => {
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
    cy.getByTestId('DomainNewModel').dblclick();

    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId(`${domainValue}`).dblclick();
    });
    cy.getByTestId('Entity1').dblclick();
    cy.getByTestId('Entity2').dblclick();
    cy.getByTestId('linkedTo').click();
    cy.getByTestId('reference-value-Entity2').should('exist');

    cy.getByTestId('linkedTo').type('{ctrl+f}');
    cy.getByTestId('filterbar-textfield').type('Entity1');
    cy.getByTestId('filterbar-filter-button').click();
    cy.getByTestId('Entity2').should('not.exist');

    cy.getByTestId('reference-value-Entity2').click();
    cy.getByTestId('page-tab-Entity2').should('exist');
    cy.getByTestId('filterbar-close-button').click();
    cy.getByTestId('selected').findByTestId('Entity2').should('exist');
  });

  it('check widget reference filter ancestor only for containment reference', () => {
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });

    cy.getByTestId('DomainNewModel').dblclick();

    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });

    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId(`${domainValue}`).dblclick();
    });
    cy.createChildObject('Entity1', 'Relation');
    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-Entity2').click();
    cy.createChildObject('Entity2', 'Relation');
    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-Entity1').click();

    cy.get('[title="Back to the homepage"]').click();

    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.createChildObject('View', 'Form Description');
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('input-Domain Type').should('have.text', '');
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId('Domain Type').type(`${domainValue}::Entity1`);
    });
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference linkedTo');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('linkedTo');
    cy.createChildObject('GroupDescription', 'Widgets Reference Widget Description');
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference relation');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('relation');

    cy.get('[title="Back to the homepage"]').click();

    cy.get('@domainValue').then((domainValue) => {
      cy.createInstanceFromDomainModel(domainValue, false);
    });

    cy.createChildObject('Root', 'Entity2s Entity2');
    cy.getByTestId('Name').type('Entity2');
    cy.createChildObject('Entity2', 'Relation Entity1');
    cy.createRepresentationFromExplorer('Entity1', 'WidgetRefRepresentation');

    cy.getByTestId('Test Widget Reference linkedTo').click();
    cy.getByTestId('option-Entity2').should('exist');
    cy.getByTestId('Test Widget Reference linkedTo-more').click();
    cy.getByTestId('transfer-modal').findByTestId('tree-root-elements').click();
    cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
    cy.getByTestId('transfer-modal').findByTestId('Entity2').should('exist');
    cy.getByTestId('close-transfer-modal').click();

    cy.getByTestId('Test Widget Reference relation').click();
    cy.getByTestId('option-Entity2').should('not.exist');
    cy.getByTestId('Test Widget Reference relation-more').click();
    cy.getByTestId('transfer-modal').findByTestId('tree-root-elements').click();
    cy.getByTestId('transfer-modal').findByTestId('expand-all').click();
    cy.getByTestId('transfer-modal').findByTestId('Entity2').should('not.exist');
    cy.getByTestId('close-transfer-modal').click();
  });
});
