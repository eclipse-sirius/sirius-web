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

import styles from './Textfield.module.css';

const propTypes = {
  kind: PropTypes.string,
  type: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  autoComplete: PropTypes.string,
  onFocus: PropTypes.func,
  onChange: PropTypes.func,
  onBlur: PropTypes.func,
  onKeyPress: PropTypes.func,
  onKeyDown: PropTypes.func,
  readOnly: PropTypes.bool,
  autoFocus: PropTypes.bool,
  'data-testid': PropTypes.string.isRequired,
};
const defaultProps = {
  type: 'text',
};

export const Textfield = ({
  kind,
  type,
  name,
  value,
  placeholder,
  autoComplete,
  onFocus,
  onChange,
  onBlur,
  onKeyPress,
  onKeyDown,
  readOnly,
  autoFocus,
  'data-testid': dataTestId,
}) => {
  let className;
  if (kind === 'small') {
    className = styles.small;
  } else {
    className = styles.textfield;
  }
  return (
    <input
      className={className}
      type={type}
      name={name}
      placeholder={placeholder}
      autoComplete={autoComplete}
      value={value}
      onChange={onChange}
      onBlur={onBlur}
      onFocus={onFocus}
      onKeyPress={onKeyPress}
      onKeyDown={onKeyDown}
      readOnly={readOnly}
      autoFocus={autoFocus}
      data-testid={dataTestId}
    />
  );
};
Textfield.propTypes = propTypes;
Textfield.defaultProps = defaultProps;
