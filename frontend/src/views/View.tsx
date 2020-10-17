import { useBranding } from 'common/BrandingContext';
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
//import { Navbar } from 'navbar/Navbar';
import { Navbar } from 'navbar/Navbar';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './View.module.css';

const propTypes = {
  children: PropTypes.node,
  condensed: PropTypes.bool,
};

export const View = ({ children, condensed }) => {
  const { footer } = useBranding();
  let containerClassName = styles.container;
  if (condensed) {
    containerClassName = `${containerClassName} ${styles.condensed}`;
  }
  return (
    <div className={styles.view}>
      <Navbar />
      <div className={containerClassName}>
        <div className={styles.content}>{children}</div>
      </div>
      {footer}
    </div>
  );
};
View.propTypes = propTypes;
