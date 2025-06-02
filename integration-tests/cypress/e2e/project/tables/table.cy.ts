/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { Table } from '../../../workbench/Table';

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
        explorer.expandWithDoubleClick('Project');
        explorer.expandWithDoubleClick('Component');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check table representation can be created', () => {
      new Explorer().createRepresentation('Package', 'Papaya package table', 'Table');
      const table = new Table();
      table.getTableRepresentation().should('exist');
      table.checkRowCount(7);
    });

    it('Check global filter reset keep the page size', () => {
      new Explorer().createRepresentation('Package', 'Papaya package table', 'Table');
      const table = new Table();
      table.getTableRepresentation().should('exist');
      table.checkRowCount(7);
      table.changeRowPerPage(5);
      table.checkRowCount(5);
      table.typeGlobalSearch('Class');
      table.checkRowCount(4);
      table.resetGlobalSearchValue();
      table.checkRowCount(5);
    });

    it('Check paginate to the next page is working', () => {
      new Explorer().createRepresentation('Package', 'Papaya package table', 'Table');
      const table = new Table();
      table.getTableRepresentation().should('exist');
      table.checkRowCount(7);
      table.changeRowPerPage(5);
      table.checkRowCount(5);
      table.navigateNextPage();
      table.checkRowCount(2);
    });
  });
});
