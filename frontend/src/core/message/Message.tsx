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
import { Danger, Success, Warning } from 'icons';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './Message.module.css';

const propTypes = {
  severity: PropTypes.string.isRequired,
  content: PropTypes.string.isRequired,
  'data-testid': PropTypes.string.isRequired,
};

export const SEVERITY_DEFAULT = 'default';
export const SEVERITY_SUCCESS = 'success';
export const SEVERITY_WARNING = 'warning';
export const SEVERITY_DANGER = 'danger';

export const Message = ({ severity, content, 'data-testid': dataTestId }) => {
  let textClassName = styles.defaultText;
  let image = null;
  if (severity === SEVERITY_SUCCESS) {
    textClassName = styles.successText;
    image = <Success width="16" height="16" className={styles.successIcon} title="Success" />;
  } else if (severity === SEVERITY_WARNING) {
    textClassName = styles.warningText;
    image = <Warning width="16" height="16" className={styles.warningIcon} title="Warning" />;
  } else if (severity === SEVERITY_DANGER) {
    textClassName = styles.dangerText;
    image = <Danger title="Danger" width="16" height="16" className={styles.dangerIcon} />;
  }
  return (
    <div className={styles.message} data-testseverity={severity} data-testid={dataTestId}>
      {image}
      <Text className={textClassName}>{content}</Text>
    </div>
  );
};

Message.propTypes = propTypes;
