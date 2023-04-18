/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

describe('/upload/project', () => {
  beforeEach(() => {
    cy.visit('/upload/project');
  });

  it('contains a proper project upload form', () => {
    cy.getByTestId('file').should('have.attr', 'type', 'file');
    cy.getByTestId('file').should('have.attr', 'name', 'file');
    cy.getByTestId('file').closest('form').should('have.attr', 'enctype', 'multipart/form-data');
  });

  it('requires a file', () => {
    cy.getByTestId('upload-project').should('be.disabled');
  });
});
