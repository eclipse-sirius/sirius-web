/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import { ForwardedRef, forwardRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { WorkbenchViewHandle } from './Workbench.types';
import { WorkbenchPartProps } from './WorkbenchPart.types';

const useSiteStyles = makeStyles()((_) => ({
  view: {
    display: 'flex',
    flexDirection: 'column',
    minWidth: 0,
    overflow: 'hidden',
    height: '100%',
  },
}));

export const WorkbenchPart = forwardRef<WorkbenchViewHandle, WorkbenchPartProps>(
  (
    { editingContextId, readOnly, side, contribution, initialConfiguration }: WorkbenchPartProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const { classes } = useSiteStyles();

    const { id, component: Component } = contribution;
    return (
      <div className={classes.view} data-testid={`site-${side}`}>
        <Component
          id={id}
          editingContextId={editingContextId}
          readOnly={readOnly}
          initialConfiguration={initialConfiguration}
          ref={ref}
        />
      </div>
    );
  }
);
