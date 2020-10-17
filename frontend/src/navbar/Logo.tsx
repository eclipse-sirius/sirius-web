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
import PropTypes from 'prop-types';
import { useBranding } from 'common/BrandingContext';
import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Logo.module.css';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const Logo = ({ title }) => {
  const { icon } = useBranding();
  const iconWithTitle = React.cloneElement(icon, { title, 'data-testid': 'logo' });

  return (
    <div className={styles.logo}>
      <Link to="/" data-testid="home">
        {iconWithTitle}
      </Link>
    </div>
  );
};
Logo.propTypes = propTypes;
