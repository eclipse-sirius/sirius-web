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
import { Studio } from '../../../usecases/Studio';
import { DataTree } from '../../../workbench/DataTree';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';
import { ImpactAnalysis } from '../../../workbench/ImpactAnalysis';

describe('Impact analysis - diagram', () => {
  const domainName: string = 'diagramImpactAnalysis';
  context('Given a view with a node tool with impact analysis', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple node`;
      const diagramTitle = 'Impact analysis diagram';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the tool trigger the impact analysis dialog with the good report', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        const impactAnalysis = new ImpactAnalysis();
        const dataTree = new DataTree();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('InitialName{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);

        diagram.getNodes('Impact analysis diagram', 'InitialName').findByTestId('Label - InitialName').rightclick();
        diagram.getPalette().should('exist');
        diagram.getPalette().findByTestId('tool-Tool with impact analysis').click();
        impactAnalysis.getImpactAnalysisDialog().should('exist');
        impactAnalysis.getNbElementModified().should('contain', 'Elements modified: 2');
        impactAnalysis.getNbElementCreated().should('contain', 'Elements added: 1');
        dataTree.getDataTree().should('exist');
        dataTree.expandWithDoubleClick('Others...');
        dataTree.expandWithDoubleClick('Root');
        dataTree.getTreeItemByLabel('entity1s: Entity1').should('exist');
        dataTree.expandWithDoubleClick('newName');
        dataTree.getTreeItemByLabel('name: InitialName -> newName').should('exist');
      });
    });
  });
});
