/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import React from 'react';

import styles from './Text.module.css';

const propTypes = {
  className: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
  'data-testid': PropTypes.string,
};

export const Text = ({ className, children, 'data-testid': dataTestid }) => {
  return (
    <div className={`${styles.text} ${className}`} data-testid={dataTestid}>
      {children}
    </div>
  );
};
Text.propTypes = propTypes;
