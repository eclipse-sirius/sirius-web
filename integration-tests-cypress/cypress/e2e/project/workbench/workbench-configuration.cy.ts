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
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';
import { Workbench } from '../../../workbench/Workbench';
import {
  workbenchConfigurationWithClosedPanels,
  workbenchConfigurationWithExpandedPanels,
  workbenchConfigurationWithQueryView,
} from './workbench-configuration.data';

const projectName = 'Cypress - Workbench Configuration Resolution';

describe('Workbench Configuration Resolution', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string;
    let topography1Id: string;
    let topography2Id: string;
    before(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
        const explorer: Explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.createRepresentation('System', 'Topography', 'Topography1').then((createdRepresentationData) => {
          topography1Id = createdRepresentationData.representationId;
        });
        explorer.createRepresentation('System', 'Topography', 'Topography2').then((createdRepresentationData) => {
          topography2Id = createdRepresentationData.representationId;
        });
      })
    );

    after(() => cy.deleteProject(projectId));

    context('When opening the project with a "null" workbench configuration', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, undefined, {
          qs: {
            workbenchConfiguration: JSON.stringify(null),
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

      it('Then, in the main area, no representation editor is opened', () => {
        workbench.checkRepresentationEditorTabs([]);
      });
    });

    context('When opening the project with a workbench configuration where the side panels are collapsed', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, undefined, {
          qs: {
            workbenchConfiguration: JSON.stringify(workbenchConfigurationWithClosedPanels),
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

      it('Then, in the main area, no representation editor is opened', () => {
        workbench.checkRepresentationEditorTabs([]);
      });
    });

    context('When opening the project with a workbench configuration where the side panels are expanded', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, undefined, {
          qs: {
            workbenchConfiguration: JSON.stringify(workbenchConfigurationWithExpandedPanels),
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

      it('Then, in the main area, no representation editor is opened', () => {
        workbench.checkRepresentationEditorTabs([]);
      });
    });

    context(
      'When opening the project with a workbench configuration where representation editors are specified',
      () => {
        let workbench: Workbench;
        beforeEach(() => {
          new Project().visit(projectId, topography2Id, {
            qs: {
              workbenchConfiguration: createWorkbenchConfigurationWithRepresentationEditors([
                {
                  representationId: topography1Id,
                  isActive: false,
                },
                {
                  representationId: topography2Id,
                  isActive: true,
                },
              ]),
            },
          });
          workbench = new Workbench();
        });

        it('Then, in the URL, the "workbenchConfiguration" search param is removed', () => {
          cy.url().should('not.include', 'workbenchConfiguration=');
        });

        it('Then, in the main area, the specified representation editors are opened', () => {
          cy.getByTestId('navbar-title').should('contain.text', projectName);
          workbench.checkRepresentationEditorTabs([
            {
              representationLabel: 'Topography1',
            },
            {
              representationLabel: 'Topography2',
            },
          ]);
          const diagram: Diagram = new Diagram();
          diagram.getDiagram('Topography1').should('not.exist');
          diagram.getDiagram('Topography2').should('exist');
        });
      }
    );

    context('When opening the project with a workbench configuration with a configuration for the "Query" view', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, undefined, {
          qs: {
            workbenchConfiguration: JSON.stringify(workbenchConfigurationWithQueryView),
          },
        });
        workbench = new Workbench();
      });
      it('Then, in the "Query" view, the text is set as specified in the configuration', () => {
        workbench.checkPanelContent('right', ['Query']);
        cy.getByTestId('query-textfield')
          .should('exist')
          .get('div')
          .get('textarea')
          .invoke('val')
          .should('equal', 'aql:self');
      });
    });
  });
});

const createWorkbenchConfigurationWithRepresentationEditors: (
  representationEditorConfigurations: RepresentationEditorConfiguration[]
) => string = (representationEditorConfigurations) => {
  return JSON.stringify({
    mainPanel: {
      id: 'main',
      representationEditors: representationEditorConfigurations,
    },
  });
};

type RepresentationEditorConfiguration = {
  representationId: string;
  isActive: boolean;
};
