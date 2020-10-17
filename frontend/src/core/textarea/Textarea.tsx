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

import styles from './Textarea.module.css';

const propTypes = {
  name: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  readOnly: PropTypes.bool,
  autoComplete: PropTypes.string,
  rows: PropTypes.number,
  maxLength: PropTypes.number,
  onChange: PropTypes.func,
  onBlur: PropTypes.func,
  onFocus: PropTypes.func,
  onKeyPress: PropTypes.func,
  autoFocus: PropTypes.bool,
  'data-testid': PropTypes.string.isRequired,
};

export const Textarea = ({
  name,
  value,
  placeholder,
  readOnly,
  autoComplete,
  rows,
  maxLength,
  onChange,
  onBlur,
  onFocus,
  onKeyPress,
  autoFocus,
  'data-testid': dataTestId,
}) => {
  return (
    <textarea
      className={styles.textarea}
      name={name}
      value={value}
      placeholder={placeholder}
      autoComplete={autoComplete}
      readOnly={readOnly}
      rows={rows}
      maxLength={maxLength}
      onChange={onChange}
      onBlur={onBlur}
      onFocus={onFocus}
      onKeyPress={onKeyPress}
      autoFocus={autoFocus}
      data-testid={dataTestId}
    />
  );
};
Textarea.propTypes = propTypes;
