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
import React from 'react';
import PropTypes from 'prop-types';

import styles from './LinkButton.module.css';

const propTypes = {
  children: PropTypes.node,
  label: PropTypes.string.isRequired,
  onClick: PropTypes.func,
  'data-testid': PropTypes.string.isRequired,
};

export const LinkButton = ({ children, label, onClick, 'data-testid': dataTestid }) => {
  return (
    <div className={styles.linkButton}>
      {children}
      <button className={styles.text} onClick={onClick} data-testid={dataTestid}>
        {label}
      </button>
    </div>
  );
};
LinkButton.propTypes = propTypes;
