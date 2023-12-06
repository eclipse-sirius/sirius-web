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

describe('/upload/project', () => {
  beforeEach(() => new UploadProject().visit());

  it('contains a proper project upload form', () => {
    new UploadProject()
      .getFileInput()
      .should('have.attr', 'type', 'file')
      .should('have.attr', 'name', 'file')
      .closest('form')
      .should('have.attr', 'enctype', 'multipart/form-data');
  });

  it('requires a file', () => {
    new UploadProject().getUploadProjectButton().should('be.disabled');
  });
});
