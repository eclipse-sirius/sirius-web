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

import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import { ExampleProps } from './Example.types';

export const Example = ({ label, children }: ExampleProps) => {
  return (
    <Paper
      variant="outlined"
      style={{
        padding: '20px',
        display: 'flex',
        flexDirection: 'column',
        justifyItems: 'flex-start',
        alignItems: 'flex-start',
        gap: '20px',
      }}>
      <Typography variant="h6">{label}</Typography>
      {children}
    </Paper>
  );
};
