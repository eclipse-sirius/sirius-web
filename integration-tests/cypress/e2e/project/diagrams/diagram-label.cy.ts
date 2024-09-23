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
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - inside outside labels', () => {
  context.skip('Given a view with inside label on rectangular node with none strategy', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() => {
      cy.createProjectFromTemplate('studio-template').then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;

          const project = new Project();
          project.visit(projectId);
          project.disableDeletionConfirmationDialog();

          const explorer = new Explorer();
          const details = new Details();
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.expand('Entity1 Node');
            explorer.select('aql:self.name');
            details.getRadioOption('Overflow Strategy', 'NONE').click();
          });
        }
      });
    });

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the node size increase to fully display the inside label', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue('diagram', 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label one line fully displayed{enter}');
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'very large label one line fully displayed', 'width').then((nodeWidth) => {
            expect(nodeWidth / scale).to.greaterThan(initialWidth);
          });
          diagram
            .getNodeCssValue('diagram', 'very large label one line fully displayed', 'height')
            .then((nodeHeight) => {
              expect(nodeHeight / scale).to.approximately(initialHeight, 2);
            });
        });
      });
    });
  });

  context.skip('Given a view with inside label on rectangular node with wrap strategy', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() => {
      cy.createProjectFromTemplate('studio-template').then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;

          const project = new Project();
          project.visit(projectId);
          project.disableDeletionConfirmationDialog();

          const explorer = new Explorer();
          const details = new Details();
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.expand('Entity1 Node');
            explorer.select('aql:self.name');
            details.getRadioOption('Overflow Strategy', 'WRAP').click();
          });
        }
      });
    });

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then inside label is wrapped and the node width is not updated', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue('diagram', 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label one multi line after wrap{enter}');
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram
            .getNodeCssValue('diagram', 'very large label one multi line after wrap', 'width')
            .then((nodeWidth) => {
              expect(nodeWidth / scale).to.approximately(initialWidth, 2);
            });
          diagram
            .getNodeCssValue('diagram', 'very large label one multi line after wrap', 'height')
            .then((nodeHeight) => {
              expect(nodeHeight / scale).to.greaterThan(initialHeight);
            });
        });
      });
    });
  });

  context.skip('Given a view with inside label on rectangular node with ellipsis strategy', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() => {
      cy.createProjectFromTemplate('studio-template').then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const projectId = payload.project.id;
          studioProjectId = projectId;

          const project = new Project();
          project.visit(projectId);
          project.disableDeletionConfirmationDialog();

          const explorer = new Explorer();
          const details = new Details();
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.expand('Entity1 Node');
            explorer.select('aql:self.name');
            details.getRadioOption('Overflow Strategy', 'ELLIPSIS').click();
          });
        }
      });
    });

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then inside label has an ellipsis and the node width is not updated', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue('diagram', 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label with an ellipsis{enter}');
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'very large label with an ellipsis', 'width').then((nodeWidth) => {
            expect(nodeWidth / scale).to.approximately(initialWidth, 2);
          });
          diagram.getNodeCssValue('diagram', 'very large label with an ellipsis', 'height').then((nodeHeight) => {
            expect(nodeHeight / scale).to.approximately(initialHeight, 2);
          });
        });
      });
    });
  });
});
