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
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - inside outside labels', () => {
  let domainName: string = 'diagramLabel';
  context('Given a view with inside label on rectangular node with none strategy', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - Fully display the inside label Diagram`;
      const diagramTitle = 'Fully display the inside label Diagram';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the node size increase to fully display the inside label', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram.getNodeCssValue(diagramTitle, 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue(diagramTitle, 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label one line fully displayed{enter}');
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram
            .getNodeCssValue(diagramTitle, 'very large label one line fully displayed', 'width')
            .then((nodeWidth) => {
              expect(nodeWidth / scale).to.greaterThan(initialWidth);
            });
          diagram
            .getNodeCssValue(diagramTitle, 'very large label one line fully displayed', 'height')
            .then((nodeHeight) => {
              expect(nodeHeight / scale).to.approximately(initialHeight, 2);
            });
        });
      });
    });
  });

  context('Given a view with inside label on rectangular node with wrap strategy', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - Wrap the label without changing the node width`;
      const diagramTitle = 'Wrap the label without changing the node width';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then inside label is wrapped and the node width is not updated', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram.getNodeCssValue(diagramTitle, 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue(diagramTitle, 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label one multi line after wrap{enter}');
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram
            .getNodeCssValue(diagramTitle, 'very large label one multi line after wrap', 'width')
            .then((nodeWidth) => {
              expect(nodeWidth / scale).to.approximately(initialWidth, 2);
              diagram
                .getLabelCssValue(diagramTitle, 'very large label one multi line after wrap', 'width')
                .then((labelWidth) => {
                  expect(labelWidth / scale).to.be.below(nodeWidth / scale);
                });
            });
          diagram
            .getNodeCssValue(diagramTitle, 'very large label one multi line after wrap', 'height')
            .then((nodeHeight) => {
              expect(nodeHeight / scale).to.greaterThan(initialHeight);
            });
        });
      });
    });
  });

  context('Given a view with inside label on rectangular node with ellipsis strategy', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - The label is truncated without changing the node width`;
      const diagramTitle = 'The label is truncated without changing the node width';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;

          new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it.skip('Then inside label has an ellipsis and the node width is not updated', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('small{enter}');
        diagram.fitToScreen();
        let initialWidth: number;
        let initialHeight: number;
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram.getNodeCssValue(diagramTitle, 'small', 'width').then((nodeWidth) => {
            initialWidth = nodeWidth / scale;
          });
          diagram.getNodeCssValue(diagramTitle, 'small', 'height').then((nodeHeight) => {
            initialHeight = nodeHeight / scale;
          });
        });
        details.getTextField('Name').type('{selectAll}very large label with an ellipsis{enter}');
        diagram.getDiagramScale(diagramTitle).then((scale) => {
          diagram.getNodeCssValue(diagramTitle, 'very large label with an ellipsis', 'width').then((nodeWidth) => {
            expect(nodeWidth / scale).to.approximately(initialWidth, 2);
            diagram.getLabelCssValue(diagramTitle, 'very large label with an ellipsis', 'width').then((labelWidth) => {
              expect(labelWidth / scale).to.be.below(nodeWidth / scale);
            });
          });
          diagram.getNodeCssValue(diagramTitle, 'very large label with an ellipsis', 'height').then((nodeHeight) => {
            expect(nodeHeight / scale).to.approximately(initialHeight, 2);
          });
        });
      });
    });
  });
});
