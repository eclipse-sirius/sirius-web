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

import { Projects } from '../../pages/Projects';
import { Flow } from '../../usecases/Flow';

const projectName = 'Cypress - Project';

describe('Project Browser', () => {
  context('Given a project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createProjectData) => {
        projectId = createProjectData.projectId;
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we try to type specific letters to rename the project', () => {
      beforeEach(() => new Projects().visit());
      const letter = 'r';

      /**
       * The text field was loosing the focus because the menu in the background had a item starting with the letter 'r'.
       */
      it('Then the rename text field should keep the focus', () => {
        const projects = new Projects();
        const renameDialog = projects.getActionMenu(projectName).getRenameDialog();
        const innerRenameTextField = renameDialog.getInnerRenameTextField();

        innerRenameTextField.should('have.focus');
        innerRenameTextField.trigger('keydown', { key: letter });
        innerRenameTextField.should('have.focus');
      });
    });
  });
});
