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

    context('When opening the project with a null workbench configuration', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: nullWorkbenchConfiguration,
          },
        });
        workbench = new Workbench();
      });
      it('should, in the left side panel, select and expand the Explorer view', () => {
        workbench.checkPanelContent('left', 'Explorer');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.checkPanelState('left', 'expanded');
      });
      it('should, in the right side panel, select and expand the Details view', () => {
        workbench.checkPanelContent('right', 'Details');
        workbench.isIconHighlighted('right', 'Details');
        workbench.checkPanelState('right', 'expanded');
      });
    });

    context('When opening the project with a default workbench configuration', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Project().visit(projectId, {
          qs: {
            workbenchConfiguration: workbenchConfigurationWithDefaultValues,
          },
        });
        workbench = new Workbench();
      });
      it('should, in the left side panel, select and expand the Explorer view', () => {
        workbench.checkPanelContent('left', 'Explorer');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.checkPanelState('left', 'expanded');
      });
      it('should, in the right side panel, select and expand the Details view', () => {
        workbench.checkPanelContent('right', 'Details');
        workbench.isIconHighlighted('right', 'Details');
        workbench.checkPanelState('right', 'expanded');
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
      it('should, in the left side panel, select and expand the Validation view', () => {
        workbench.checkPanelContent('left', 'Validation');
        workbench.isIconHighlighted('left', 'Validation');
        workbench.checkPanelState('left', 'expanded');
      });
      it('should, in the right side panel, select and expand the Related Elements view', () => {
        workbench.checkPanelContent('right', 'Related Elements');
        workbench.isIconHighlighted('right', 'Related Elements');
        workbench.checkPanelState('right', 'expanded');
      });
    });
  });
});

const nullWorkbenchConfiguration: string = JSON.stringify(null);
const workbenchConfigurationWithDefaultValues: string = JSON.stringify({
  sidePanels: [
    {
      id: 'left',
      views: [
        { id: 'explorer', isActive: true },
        { id: 'validation', isActive: false },
      ],
    },
    {
      id: 'right',
      views: [
        { id: 'details', isActive: true },
        { id: 'query', isActive: false },
        { id: 'representations', isActive: false },
        { id: 'related-elements', isActive: false },
      ],
    },
  ],
});
const workbenchConfigurationWithCustomValues: string = JSON.stringify({
  sidePanels: [
    {
      id: 'left',
      views: [
        { id: 'explorer', isActive: false },
        { id: 'validation', isActive: true },
      ],
    },
    {
      id: 'right',
      views: [
        { id: 'details', isActive: false },
        { id: 'query', isActive: false },
        { id: 'representations', isActive: false },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
});
