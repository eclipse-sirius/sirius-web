/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

describe('Details - Widget-reference', () => {
  context('Given a flow project', () => {
    let flowProjectId: string = '';

    before(() =>
      new Flow().createFlowProject().then((createdProjectData) => {
        flowProjectId = createdProjectData.projectId;
        new Project().visit(flowProjectId);
      })
    );
    after(() => cy.deleteProject(flowProjectId));

    it('then diagnostic messages are display on widget reference', () => {
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('Flow');
      explorer.expandWithDoubleClick('NewSystem');
      explorer.expandWithDoubleClick('DataSource1');
      explorer.select('standard');
      const details = new Details();
      details.getReferenceWidget('Target').find('p[class*="Mui-error"]').should('not.exist');
      details.getReferenceWidget('Target').findByTestId('Target-clear').click();
      details.getReferenceWidget('Target').find('p[class*="Mui-error"]').should('exist');
    });
  });
});
