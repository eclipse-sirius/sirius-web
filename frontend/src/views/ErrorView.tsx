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
import { Link } from 'core/link/Link';
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './ErrorView.module.css';
import { View } from './View';

const propTypes = {
  message: PropTypes.string.isRequired,
};
export const ErrorView = ({ message }) => {
  return (
    <View>
      <div className={styles.errorMessage}>
        <Text className={styles.message}>{message}</Text>

        <Link to="/projects" data-testid="back">
          Back to all projects
        </Link>
      </div>
    </View>
  );
};
ErrorView.propTypes = propTypes;
