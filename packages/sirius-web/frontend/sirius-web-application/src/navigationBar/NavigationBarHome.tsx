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

import { useComponent } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@mui/material/IconButton';
import Link from '@mui/material/Link';
import Tooltip from '@mui/material/Tooltip';
import { Link as RouterLink } from 'react-router-dom';
import { useNavigationBarStyles } from './NavigationBar';
import { NavigationBarHomeProps } from './NavigationBar.types';
import { navigationBarIconExtensionPoint } from './NavigationBarExtensionPoints';

export const SiriusWebNavigationBarHome: React.ComponentType<NavigationBarHomeProps> = ({}: NavigationBarHomeProps) => {
  const { classes } = useNavigationBarStyles();
  const { Component: Icon } = useComponent(navigationBarIconExtensionPoint);

  return (
    <Tooltip title="Back to the homepage">
      <Link component={RouterLink} to="/" className={classes.link} color="inherit">
        <IconButton className={classes.onDarkBackground} color="inherit">
          <Icon />
        </IconButton>
      </Link>
    </Tooltip>
  );
};
