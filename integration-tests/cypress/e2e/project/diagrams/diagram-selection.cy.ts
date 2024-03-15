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
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - diagram selection';

describe('Diagram selection', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.expand('Robot');

      explorer.createRepresentation('Robot', 'Topography', 'diagram');

      // Wait for the diagram to be render
      new Diagram().getNodes('diagram', 'Wifi').should('exist');
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it('Then diagram selection is synchronized with the explorer', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();

        explorer.expand('Central_Unit');
        explorer.select('DSP');
        diagram.getSelectedNodes('diagram', 'DSP').should('exist');
        diagram.getSelectedNodes('diagram', 'Motion_Engine').should('not.exist');

        explorer.select('Motion_Engine');
        diagram.getSelectedNodes('diagram', 'DSP').should('not.exist');
        diagram.getSelectedNodes('diagram', 'Motion_Engine').should('exist');
      });
      it('Then diagram selection is synchronized with the explorer during multi selection', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();

        explorer.expand('Central_Unit');
        explorer.select('DSP');
        diagram.getSelectedNodes('diagram', 'DSP').should('exist');
        diagram.getSelectedNodes('diagram', 'Motion_Engine').should('not.exist');

        explorer.select('Motion_Engine', true);
        diagram.getSelectedNodes('diagram', 'DSP').should('exist');
        diagram.getSelectedNodes('diagram', 'Motion_Engine').should('exist');
      });
    });

    context('When we interact with the details view', () => {
      it('Then diagram selection is preserved when using the details view', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();

        explorer.expand('Central_Unit');
        explorer.select('DSP');
        diagram.getSelectedNodes('diagram', 'DSP').should('exist');

        const details = new Details();
        details.getRadioOption('Status', 'inactive').click();

        diagram.getSelectedNodes('diagram', 'DSP').should('exist');
      });
    });
  });
});
