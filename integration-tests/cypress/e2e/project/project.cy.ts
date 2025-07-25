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

import { Project } from '../../pages/Project';
import { Flow } from '../../usecases/Flow';

const projectName = 'Cypress - Project rename';
describe('Project - Rename', () => {
  context('Given a Robot flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we try to rename with an invalid project name', () => {
      it('Then the rename button from the rename modal should be disabled', () => {
        const renameDialog = new Project().getProjectNavigationBar(projectName).getRenameDialog();
        renameDialog.clearValue();
        renameDialog.validate(false);
      });
    });
  });
});
