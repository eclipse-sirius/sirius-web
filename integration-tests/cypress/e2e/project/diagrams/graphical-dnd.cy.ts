/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';

describe('Graphical-dnd', () => {
  context('Given a studio template', () => {
    let studioProjectId: string = '';
    let domainName: string = '';

    before(() =>
      new Studio().createStudioProject().then((createdProjectData) => {
        studioProjectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
        const explorer = new Explorer();
        explorer.expand('DomainNewModel');
        cy.get('[title="domain::Domain"]').then(($div) => {
          domainName = $div.data().testid;
          explorer.expand(`${domainName}`);
        });
      })
    );

    after(() => cy.deleteProject(studioProjectId));
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Check graphical Drag and Drop', () => {
        const explorer = new Explorer();
        explorer.createObject('Entity2', 'Relation');
        const details = new Details();
        details.getCheckBox('Containment').check();
        details.openReferenceWidgetOptions('Target Type');
        details.selectReferenceWidgetOption('Entity1');

        explorer.expand('ViewNewModel');
        explorer.expand('View');
        explorer.expand(`${domainName} Diagram Description`);
        explorer.expand('Entity2 Node');
        details.openReferenceWidgetOptions('Reused Child Node Descriptions');
        details.selectReferenceWidgetOption('Entity1 Node');
        explorer.createObject('NodePalette', 'Drop Node Tool');
        explorer.select('Tool');
        details.getTextField('Name').type('{selectAll}Drop Entity1 Tool{Enter}');
        details.openReferenceWidgetOptions('Accepted Node Types');
        details.selectReferenceWidgetOption('Entity1 Node');
        explorer.createObject('Drop Entity1 Tool', 'Change Context');
        details.getTextField('Expression').type('{selectAll}aql:targetElement{enter}');
        explorer.createObject('aql:targetElement', 'Set Value');
        details.getTextField('Feature Name').type('relation{Enter}');
        details.getTextField('Value Expression').type('aql:droppedElement{Enter}');

        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
          new Explorer().createRepresentation('Root', `${domainName} Diagram Description`, 'diagram');
        });

        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('Entity1{Enter}');
        explorer.createObject('Root', 'Entity2s Entity2');
        details.getTextField('Name').type('Entity2{Enter}');

        const diagram = new Diagram();
        diagram.dragAndDropNode('Rectangle - Entity1', 'Rectangle - Entity2');
        diagram.isNodeInside('Rectangle - Entity1', 'Rectangle - Entity2');

        explorer.createObject('Root', 'Entity1s Entity1');
        details.getTextField('Name').type('InvalidTarget{Enter}');

        diagram.dragAndDropNode('Rectangle - Entity1', 'Rectangle - InvalidTarget');
        // The target is invalid, the move is canceled
        diagram.isNodeInside('Rectangle - Entity1', 'Rectangle - Entity2');
      });
    });
  });
});
