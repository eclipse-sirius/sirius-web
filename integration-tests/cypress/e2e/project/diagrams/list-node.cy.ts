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

import { Studio } from '../../../usecases/Studio';
import { Project } from '../../../pages/Project';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';

describe('List-node', () => {
  context('Given a studio template with a list node strategy', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expand(`${domainName}`);
          explorer.expand('ViewNewModel');
          explorer.expand('View');
          explorer.expand(`${domainName} Diagram Description`);
        });
        explorer.expand('Entity1 Node');
        explorer.createObject('Entity1 Node', 'Layout List');
      })
    );

    after(() => cy.deleteProject(studioProjectId));
    context('When we interact with the details view', () => {
      beforeEach(() => {
        new Project().visit(studioProjectId);
        const explorer = new Explorer();
        explorer.expand('ViewNewModel');
        explorer.expand('View');
        explorer.expand(`${domainName} Diagram Description`);
        explorer.expand('Entity1 Node');
        explorer.select('aql:true');
      });

      it('Then it is possible to set an Are Child Nodes Draggable Expression', () => {
        const details = new Details();
        details.getTextField('Are Child Nodes Draggable Expression').should('exist');
        details.getTextField('Are Child Nodes Draggable Expression').should('have.value', 'aql:true');
      });

      it('Then it is possible to set a Top Gap Expression', () => {
        const details = new Details();
        details.getTextField('Top Gap Expression').should('exist');
        details.getTextField('Top Gap Expression').should('have.value', '');
      });

      it('Then it is possible to set a Bottom Gap Expression', () => {
        const details = new Details();
        details.getTextField('Bottom Gap Expression').should('exist');
        details.getTextField('Bottom Gap Expression').should('have.value', '');
      });
    });
  });
});
