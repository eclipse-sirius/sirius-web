/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Library } from '../../../pages/Library';
import { Workbench } from '../../../workbench/Workbench';

describe('Library Workbench Configuration', () => {
  context('Given a library', () => {
    const namespace = 'papaya';
    const name = 'java';
    const version = '0.0.1';

    context('When the library is opened', () => {
      let workbench: Workbench;
      beforeEach(() => {
        new Library().visit(namespace, name, version);
        workbench = new Workbench();
      });

      it('Then the default workbench configuration for papaya library should be displayed', () => {
        workbench.checkPanelState('left', 'expanded');
        workbench.isIconHighlighted('left', 'Explorer');
        workbench.checkPanelContent('left', ['Explorer']);
        workbench.checkPanelState('right', 'expanded');
        workbench.isIconHighlighted('right', 'Details');
        workbench.checkPanelContent('right', ['Details']);
      });
    });
  });
});
