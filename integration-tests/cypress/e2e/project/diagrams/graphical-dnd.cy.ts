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
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

// Skipped because Diagram#dragAndDropNode has an issue with the coordinate: see the warning in Diagram#dragAndDropNode
describe.skip('Diagram - Graphical-dnd', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expandWithDoubleClick(`${domainName}`);
        });
      })
    );

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Check graphical Drag and Drop', () => {
        const explorer = new Explorer();
        explorer.createObject('Entity2', 'relations-Relation');
        const details = new Details();
        details.getCheckBox('Containment').check();
        details.openReferenceWidgetOptions('Target Type');
        details.selectReferenceWidgetOption('Entity1');

        explorer.expandWithDoubleClick('ViewNewModel');
        explorer.expandWithDoubleClick('View');
        explorer.expandWithDoubleClick(`${domainName} Diagram Description`);
        explorer.expandWithDoubleClick('Entity2 Node');
        details.openReferenceWidgetOptions('Reused Child Node Descriptions');
        details.selectReferenceWidgetOption('Entity1 Node');
        explorer.createObject('NodePalette', 'dropNodeTool-DropNodeTool');
        explorer.select('Tool');
        details.getTextField('Name').type('{selectAll}Drop Entity1 Tool{Enter}');
        details.openReferenceWidgetOptions('Accepted Node Types');
        details.selectReferenceWidgetOption('Entity1 Node');
        explorer.createObject('Drop Entity1 Tool', 'body-ChangeContext');
        details.getTextField('Expression').type('{selectAll}aql:targetElement{enter}');
        explorer.createObject('aql:targetElement', 'children-SetValue');
        details.getTextField('Feature Name').type('relation{Enter}');
        details.getTextField('Value Expression').type('aql:droppedElement{Enter}');

        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Root', 'entity2s-Entity2');
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.dragAndDropNode('FreeForm - Entity1', 'FreeForm - Entity2');
        diagram.isNodeInside('FreeForm - Entity1', 'FreeForm - Entity2');

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('InvalidTarget{Enter}');

        diagram.dragAndDropNode('FreeForm - Entity1', 'FreeForm - InvalidTarget');
        // The target is invalid, the move is canceled
        diagram.isNodeInside('FreeForm - Entity1', 'FreeForm - Entity2');
      });
    });
  });
});
