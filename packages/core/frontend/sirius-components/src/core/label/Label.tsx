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
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './Label.module.css';

const propTypes = {
  value: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};
export const Label = ({ value, children }) => {
  return (
    <label className={styles.label}>
      <Text className={styles.labelText}>{value}</Text>
      {children}
    </label>
  );
};
Label.propTypes = propTypes;
