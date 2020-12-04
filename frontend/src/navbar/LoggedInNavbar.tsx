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
import { useBranding } from 'common/BrandingContext';
import { Logo } from 'navbar/Logo';
import { Title } from 'navbar/Title';
import React from 'react';
import styles from './LoggedInNavbar.module.css';

export const LoggedInNavbar = () => {
  const { productName, userStatus } = useBranding();
  return (
    <div className={styles.loggedInNavbar}>
      <div className={styles.container}>
        <div className={styles.leftArea}>
          <Logo title="Back to all projects" />
          <Title label={productName} />
        </div>
        <div className={styles.rightArea}>{userStatus}</div>
      </div>
    </div>
  );
};
