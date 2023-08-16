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
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.getByTestId('View-more').should('be.enabled').click();
    // create the form description
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Form Description"]').click();
    cy.getByTestId('create-object').click();
    // create the form description editor
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('New Form Description-more').should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('create-representation').click();
  });

  it('try to move a toolbar action into another empty group', () => {
    // create another group
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Group').trigger('dragstart', { dataTransfer });
    cy.getByTestId('Page-DropArea').trigger('drop', { dataTransfer });
    // create a toolbar action in the first group
    cy.get('[data-testid^="ToolbarActions-NewAction-"]').eq(1).click();
    // move the toolbar action from the first group to the second one
    cy.getByTestId('ToolbarAction').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="ToolbarActions-DropArea-"]').eq(2).trigger('drop', { dataTransfer });
  });

  it('try to create an empty page', () => {
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').should('have.lengthOf', 1);
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Page').trigger('dragstart', { dataTransfer });
    cy.getByTestId('PageList-DropArea').trigger('drop', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').should('have.lengthOf', 2);
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(1).click();
    cy.get('[title="Group"]').should('exist');
  });

  it('try to rename a page', () => {
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(0).click();
    cy.getByTestId('Label Expression').click().type('Page Rename{enter}');
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').first().should('have.text', 'Page Rename');
  });

  it('try to move a page', () => {
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(0).click();
    cy.getByTestId('Label Expression').click().type('Page 1{enter}');
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Page').trigger('dragstart', { dataTransfer });
    cy.getByTestId('PageList-DropArea').trigger('drop', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').should('have.lengthOf', 2);
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(1).click();
    cy.wait(500); // Wait for representation to refresh
    cy.getByTestId('Label Expression').click().type('Page 2{enter}');
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(0).should('have.text', 'Page 1');
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(1).should('have.text', 'Page 2');
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(0).trigger('dragstart', { dataTransfer });
    cy.getByTestId('PageList-DropArea').trigger('drop', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(0).should('have.text', 'Page 2');
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').eq(1).should('have.text', 'Page 1');
  });

  it('try to delete a page', () => {
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Page').trigger('dragstart', { dataTransfer });
    cy.getByTestId('PageList-DropArea').trigger('drop', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').should('have.lengthOf', 2);
    cy.get('[data-testid^="Page-"]').eq(0).click().type('{del}');
    cy.wait(500); // Wait for representation to refresh
    cy.get('[data-testid^="Page-"]').not('[data-testid="Page-DropArea"]').should('have.lengthOf', 1);
  });

  it('try to add group and widget to a page', () => {
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-BarChart').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').eq(0).trigger('drop', { dataTransfer });
    cy.getByTestId('BarChart').should('exist');
  });

  it('can create a slider widget in a Group', () => {
    // Check that the "Slider" widget is available in the list
    cy.getByTestId('FormDescriptionEditor-Slider').should('exist');
    // Create a slider inside the Group
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Slider').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="Slider"]').should('be.visible');
  });

  it('can create a slider widget in a Flexbox Container', () => {
    // Check that the "Slider" widget is available in the list
    cy.getByTestId('FormDescriptionEditor-Slider').should('exist');
    // Create a Flexbox inside the Group
    var dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-FlexboxContainer').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="FlexboxContainer"]').should('be.visible');
    // Create a slider inside the Flexbox
    dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Slider').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="FlexboxContainer-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="Slider"]').should('be.visible');
  });

  it('display the page of the element selected', () => {
    // Creates a second page
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('New Form Description-more').should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click().get('[data-value="Page Description"]').should('exist').click();
    cy.getByTestId('create-object').click();
    // Adds a widget to the first page
    cy.getByTestId('PageDescription').eq(0).dblclick();
    cy.getByTestId('GroupDescription').eq(0).click();
    cy.getByTestId('GroupDescription-more').eq(0).should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="Widgets Pie Chart Description"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    // Adds a widget to the second page
    cy.getByTestId('PageDescription').eq(1).dblclick();
    cy.getByTestId('GroupDescription').eq(1).click();
    cy.getByTestId('GroupDescription-more').eq(1).should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="Widgets Bar Chart Description"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    // Select the widget from the first page must display it on the editor, and the first page must be selected
    cy.getByTestId('PieChartDescription').click();
    cy.getByTestId('PieChart').should('exist');
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'true');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'false');
    // Select the widget from the second page must display it on the editor, and the second page must be selected
    cy.getByTestId('BarChartDescription').click();
    cy.getByTestId('PieChart').should('not.exist');
    cy.getByTestId('BarChart').should('exist');
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'false');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'true');
    // Check page selection for group
    cy.getByTestId('GroupDescription').eq(0).click();
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'true');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'false');
    cy.getByTestId('GroupDescription').eq(1).click();
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'false');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'true');
    // Check page selection for page
    cy.getByTestId('PageDescription').eq(0).click();
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'true');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'false');
    cy.getByTestId('PageDescription').eq(1).click();
    cy.get('[data-testid^="Page-"]').eq(0).should('have.attr', 'aria-selected', 'false');
    cy.get('[data-testid^="Page-"]').eq(1).should('have.attr', 'aria-selected', 'true');
  });

  it('can create a reference widget in a Group', () => {
    // Check that the "Reference" widget is available in the list
    cy.getByTestId('FormDescriptionEditor-ReferenceWidget').should('exist');
    // Create a reference inside the Group
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-ReferenceWidget').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="ReferenceWidget"]').should('be.visible');
    cy.getByTestId('PageDescription').dblclick();
    cy.getByTestId('GroupDescription').dblclick();
    cy.getByTestId('ReferenceWidgetDescription-more').should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="Conditional Styles Conditional Reference Widget Description Style"]')
      .should('exist');
    cy.get('[data-value="Style Widget Description Style"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Font Size').should('exist');
    cy.getByTestId('Italic').should('exist');
    cy.getByTestId('Bold').should('exist');
    cy.getByTestId('Underline').should('exist');
    cy.getByTestId('Strike Through').should('exist');
    cy.getByTestId('Color').should('exist');
  });

  it('can create a reference widget in a Flexbox Container', () => {
    // Check that the "Reference" widget is available in the list
    cy.getByTestId('FormDescriptionEditor-ReferenceWidget').should('exist');
    // Create a Flexbox inside the Group
    var dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-FlexboxContainer').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="FlexboxContainer"]').should('be.visible');
    // Create a Reference inside the Flexbox
    dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-ReferenceWidget').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="FlexboxContainer-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="ReferenceWidget"]').should('be.visible');
  });

  function checkWidgetIsEnabledExpression(widgetName, should) {
    cy.getByTestId('GroupDescription-more').should('be.enabled').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="' + widgetName + ' Description"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Is Enabled Expression').should(should);
  }

  it('is enabled expression is available only for the editable widgets', () => {
    cy.getByTestId('PageDescription').dblclick();
    // Not editable widgets shouldn't have isEnabledExpression
    checkWidgetIsEnabledExpression('Widgets Link', 'not.exist');
    checkWidgetIsEnabledExpression('Widgets Pie Chart', 'not.exist');
    checkWidgetIsEnabledExpression('Widgets Label', 'not.exist');

    // Editable widgets should have isEnabledExpression
    checkWidgetIsEnabledExpression('Widgets Button', 'exist');
    checkWidgetIsEnabledExpression('Widgets Flexbox Container', 'exist');
    checkWidgetIsEnabledExpression('Toolbar Actions Button', 'exist');
    checkWidgetIsEnabledExpression('Widgets Slider', 'exist');
  });

  function createBorderStyleAndCheckProperties(styleName) {
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="' + styleName + '"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Border Color').should('exist');
    cy.getByTestId('Border Radius').should('exist');
    cy.getByTestId('Border Size').should('exist');
    cy.getByTestId('Solid').should('exist');
    cy.getByTestId('Dashed').should('exist');
    cy.getByTestId('Dotted').should('exist');
  }

  it('can create border style in a Group', () => {
    cy.getByTestId('PageDescription').dblclick();
    cy.getByTestId('GroupDescription-more').should('be.enabled').click();
    createBorderStyleAndCheckProperties('Border Style Container Border Style');

    cy.getByTestId('GroupDescription-more').should('be.enabled').click();
    createBorderStyleAndCheckProperties('Conditional Border Styles Conditional Container Border Style');
    cy.getByTestId('Condition').should('exist');
  });

  it('can create border style in a Flexbox Container', () => {
    cy.getByTestId('PageDescription').dblclick();

    // Create a Flexbox inside the Group
    var dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-FlexboxContainer').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').trigger('drop', { dataTransfer });
    cy.get('[title="FlexboxContainer"]').should('be.visible');

    cy.getByTestId('GroupDescription').dblclick();
    cy.getByTestId('FlexboxContainerDescription').click();
    cy.getByTestId('Margin').should('exist');
    cy.getByTestId('Padding').should('exist');
    cy.getByTestId('Gap').should('exist');

    cy.getByTestId('FlexboxContainerDescription-more').click();
    createBorderStyleAndCheckProperties('Border Style Container Border Style');

    cy.getByTestId('FlexboxContainerDescription-more').click();
    createBorderStyleAndCheckProperties('Conditional Border Styles Conditional Container Border Style');
    cy.getByTestId('Condition').should('exist');
  });

  it('can create For and If elements inside Groups/Flexbox/For/If', () => {
    function createControl(controlType, targetId, targetIndex) {
      const dataTransfer = new DataTransfer();
      cy.getByTestId(`FormDescriptionEditor-${controlType}`).trigger('dragstart', { dataTransfer });
      cy.get(`[data-testid^="${targetId}-"]`).eq(targetIndex).trigger('drop', { dataTransfer });
    }

    // Create top-level If, For and Flexbox inside the Group
    createControl('FormElementIf', 'Group-Widgets-DropArea', 0);
    cy.get('[title="FormDescriptionEditorIf"]').eq(0).should('be.visible');
    createControl('FormElementFor', 'Group-Widgets-DropArea', 0);
    cy.get('[title="FormDescriptionEditorFor"]').eq(0).should('be.visible');
    createControl('FlexboxContainer', 'Group-Widgets-DropArea', 0);
    cy.get('[title="FlexboxContainer"]').eq(0).should('be.visible');

    // NOTE: the indexes for the target drop-area depend on the order in which the
    // widgets of the same kind were created and appear before on the page.

    // If, For, Flexbox and Label widget inside an If
    createControl('FormElementIf', 'FormDescriptionEditorIf-Widgets-DropArea', 0);
    cy.get('[title="FormDescriptionEditorIf"]').should('have.lengthOf', 2);
    createControl('FormElementFor', 'FormDescriptionEditorIf-Widgets-DropArea', 1);
    cy.get('[title="FormDescriptionEditorFor"]').should('have.lengthOf', 2);
    createControl('FlexboxContainer', 'FormDescriptionEditorIf-Widgets-DropArea', 1);
    cy.get('[title="FlexboxContainer"]').should('have.lengthOf', 2);
    createControl('Label', 'FormDescriptionEditorIf-Widgets-DropArea', 1);
    cy.get('[title="LabelWidget"]').should('have.lengthOf', 1);

    // The same, but inside the For
    createControl('FormElementIf', 'FormDescriptionEditorFor-Widgets-DropArea', 1);
    cy.get('[title="FormDescriptionEditorIf"]').should('have.lengthOf', 3);
    createControl('FormElementFor', 'FormDescriptionEditorFor-Widgets-DropArea', 1);
    cy.get('[title="FormDescriptionEditorFor"]').should('have.lengthOf', 3);
    createControl('FlexboxContainer', 'FormDescriptionEditorFor-Widgets-DropArea', 2);
    cy.get('[title="FlexboxContainer"]').should('have.lengthOf', 3);
    createControl('Label', 'FormDescriptionEditorFor-Widgets-DropArea', 2);
    cy.get('[title="LabelWidget"]').should('have.lengthOf', 2);
    // The same, but inside the flexbox container
    createControl('FormElementIf', 'FlexboxContainer-Widgets-DropArea', 2);
    cy.get('[title="FormDescriptionEditorIf"]').should('have.lengthOf', 4);
    createControl('FormElementFor', 'FlexboxContainer-Widgets-DropArea', 2);
    cy.get('[title="FormDescriptionEditorFor"]').should('have.lengthOf', 4);
    createControl('FlexboxContainer', 'FlexboxContainer-Widgets-DropArea', 2);
    cy.get('[title="FlexboxContainer"]').should('have.lengthOf', 4);
    createControl('Label', 'FlexboxContainer-Widgets-DropArea', 3);
    cy.get('[title="LabelWidget"]').should('have.lengthOf', 2);
  });
});
