/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
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

const projectName = 'Cypress - Drop on diagram';

describe('/projects/:projectId/edit - Diagram', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then we can create views by Drag and Drop on an unsynchronized diagram', () => {
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography unsynchronized', 'diagram');

      const diagram = new Diagram();

      const dataTransfer = new DataTransfer();
      explorer.dragTreeItem('Central_Unit', dataTransfer);
      diagram.dropOnDiagram('diagram', dataTransfer);

      diagram.getNodes('diagram', 'Central_Unit').should('exist');
    });
  });
});
