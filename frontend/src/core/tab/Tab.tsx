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
import { M, Spacing } from 'core/spacing/Spacing';
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React, { useContext } from 'react';
import styles from './Tab.module.css';

export const ActiveContext = React.createContext(false);

const propTypes = {
  label: PropTypes.string.isRequired,
};
export const Tab = ({ label }) => {
  const active = useContext(ActiveContext);
  let className = styles.tab;
  if (active) {
    className = `${className} ${styles.active}`;
  }
  return (
    <div className={className} data-testid={label}>
      <Spacing right={M} left={M} top={M} bottom={M}>
        <Text className={styles.tabLabel}>{label}</Text>
      </Spacing>
    </div>
  );
};
Tab.propTypes = propTypes;
