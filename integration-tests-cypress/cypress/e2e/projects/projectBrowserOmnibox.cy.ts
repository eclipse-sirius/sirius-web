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

import { Projects } from '../../pages/Projects';
import { Omnibox } from '../../workbench/Omnibox';

describe('Project Browser - Omnibox', () => {
  context('Given the project browser', () => {
    beforeEach(() => new Projects().visit());

    it('Then the omnibox can be displayed and used to create a new project', () => {
      const omnibox = new Omnibox();
      omnibox.display();
      omnibox.sendQuery('').findByTestId('New project').click();
      omnibox.shouldBeClosed();
      cy.location().should((location) => {
        expect(location.pathname).to.eq('/new/project');
      });
    });
  });
});
