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

const projectName = 'Cypress - explorer';

describe('Explorer', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    it('should initially display the first view in side panels', () => {
      const workbench = new Workbench();
      workbench.checkPanelContent('left', ['Explorer']);
      workbench.checkPanelContent('right', ['Details']);
    });

    it('should display a specific view when selected from its icon', () => {
      const workbench = new Workbench();
      // left
      workbench.selectPanelView('left', 'Validation');
      workbench.checkPanelContent('left', ['Explorer', 'Validation']);
      workbench.isIconHighlighted('left', 'Validation');
      workbench.isIconHighlighted('left', 'Explorer');
      // right
      workbench.selectPanelView('right', 'Query');
      workbench.checkPanelContent('right', ['Details', 'Query']);
      workbench.isIconHighlighted('right', 'Details');
      workbench.isIconHighlighted('right', 'Query');
      workbench.isIconHighlighted('right', 'Representations', false);
      workbench.isIconHighlighted('right', 'Related Elements', false);

      workbench.selectPanelView('right', 'Representations');
      workbench.checkPanelContent('right', ['Details', 'Query', 'Representations']);
      workbench.isIconHighlighted('right', 'Details');
      workbench.isIconHighlighted('right', 'Query');
      workbench.isIconHighlighted('right', 'Representations');
      workbench.isIconHighlighted('right', 'Related Elements', false);

      workbench.selectPanelView('right', 'Related Elements');
      workbench.checkPanelContent('right', ['Details', 'Query', 'Representations', 'Related Elements']);
      workbench.isIconHighlighted('right', 'Details');
      workbench.isIconHighlighted('right', 'Query');
      workbench.isIconHighlighted('right', 'Representations');
      workbench.isIconHighlighted('right', 'Related Elements');
    });

    it("should toggle state when clicking on the selected view's icon (if expanded or collapsed)", () => {
      const workbench = new Workbench();
      // left
      // once expanded, it should close
      workbench.selectPanelView('left', 'Explorer');
      workbench.checkPanelState('left', 'collapsed');
      // once collapsed, it should open
      workbench.selectPanelView('left', 'Explorer');
      workbench.checkPanelState('left', 'expanded');
      //right
      workbench.selectPanelView('right', 'Details');
      workbench.checkPanelState('right', 'collapsed');
      workbench.selectPanelView('right', 'Details');
      workbench.checkPanelState('right', 'expanded');
    });

    it('should toggle state when closed but a different view is selected', () => {
      const workbench = new Workbench();
      // left
      // first close Explorer
      workbench.selectPanelView('left', 'Explorer');
      workbench.checkPanelState('left', 'collapsed');
      // then select Validation
      workbench.selectPanelView('left', 'Validation');
      workbench.checkPanelState('left', 'expanded');
      //right
      workbench.selectPanelView('right', 'Details');
      workbench.checkPanelState('right', 'collapsed');
      workbench.selectPanelView('right', 'Query');
      workbench.checkPanelState('right', 'expanded');
    });

    it('should not toggle state when expanded and a different view is selected', () => {
      const workbench = new Workbench();
      // left
      workbench.checkPanelState('left', 'expanded');
      workbench.selectPanelView('left', 'Validation');
      workbench.checkPanelState('left', 'expanded');
      // right
      workbench.checkPanelState('right', 'expanded');
      workbench.selectPanelView('right', 'Query');
      workbench.checkPanelState('right', 'expanded');
    });
  });
});
