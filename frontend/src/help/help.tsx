/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Link } from '@material-ui/core';
import HelpIcon from '@material-ui/icons/Help';
import React from 'react';

export const Help = () => {
  return (
    <Link href="https://www.eclipse.org/sirius" rel="noopener noreferrer" target="_blank" color="inherit">
      <HelpIcon />
    </Link>
  );
};
