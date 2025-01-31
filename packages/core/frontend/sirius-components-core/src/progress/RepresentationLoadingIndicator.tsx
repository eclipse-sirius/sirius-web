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
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress';

export const RepresentationLoadingIndicator = () => {
  return (
    <LinearProgress
      variant="indeterminate"
      color="inherit"
      sx={(theme) => ({
        [`&.${linearProgressClasses.root}`]: {
          height: '2px',
          backgroundColor: theme.palette.grey[200],
          color: theme.palette.grey[500],
        },
      })}
    />
  );
};
