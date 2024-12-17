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
import { Explorer } from '../../../workbench/Explorer';

describe('Details', () => {
  context('Given a flow project', () => {
    let flowProjectId: string = '';

    before(() =>
      new Flow().createFlowProject().then((createdProjectData) => {
        flowProjectId = createdProjectData.projectId;
        new Project().visit(flowProjectId);
      })
    );
    after(() => cy.deleteProject(flowProjectId));

    it('no details are shown when everything is deselected', () => {
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('Flow');
      explorer.expandWithDoubleClick('NewSystem');
      explorer.expandWithDoubleClick('CompositeProcessor1');
      explorer.select('CompositeProcessor1');
      const details = new Details();
      details.getTextField('Name').should('exist');
      // Deselect
      explorer.select('CompositeProcessor1', true);
      details.getTextField('Name').should('not.exist');
      details.getDetailsView().find('h6').should('have.text', 'No object selected');
    });
  });
});
