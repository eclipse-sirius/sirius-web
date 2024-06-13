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
  context('Given a view with inside and outside labels on rectangular and image node', () => {
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
            details.getTextField('Label Expression').type('{selectAll}InsideLabelNode1{enter}');
            explorer.createObject('Entity1 Node', 'Outside Label Description');
            explorer.select('aql:self.name');
            details.getTextField('Label Expression').type('{selectAll}OutsideLabelNode1{enter}');
            explorer.collapse('Entity1 Node');
            explorer.expand('Entity2 Node');
            explorer.select('aql:self.name');
            details.getTextField('Label Expression').type('{selectAll}InsideLabelNode2{enter}');
            explorer.createObject('Entity2 Node', 'Outside Label Description');
            explorer.select('aql:self.name');
            details.getTextField('Label Expression').type('{selectAll}OutsideLabelNode2{enter}');
            explorer.delete('RectangularNodeStyleDescription');
            explorer.createObject('Entity2 Node', 'Style Image');
            details.selectValue('Shape', 'camera');
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

      it('Then inside and outside label are display for both nodes', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Root', 'Entity2s Entity2');

        diagram.getNodes('diagram', 'InsideLabelNode1').findByTestId('Label - InsideLabelNode1').should('exist');
        diagram.getNodes('diagram', 'InsideLabelNode1').findByTestId('Label - OutsideLabelNode1').should('exist');
      });
    });
  });

  context('Given a view with inside label on rectangular node with none strategy', () => {
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

      it('Then inside label is fully displayed', () => {
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

  context('Given a view with inside label on rectangular node with wrap strategy', () => {
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

      it('Then inside label is wrapped', () => {
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

  context('Given a view with inside label on rectangular node with ellipsis strategy', () => {
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

      it('Then inside label has an ellipsis', () => {
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

  context('Given a view with outside label on rectangular node with none strategy', () => {
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
          explorer.getTreeItemByLabel('DomainNewModel').dblclick();
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.expand('Entity1 Node');
            explorer.delete('aql:self.name');
            explorer.createObject('Entity1 Node', 'Outside Label Description');
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

      it('Then the outside label is not truncated', () => {
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
        details.getTextField('Name').type('{selectAll}very large label without wrap{enter}');
        diagram.getDiagramScale('diagram').then((scale) => {
          diagram.getNodeCssValue('diagram', 'very large label without wrap', 'width').then((nodeWidth) => {
            expect(nodeWidth / scale).to.approximately(initialWidth, 2);
          });
          diagram.getNodeCssValue('diagram', 'very large label without wrap', 'height').then((nodeHeight) => {
            expect(nodeHeight / scale).to.approximately(initialHeight, 2);
          });
        });
        diagram.getLabel('very large label without wrap').should('exist');
        details.getTextField('Name').type('{selectAll}very large label {shift}{enter}with wrap');
        details.getTextField('Name').type('{enter}');
        diagram.getLabel('very large label \nwith wrap').should('exist');
      });
    });
  });
  context('Given a studio with a top header label node', () => {
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
          explorer.expand('DomainNewModel');
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand(domainName);
            explorer.createObject('Entity1', 'Relation');
            details.getCheckBox('Containment').check();
            details.openReferenceWidgetOptions('Target Type');
            details.selectReferenceWidgetOption('Entity2');
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.select('Entity1 Node');
            details.getCheckBox('Collapsible').check();
            details.openReferenceWidgetOptions('Reused Child Node Descriptions');
            details.selectReferenceWidgetOption('Entity2 Node');
            explorer.expand('Entity1 Node');
            explorer.select('aql:self.name');
            details.getRadioOption('Position', 'TOP_LEFT').click();
            explorer.expand('aql:self.name');
            explorer.select('InsideLabelStyle');
            details.getCheckBox('With Header').check();
            details.getCheckBox('Display Header Separator').check();
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
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the separator is display under the label', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Entity1', 'Relation Entity2');
        explorer.select('Entity1');
        details.getTextField('Name').type('Entity1{enter}');
        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        diagram.fitToScreen();
        diagram
          .getNodes('diagram', 'Entity1')
          .findByTestId('Label bottom separator - Entity1')
          .should('have.css', 'border-bottom', '1px solid rgb(51, 176, 195)');
        diagram.getNodes('diagram', 'Entity1').findByTestId('Label top separator - Entity1').should('not.exist');
      });
    });
  });
  context('Given a studio with a bottom header label node', () => {
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
          explorer.expand('DomainNewModel');
          cy.get('[title="domain::Domain"]').then(($div) => {
            domainName = $div.data().testid;
            explorer.expand(domainName);
            explorer.createObject('Entity1', 'Relation');
            details.getCheckBox('Containment').check();
            details.openReferenceWidgetOptions('Target Type');
            details.selectReferenceWidgetOption('Entity2');
            explorer.expand('ViewNewModel');
            explorer.expand('View');
            explorer.expand(`${domainName} Diagram Description`);
            explorer.select('Entity1 Node');
            details.getCheckBox('Collapsible').check();
            details.openReferenceWidgetOptions('Reused Child Node Descriptions');
            details.selectReferenceWidgetOption('Entity2 Node');
            explorer.expand('Entity1 Node');
            explorer.select('aql:self.name');
            details.getRadioOption('Position', 'BOTTOM_RIGHT').click();
            explorer.expand('aql:self.name');
            explorer.select('InsideLabelStyle');
            details.getCheckBox('With Header').check();
            details.getCheckBox('Display Header Separator').check();
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
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the separator is display under the label', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'Entity1s Entity1');
        explorer.createObject('Entity1', 'Relation Entity2');
        explorer.select('Entity1');
        details.getTextField('Name').type('Entity1{enter}');
        explorer.createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        diagram.fitToScreen();
        diagram
          .getNodes('diagram', 'Entity1')
          .findByTestId('Label top separator - Entity1')
          .should('have.css', 'border-top', '1px solid rgb(51, 176, 195)');
        diagram.getNodes('diagram', 'Entity1').findByTestId('Label bottom separator - Entity1').should('not.exist');
      });
    });
  });
});
