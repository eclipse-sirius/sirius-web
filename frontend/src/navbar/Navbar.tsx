/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { useAuth } from 'auth/useAuth';
import { LoggedInNavbar } from 'navbar/LoggedInNavbar';
import { LoggedOutNavbar } from 'navbar/LoggedOutNavbar';
import React from 'react';

export const Navbar = () => {
  const { username } = useAuth() as any;
  if (username) {
    return <LoggedInNavbar />;
  }
  return <LoggedOutNavbar />;
};
