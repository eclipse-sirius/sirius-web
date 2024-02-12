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
import { Flow } from '../../../usecases/Flow';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - explorer selection';

describe('/projects/:projectId/edit - Tree toolbar', () => {
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

      new Diagram().getNodes('diagram', 'Wifi').should('exist');
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it('Then the synchronization mode should not reveal the semantic element selected on a diagram but activating it will', () => {
        // 1. Deactivate the synchronization mode
        cy.getByTestId('tree-synchronize').click();

        // 2. On the diagram, click on the 'CaptureSubSystem'
        const diagram = new Diagram();
        diagram.getNodes('diagram', 'CaptureSubSystem').click(0, 0);

        // 3. Check that the 'CaptureSubSystem' node is visible and selected in the explorer
        const explorer = new Explorer();
        explorer.getSelectedTreeItems().contains('CaptureSubSystem').should('exist');
        explorer.getSelectedTreeItems().contains('DSP').should('not.exist');

        // 4. On the diagram, click on the 'DSP'
        diagram.getNodes('diagram', 'DSP').click();
        explorer.getTreeItemByLabel('DSP').should('not.exist');

        // 5. Activate the synchronization mode
        cy.getByTestId('tree-synchronize').click();

        // 6. Check that the 'DSP' node is visible and selected
        explorer.getSelectedTreeItems().contains('DSP').should('exist');
      });
    });
  });
});
