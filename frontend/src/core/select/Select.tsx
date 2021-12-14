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

import styles from './Select.module.css';

const optionPropType = {
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
};

const propTypes = {
  name: PropTypes.string.isRequired,
  value: PropTypes.string,
  options: PropTypes.arrayOf(PropTypes.shape(optionPropType)).isRequired,
  onChange: PropTypes.func,
  disabled: PropTypes.bool,
  autoFocus: PropTypes.bool,
  small: PropTypes.bool,
  'data-testid': PropTypes.string.isRequired,
};

const defaultProps = {
  value: '',
  options: [],
};

export const Select = ({ name, value, options, onChange, disabled, autoFocus, small, 'data-testid': dataTestId }) => {
  let className = styles.select;

  if (disabled) {
    className = `${className} ${styles.disabled}`;
  }
  if (small) {
    className = `${className} ${styles.small}`;
  }

  const opts = options.map((option) => {
    return (
      <option value={option.id} key={option.id}>
        {option.label}
      </option>
    );
  });

  let selectValue = '';
  if (value) {
    selectValue = value;
  }

  return (
    <select
      className={className}
      name={name}
      value={selectValue}
      onChange={onChange}
      disabled={disabled}
      autoFocus={autoFocus}
      data-testid={dataTestId}
    >
      {opts}
    </select>
  );
};
Select.propTypes = propTypes;
Select.defaultProps = defaultProps;
