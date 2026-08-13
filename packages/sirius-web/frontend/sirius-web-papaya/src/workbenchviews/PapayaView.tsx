/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import {
  ViewAccordion,
  ViewAccordionContent,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { ForwardedRef, forwardRef } from 'react';

export const PapayaView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id }: WorkbenchViewComponentProps, _ref: ForwardedRef<WorkbenchViewHandle>) => {
    return (
      <ViewAccordion id={id} title="Papaya View">
        <ViewAccordionContent>
          <Box sx={{ flexGrow: 1, minHeight: 0 }}>Papaya View</Box>
        </ViewAccordionContent>
      </ViewAccordion>
    );
  }
);
