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

import { Project } from '../../pages/Project';
import { Flow } from '../../usecases/Flow';
import { Explorer } from '../../workbench/Explorer';
import { Omnibox } from '../../workbench/Omnibox';

const projectName = 'Cypress - Omnibox';
describe('Project - Omnibox', () => {
  context('Given a Robot flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then the omnibox can be display and used to select an element in the workbench', () => {
      const explorer = new Explorer();
      const omnibox = new Omnibox(projectName);
      omnibox.display();
      omnibox.sendQuery('').findByTestId('DSP').click();
      omnibox.shouldBeClosed();
      explorer.getSelectedTreeItems().contains('DSP').should('exist');
    });
  });
});
