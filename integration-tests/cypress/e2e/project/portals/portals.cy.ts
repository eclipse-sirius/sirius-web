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
import { Flow } from '../../../usecases/Flow';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';
import { Portal } from '../../../workbench/Portal';
import { Representations } from '../../../workbench/Representations';

const projectName = 'Cypress - portal';

describe('/projects/:projectId/edit - Portal', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we create a new portal on a System using the contextual menu', () => {
      beforeEach(() => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('Robot').should('exist');
        explorer.createRepresentation('Robot', 'Portal', 'Portal');
      });

      it('Then it reveals a newly created empty portal', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
        explorer.getTreeItemByLabel('Portal').should('exist');

        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Portal').should('exist');

        const portal = new Portal();
        portal.getDropArea().should('exist');
      });

      it('Then the portal can be renamed from the explorer', () => {
        cy.getByTestId('representation-tab-Portal').should('be.visible');
        const explorer = new Explorer();
        explorer.rename('Portal', 'New Portal');
        cy.getByTestId('representation-tab-New Portal').should('be.visible');
      });

      it('Then the portal can be deleted from the explorer', () => {
        const portal = new Portal();
        portal.getPortal().should('be.visible');
        const explorer = new Explorer();
        explorer.delete('Portal');
        portal.getPortal().should('not.exist');
      });

      it('Then a portal can not be added into itself', () => {
        const portal = new Portal();
        portal.addRepresentationFromExplorer('Portal', 'Portal');
        cy.get('#notistack-snackbar').should('be.visible');
        cy.get('#notistack-snackbar').contains('A portal can not contain itself');
      });

      it('The we can share a portal URL', () => {
        const portal = new Portal();
        portal.getToolbar().then((res) => {
          const toolbar = cy.wrap(res);
          toolbar.should('be.visible');
          toolbar.get('[aria-label="share portal').should('be.visible').should('be.enabled').click();
          cy.url().then(($url) => {
            cy.window().then((win) => {
              win.navigator.clipboard.readText().then((text) => {
                expect(text).to.eq($url);
              });
            });
          });
        });
      });

      context('When we create a second portal', () => {
        const secondPortal = 'Portal2';
        beforeEach(() => {
          const explorer = new Explorer();
          explorer.getTreeItemByLabel('Robot').should('exist');
          explorer.createRepresentation('Robot', 'Portal', secondPortal);
        });

        it('A portal can be added into a portal', () => {
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', secondPortal);
          portal.getFrame(secondPortal).then((res) => {
            const frame = cy.wrap(res);
            frame.should('be.visible');
            frame
              .findByTestId('representation-frame-header')
              .find('[aria-label="remove"]')
              .should('be.visible')
              .should('be.enabled');
            frame.get('.react-resizable-handle').should('be.visible');
          });

          const representations = new Representations();
          representations.showRepresentationsView();
          representations.hasTreeItems([secondPortal]);
        });

        it('Portal loops can not be created', () => {
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', secondPortal);
          portal.addRepresentationFromExplorer(secondPortal, 'Portal');
          cy.get('#notistack-snackbar').should('be.visible');
          cy.get('#notistack-snackbar').contains('A portal can not contain itself');
        });
      });

      context('When we create a Topography diagram', () => {
        const diagramTitle = 'Topography';
        beforeEach(() => {
          const explorer = new Explorer();
          const diagram = new Diagram();
          explorer.getTreeItemByLabel('Robot').should('exist');
          explorer.createRepresentation('Robot', 'Topography', diagramTitle);
          diagram.getDiagram('Topography').should('exist');
        });

        it('The diagram can be added to the portal', () => {
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', diagramTitle);

          // Check that the diagram has been added
          portal.getFrame(diagramTitle).then((res) => {
            const frame = cy.wrap(res);
            frame.should('be.visible');
            frame
              .findByTestId('representation-frame-header')
              .find('[aria-label="remove"]')
              .should('be.visible')
              .should('be.enabled');
            frame.get('.react-resizable-handle').should('be.visible');
          });

          // Check the portal's toolbar content and state
          portal.getToolbar().then((res) => {
            const toolbar = cy.wrap(res);
            toolbar.should('be.visible');
            toolbar.get('[aria-label="toggle full screen mode').should('be.visible').should('be.enabled');
            toolbar.get('[aria-label="exit full screen mode').should('not.exist');
            toolbar.get('[aria-label="edit portal configuration').should('be.visible').should('be.disabled');
            toolbar.get('[aria-label="edit representations').should('be.visible').should('be.enabled');
          });

          // Switch the portal to "direct" mode
          portal.getToolbar().get('[aria-label="edit representations').click();

          // Check the diagram's frame is no longer removable nor resizable
          portal.getFrame(diagramTitle).then((res) => {
            const frame = cy.wrap(res);
            frame.should('be.visible');
            frame.findByTestId('representation-frame-header').find('[aria-label="remove"]').should('not.exist');
            frame.get('.react-resizable-handle').should('not.be.visible');
          });

          // Check the new state of the toolbar
          portal.getToolbar().then((res) => {
            const toolbar = cy.wrap(res);
            toolbar.should('be.visible');
            toolbar.get('[aria-label="toggle full screen mode').should('be.visible').should('be.enabled');
            toolbar.get('[aria-label="exit full screen mode').should('not.exist');
            toolbar.get('[aria-label="edit portal configuration').should('be.visible').should('be.enabled');
            toolbar.get('[aria-label="edit representations').should('be.visible').should('be.disabled');
          });

          const representations = new Representations();
          representations.showRepresentationsView();
          representations.hasTreeItems([diagramTitle]);
        });

        it('A portal which already contains a representation opens in direct mode', () => {
          const explorer = new Explorer();
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', diagramTitle);
          portal.getFrame(diagramTitle).should('be.visible');
          explorer.select('Robot');
          cy.getByTestId('close-representation-tab-Portal').click();
          portal.getPortal().should('not.exist');
          explorer.select('Portal');
          portal.getToolbar().then((res) => {
            const toolbar = cy.wrap(res);
            toolbar.should('be.visible');
            toolbar.get('[aria-label="edit portal configuration').should('be.visible').should('be.enabled');
            toolbar.get('[aria-label="edit representations').should('be.visible').should('be.disabled');
          });
        });

        it('We can share the URL of the diagram from inside the portal', () => {
          // Find the diagram's direct URL
          let diagramURL = '';
          const explorer = new Explorer();
          explorer.getTreeItemByLabel(diagramTitle).click();
          cy.url().then(($url) => {
            diagramURL = $url;
          });

          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', diagramTitle);
          // Open the diagram's share modal from inside the portal and compare
          explorer.getTreeItemByLabel('Portal').click();
          new Diagram().getDiagram(diagramTitle).find('[aria-label="share diagram"]').click();
          cy.window().then((win) => {
            win.navigator.clipboard.readText().then((text) => {
              expect(text).to.eq(diagramURL);
            });
          });
        });

        it('Renaming a diagram embedded in a portal renames its frame', () => {
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', diagramTitle);
          const explorer = new Explorer();
          explorer.rename(diagramTitle, 'New ' + diagramTitle);
          explorer.getTreeItemByLabel('Portal').click();

          portal.getFrame('New ' + diagramTitle).should('be.visible');

          const representations = new Representations();
          representations.showRepresentationsView();
          representations.hasTreeItems(['New ' + diagramTitle]);
        });

        it('Deleting a diagram embedded in a portal removes its frame', () => {
          const portal = new Portal();
          portal.addRepresentationFromExplorer('Portal', diagramTitle);
          const explorer = new Explorer();
          explorer.delete(diagramTitle);
          explorer.getTreeItemByLabel('Portal').click();

          portal.getFrame(diagramTitle).should('not.exist');
        });
      });
    });
  });
});
