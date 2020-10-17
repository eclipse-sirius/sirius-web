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
import { Link as ReactLink } from 'react-router-dom';

import styles from './Link.module.css';

const propTypes = {
  children: PropTypes.node.isRequired,
  to: PropTypes.string.isRequired,
  'data-testid': PropTypes.string.isRequired,
};
export const Link = ({ children, to, 'data-testid': dataTestid }) => {
  return (
    <ReactLink className={styles.link} to={to} data-testid={dataTestid}>
      {children}
    </ReactLink>
  );
};
Link.propTypes = propTypes;
