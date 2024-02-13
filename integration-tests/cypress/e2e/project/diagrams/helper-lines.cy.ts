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
import { Explorer } from '../../../workbench/Explorer';

describe('helper lines - Diagram', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
        const explorer = new Explorer();
        explorer.expand('FlowNewModel');
        explorer.expand('NewSystem');
        explorer.select('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check helper lines are enabled from diagram panel', () => {
      cy.getByTestId('show-helper-lines').should('exist');
      cy.getByTestId('hide-helper-lines').should('not.exist');
      cy.getByTestId('show-helper-lines').click();
      cy.getByTestId('show-helper-lines').should('not.exist');
      cy.getByTestId('hide-helper-lines').should('exist');
    });

  });
});
