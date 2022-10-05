/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { Danger } from 'icons';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './Banner.module.css';

const propTypes = {
  content: PropTypes.string.isRequired,
  'data-testid': PropTypes.string.isRequired,
};

export const Banner = ({ content, 'data-testid': dataTestId }) => {
  return (
    <div className={styles.banner} title={content} data-testid={dataTestId}>
      <Danger title="Danger" width="16" height="16" className={styles.errorIcon} />
      <Text className={styles.errorText}>{content}</Text>
    </div>
  );
};

Banner.propTypes = propTypes;
