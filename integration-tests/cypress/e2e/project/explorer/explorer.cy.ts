/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { Explorer } from '../../../workbench/Explorer';

describe('Explorer', () => {
  context('Given a flow project with a robot document', () => {
    const projectName = 'Cypress - explorer';
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it('Then we can expand and collapse an item with double click', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.getTreeItemByLabel('System').should('exist');

        explorer.expandWithDoubleClick('System');
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CompositeProcessor').should('exist');
        explorer.getTreeItemByLabel('Wifi').should('exist');

        explorer.collapseWithDoubleClick('System');
        explorer.getTreeItemByLabel('Central_Unit').should('not.exist');
        explorer.getTreeItemByLabel('CompositeProcessor').should('not.exist');
        explorer.getTreeItemByLabel('Wifi').should('not.exist');
      });

      it('Then we can expand and collapse an item without selecting it by clicking on the toggle', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.getTreeItemByLabel('System').should('exist');
        explorer.select('robot');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('robot').should('exist');

        // Expand 'System' using the toggle
        explorer.toggle('System');
        // Check that its children are visible (i.e. it is correcly expanded)
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CompositeProcessor').should('exist');
        explorer.getTreeItemByLabel('Wifi').should('exist');
        // Check that this has not changed the selection (still on 'robot')
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('robot').should('exist');

        // Collapse 'System' using the toggle
        explorer.toggle('System');
        // Check that its children are no longer visible (i.e. it is correcly collapsed)
        explorer.getTreeItemByLabel('Central_Unit').should('not.exist');
        explorer.getTreeItemByLabel('CompositeProcessor').should('not.exist');
        explorer.getTreeItemByLabel('Wifi').should('not.exist');
        // Check that this has not changed the selection (still on 'robot')
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('robot').should('exist');
      });

      it('Then we can select a visible item by clicking on the right empty area', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.getTreeItemByLabel('System').should('exist');

        explorer.expandWithDoubleClick('System');
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        cy.getByTestId('Central_Unit-fullrow').should('exist');

        // Click in the empty area after the label & menu: this should select the item
        cy.getByTestId('Central_Unit-fullrow').click('right');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Central_Unit').should('exist');
      });

      it('Then we can select a visible item by clicking on the left empty area', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.getTreeItemByLabel('System').should('exist');

        explorer.expandWithDoubleClick('System');
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        cy.getByTestId('Central_Unit-fullrow').should('exist');

        // Click in the empty area before the toggle: this should also select the item
        cy.getByTestId('Central_Unit-fullrow').click('left');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Central_Unit').should('exist');
      });
    });
  });

  context('Given a read-only project with a robot document', () => {
    const projectName = 'Cypress - Disabled Edit Project';
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.rename(projectName);
        // The second `visit` is needed to ensure the whole project has reloaded in read-only mode
        project.visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we display actions of a tree item', () => {
      it('Then they are all disabled', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.getTreeItemByLabel('NewSystem').should('exist');
        const actions = explorer.openTreeItemAction('NewSystem');

        actions.getNewObjectButton().should('have.class', 'Mui-disabled');
        actions.getNewRepresentationButton().should('have.class', 'Mui-disabled');
        actions.getRenameItemButton().should('have.class', 'Mui-disabled');
        actions.getDeleteItemButton().should('have.class', 'Mui-disabled');
      });
    });
  });
});
