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
import { Text } from 'core/text/Text';
import React from 'react';
import styles from './Footer.module.css';

/**
 * Defines the footer content of the user interface.
 *
 * @author hmarchadour
 */
export const Footer = () => {
  return (
    <footer className={styles.footer}>
      <Text className={styles.copyright}>
        &copy; {new Date().getFullYear()} Obeo. Powered by{' '}
        <span className={styles.link}>
          <a href="https://www.eclipse.org/sirius/sirius-web.html">Sirius Web</a>
        </span>
      </Text>
    </footer>
  );
};
