/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

const projectName = 'Cypress - explorer-selection';

describe('Explorer selection', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    let explorer: Explorer;

    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
        explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.expandWithDoubleClick('System');
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we click on multiple items in the explorer', () => {
      it('Then the selection is always the last clicked item', () => {
        explorer.select('Central_Unit');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().first().then((item) => item.text()).should('be.equal', 'Central_Unit');
        explorer.select('Wifi');
        explorer.select('Capture_Subsystem');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().first().then((item) => item.text()).should('be.equal', 'Capture_Subsystem');
      });
    });

    context('When we click on items in the explorer while pressing the Ctrl key', () => {
      it('Then the selection is the union of the clicked items', () => {
        explorer.select('Central_Unit');
        explorer.select('Wifi', true);
        explorer.getSelectedTreeItems().should('have.length', 2);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Central_Unit', 'Wifi']);
        });
      });

      it('Then Ctrl+clicking on a selected element deselects it', () => {
        explorer.select('Central_Unit');
        explorer.select('Wifi', true);
        explorer.select('Central_Unit', true);
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().first().then((item) => item.text()).should('be.equal', 'Wifi');
        explorer.select('Wifi', true);
        explorer.getSelectedTreeItems().should('have.length', 0);
      });
    });

    context('When we are using the range selection with shift click', () => {
      it('Then shift click after a simple click selects all elements between the pivot point (simple clicked element) and the shift clicked element', () => {
        explorer.select('Central_Unit');
        explorer.select('Wifi', false, true);
        explorer.getSelectedTreeItems().should('have.length', 3);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Central_Unit', 'Wifi', 'Capture_Subsystem']);
        });
      });

      it('Then consecutive shift clicks update the selection range from the pivot point (simple clicked element)', () => {
        explorer.select('Central_Unit');
        explorer.select('Wifi', false, true);
        explorer.select('robot', false, true);
        explorer.getSelectedTreeItems().should('have.length', 3);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['robot', 'System', 'Central_Unit']);
        });
      });

      it('Then a simple click after a range selection resets the selection to the simple clicked element', () => {
        explorer.select('Central_Unit', false, true);
        explorer.select('Wifi');
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().first().then((item) => item.text()).should('be.equal', 'Wifi');
      });

      it('Then the range selection over items with unfolded children also selects the children included in the range', () => {
        explorer.expandWithDoubleClick('Central_Unit');
        explorer.select('Central_Unit', false, false);
        explorer.select('Wifi', false, true);
        explorer.getSelectedTreeItems().should('have.length', 6);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Central_Unit', 'DSP', 'Motion_Engine', 'active', 'Capture_Subsystem', 'Wifi']);
        });
      });
    });

    context('When we are using the range selection combined with the Ctrl key', () => {

      beforeEach(() => {
        explorer.expandWithDoubleClick('Capture_Subsystem');
      });

      it('Then Ctrl clicking on an unselected element after selecting a range adds the element to the selection', () => {
        explorer.select("Radar_Capture", false, false);
        explorer.select("Radar", false, true);
        explorer.select("GPU", true);

        explorer.getSelectedTreeItems().should('have.length', 4);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Radar_Capture', 'Radar', 'GPU', 'Back_Camera']);
        });
      });

      it('Then Ctrl clicking on a selected element after selecting a range removes the element from the selection', () => {
        explorer.select("Radar_Capture", false, false);
        explorer.select("Radar", false, true);
        explorer.select("Back_Camera", true);

        explorer.getSelectedTreeItems().should('have.length', 2);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Radar_Capture', 'Radar']);
        });
      });

      it('Then Ctrl+Shift clicking on an unselected element after a range selection extends the selection to include the range from the pivot point to the clicked element', () => {
        explorer.select("Radar_Capture", false, false);
        explorer.select("Radar", false, true);
        explorer.select("active", true, true);

        explorer.getSelectedTreeItems().should('have.length', 6);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Radar_Capture', 'Radar', 'GPU', 'Back_Camera', 'active', 'Engine']);
        });
      });

      it('Then Ctrl+Shift clicking on an unselected element after a range selection followed by a multi-selection adds to the selection the range from the new pivot point (set by the multi-selection) to the clicked element', () => {
        explorer.select("Radar_Capture", false, false);
        explorer.select("Back_Camera", false, true);
        explorer.select("Engine", true, false);
        explorer.select("active", true, true);

        explorer.getSelectedTreeItems().should('have.length', 5);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['Radar_Capture', 'Back_Camera', 'Engine', 'active', 'GPU']);
        });
      });
    });
  });

  context('Given a flow project with two documents', () => {
    let projectId: string = '';

    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        cy.getCurrentEditingContextId(projectId).then((res) => {
          const editingContextId = res.body.data.viewer.project.currentEditingContext.id;
          cy.createDocument(editingContextId, 'robot_flow', 'robot2');
        });
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we are using the range selection with shift click', () => {
      it('Then a single shift click selects all elements from the first element of the tree to the clicked element', () => {
        // Three models: default Flow + two Robot Flows
        const explorer = new Explorer();
        explorer.select('robot2', false, true);
        explorer.getSelectedTreeItems().should('have.length', 3);
        explorer.getSelectedTreeItems().should((items) => {
          const texts = items.toArray().map((el) => Cypress.$(el).text());
          expect(texts).to.include.members(['robot', 'robot2']);
        });
      });
    });
  });
});
