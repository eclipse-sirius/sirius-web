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

import { UploadProject } from '../../../pages/UploadProject';

describe('Project upload', () => {
  context('Given the upload project form', () => {
    beforeEach(() => new UploadProject().visit());

    context('When we manipulate the form', () => {
      it('Then it contains all the expected fields', () => {
        new UploadProject()
          .getFileInput()
          .should('have.attr', 'type', 'file')
          .should('have.attr', 'name', 'file')
          .closest('form')
          .should('have.attr', 'enctype', 'multipart/form-data');
      });

      it('Then it requires a file', () => {
        new UploadProject().getUploadProjectButton().should('be.disabled');
      });
    });
  });
});
