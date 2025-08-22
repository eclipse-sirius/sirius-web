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

import { Project } from '../../../pages/Project';
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Explorer } from '../../../workbench/Explorer';

describe('Details', () => {
  context('Given a studio project', () => {
    let projectId: string = '';

    beforeEach(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );
    afterEach(() => cy.deleteProject(projectId));

    it('auto completion is working when pressing ctrl+space', () => {
      const explorer = new Explorer();
      const details = new Details();
      explorer.expandWithDoubleClick('ViewNewModel');
      explorer.expandWithDoubleClick('View');
      explorer.getTreeItemContainingLabel('Description').dblclick();
      explorer.select('Entity1 Node');
      details.getTextField('Is Collapsed By Default Expression').should('exist');
      details
        .getTextField('Domain Type')
        .invoke('val')
        .then((actualValue) => {
          details.getTextField('Domain Type').clear({ force: true });
          // Open auto completion
          details.getTextField('Domain Type').type('{ctrl} ');
          cy.getByTestId('completion-proposals').should('exist');
          cy.getByTestId('completion-proposals')
            .contains(actualValue as string)
            .should('exist');
        });
    });
  });
});
