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
    });

    context('When opening the project with a custom workbench configuration', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: workbenchConfigurationWithCustomValues,
          },
        });
        workbench = new Workbench();
      });
      it('Then, the left side panel is expanded, and the "Explorer" and "Validation" views are highlighted and active', () => {
        workbench.checkPanelState('left', 'expanded');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.isIconHighlighted('left', 'Validation');
        workbench.checkPanelContent('left', ['Explorer', 'Validation']);
      });
      it('Then, the right side panel is expanded, and the "Representations" and "Related Elements" views are highlighted and active', () => {
        workbench.checkPanelState('right', 'expanded');
        workbench.isIconHighlighted('right', 'Representations');
        workbench.isIconHighlighted('right', 'Related Elements');
        workbench.checkPanelContent('right', ['Representations', 'Related Elements']);
      });
    });
  });
});

const nullWorkbenchConfiguration: string = JSON.stringify(null);
const workbenchConfigurationWithCustomValues: string = JSON.stringify({
  sidePanels: [
    {
      id: 'left',
      views: [
        { id: 'explorer', isActive: true },
        { id: 'validation', isActive: true },
      ],
    },
    {
      id: 'right',
      views: [
        { id: 'details', isActive: false },
        { id: 'query', isActive: false },
        { id: 'representations', isActive: true },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
});
