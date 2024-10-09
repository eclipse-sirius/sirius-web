/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { Papaya } from '../../../usecases/Papaya';
import { Explorer } from '../../../workbench/Explorer';

describe('Tables', () => {
  context('Given a papaya project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Papaya().createPapayaTableExempleProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Papaya');
        explorer.expandWithDoubleClick('Project Project');
        explorer.expandWithDoubleClick('Component Component');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check table representation can be created', () => {
      new Explorer().createRepresentation('Package Package', 'Papaya package table', 'Table');
      cy.getByTestId('table-representation').should('exist');
    });
  });
});
