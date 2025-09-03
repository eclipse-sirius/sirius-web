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
import { Flow } from '../../../usecases/Flow';
import { Workbench } from '../../../workbench/Workbench';

const projectName = 'Cypress - Workbench Configuration Resolution';

describe('Workbench Configuration Resolution', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    before(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
      })
    );

    after(() => cy.deleteProject(projectId));

    context('When opening the project with a "null" workbench configuration', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: nullWorkbenchConfiguration,
          },
        });
        workbench = new Workbench();
      });
      it('Then, the left side panel is expanded, and the "Explorer" view is highlighted and active', () => {
        workbench.checkPanelState('left', 'expanded');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.checkPanelContent('left', ['Explorer']);
      });
      it('Then, the right side panel is expanded, and the "Details" view is highlighted and active', () => {
        workbench.checkPanelState('right', 'expanded');
        workbench.isIconHighlighted('right', 'Details');
        workbench.checkPanelContent('right', ['Details']);
      });
      it('Then, in the URL, the "workbenchConfiguration" search param is removed', () => {
        cy.url().should('not.include', 'workbenchConfiguration=');
      });
    });

    context('When opening the project with a workbench configuration where the side panels are collapsed', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: workbenchConfigurationWithClosedPanels,
          },
        });
        workbench = new Workbench();
      });
      it('Then, the left panel is collapsed and all views ("Explorer", "Validation") are not highlighted and not active', () => {
        workbench.checkPanelState('left', 'collapsed');
        workbench.isIconHighlighted('left', 'Explorer', false);
        workbench.isIconHighlighted('left', 'Validation', false);
        cy.getByTestId('site-left').should('not.exist');
      });
      it('Then, the right panel is collapsed and all views ("Details", "Query", "Representations", "Related Elements") are highlighted and active', () => {
        workbench.checkPanelState('right', 'collapsed');
        workbench.isIconHighlighted('right', 'Details');
        workbench.isIconHighlighted('right', 'Query');
        workbench.isIconHighlighted('right', 'Representations');
        workbench.isIconHighlighted('right', 'Related Elements');
        cy.getByTestId('site-right').should('not.exist');
      });
      it('Then, in the URL, the "workbenchConfiguration" search param is removed', () => {
        cy.url().should('not.include', 'workbenchConfiguration=');
      });
    });

    context('When opening the project with a workbench configuration where the side panels are expanded', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: workbenchConfigurationWithExpandedPanels,
          },
        });
        workbench = new Workbench();
      });
      it('Then, the left panel is expanded and all views ("Explorer", "Validation") are highlighted and active', () => {
        workbench.checkPanelState('left', 'expanded');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.isIconHighlighted('left', 'Validation');
        workbench.checkPanelContent('left', ['Explorer', 'Validation']);
      });
      it('Then, the right panel is expanded and all views ("Details", "Query", "Representations", "Related Elements") are highlighted and active', () => {
        workbench.checkPanelState('right', 'expanded');
        workbench.isIconHighlighted('right', 'Details');
        workbench.isIconHighlighted('right', 'Query');
        workbench.isIconHighlighted('right', 'Representations');
        workbench.isIconHighlighted('right', 'Related Elements');
        workbench.checkPanelContent('right', ['Details', 'Query', 'Representations', 'Related Elements']);
      });
      it('Then, in the URL, the "workbenchConfiguration" search param is removed', () => {
        cy.url().should('not.include', 'workbenchConfiguration=');
      });
    });
  });
});

const nullWorkbenchConfiguration: string = JSON.stringify(null);
const workbenchConfigurationWithClosedPanels: string = JSON.stringify({
  sidePanels: [
    {
      id: 'left',
      isOpen: false,
      views: [
        { id: 'explorer', isActive: false },
        { id: 'validation', isActive: false },
      ],
    },
    {
      id: 'right',
      isOpen: false,
      views: [
        { id: 'details', isActive: true },
        { id: 'query', isActive: true },
        { id: 'representations', isActive: true },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
});
const workbenchConfigurationWithExpandedPanels: string = JSON.stringify({
  sidePanels: [
    {
      id: 'left',
      isOpen: true,
      views: [
        { id: 'explorer', isActive: true },
        { id: 'validation', isActive: true },
      ],
    },
    {
      id: 'right',
      isOpen: true,
      views: [
        { id: 'details', isActive: true },
        { id: 'query', isActive: true },
        { id: 'representations', isActive: true },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
});
