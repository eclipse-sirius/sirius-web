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

import { Project } from "../../../pages/Project";
import { Flow } from "../../../usecases/Flow";
import { Studio } from "../../../usecases/Studio";
import { Details } from "../../../workbench/Details";
import { Explorer } from "../../../workbench/Explorer";

describe('/projects/:projectId/edit - Form', () => {
  context('Given a Form Description', () => {
    let studioProjectId: string = '';

    before(() =>
        new Studio().createBlankStudioProjectWithView().then((createdProjectData) => {
          studioProjectId = createdProjectData.projectId;
          new Project().visit(createdProjectData.projectId);
          const explorer = new Explorer();
          explorer.expand('ViewDocument');
          explorer.expand('View');
          explorer.createObject('View', 'Form Description');
          explorer.select('New Form Description');
          const details = new Details();
          details.getTextField('Domain Type').type('flow::System');
          details.getTextField('Name').type('{selectall}').type('ReadOnlyRepresentation');
          details.getTextField('Title Expression').type('{selectall}').type('ReadOnlyRepresentation');
          explorer.expand('ReadOnlyRepresentation');
          explorer.expand('PageDescription');
          explorer.select('GroupDescription')
          details.getTextField('Name').type('Group ReadOnly{enter}');
          explorer.createObject('Group ReadOnly', 'Widgets Button Description');
          details.getTextField('Button Label Expression').type('Test Button');
          details.getTextField('Is Enabled Expression').type('aql:self.temperature==0');
          explorer.createObject('Group ReadOnly', 'Widgets Flexbox Container Description');
          details.getTextField('Name').invoke('text').should('eq', '');
          details.getTextField('Name').type('Flexbox Container');
          details.getTextField('Label Expression').type('Test flexbox container');
          details.getTextField('Is Enabled Expression').type("aql:self.name='NewSystem'");

          explorer.createObject('Flexbox Container', 'Textfield Description');
          details.getTextField('Name').invoke('text').should('eq', '');
          details.getTextField('Label Expression').type('Name');
          details.getTextField('Value Expression').type('aql:self.name');
        })
    );
    after(() => cy.deleteProject(studioProjectId));

    context('When we create a new instance project', () => {
      let flowProjectId: string = '';

      beforeEach(() => {
        new Flow().createFlowProject().then((res) => {
          flowProjectId = res.projectId;
          new Project().visit(flowProjectId);
        });
      });

      afterEach(() => cy.deleteProject(flowProjectId));

      it('check widget read-only mode in form', () => {
          const explorer = new Explorer();
          explorer.expand('FlowNewModel');
          explorer.createRepresentation('NewSystem', 'ReadOnlyRepresentation', 'ReadOnlyRepresentation');
          cy.getByTestId('Test Button').should('exist').should('not.be.disabled');
          explorer.select('NewSystem');
          const details = new Details();
          details.getTextField('Temperature').type('{selectall}2{enter}');
          cy.getByTestId('Test Button').should('be.disabled');
          details.getTextField('Temperature').type('{selectall}0{enter}');
          cy.getByTestId('Test Button').should('not.be.disabled');
      });

      it('check the flexbox read-only mode is dispatched to children', () => {
        const explorer = new Explorer();
        explorer.expand('FlowNewModel');
        explorer.createRepresentation('NewSystem', 'ReadOnlyRepresentation', 'ReadOnlyRepresentation');
        cy.getByTestId('page').findByTestId('input-Name').should('not.be.disabled');
        cy.getByTestId('NewSystem').click();
        cy.getByTestId('form').findByTestId('Name').type('2').type('{enter}');
        cy.getByTestId('page').findByTestId('input-Name').should('be.disabled');
      });
    });


  });
});
