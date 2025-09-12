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

import { WorkbenchConfiguration } from './workbench-configuration.data.types';

export const workbenchConfigurationWithClosedPanels: WorkbenchConfiguration = {
  mainPanel: null,
  workbenchPanels: [
    {
      id: 'left',
      isOpen: false,
      views: [
        { id: 'explorer', isActive: false },
        { id: 'validation', isActive: false },
      ],
    },
    {
      id: 'right',
      isOpen: false,
      views: [
        { id: 'details', isActive: true },
        { id: 'query', isActive: true },
        { id: 'representations', isActive: true },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
};

export const workbenchConfigurationWithExpandedPanels: WorkbenchConfiguration = {
  mainPanel: null,
  workbenchPanels: [
    {
      id: 'left',
      isOpen: true,
      views: [
        { id: 'explorer', isActive: true },
        { id: 'validation', isActive: true },
      ],
    },
    {
      id: 'right',
      isOpen: true,
      views: [
        { id: 'details', isActive: true },
        { id: 'query', isActive: true },
        { id: 'representations', isActive: true },
        { id: 'related-elements', isActive: true },
      ],
    },
  ],
};
