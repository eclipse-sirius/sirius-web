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

import styles from './Radio.module.css';

const propTypes = {
  name: PropTypes.string.isRequired,
  options: PropTypes.array.isRequired,
  vertical: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  'data-testid': PropTypes.string.isRequired,
};

export const Radio = ({ name, options, vertical, onChange, disabled, 'data-testid': dataTestId }) => {
  const opts = options.map((option) => {
    let radioClass = `${styles.radio}`;
    if (option.selected) {
      radioClass = `${radioClass} ${styles.selected}`;
    }
    if (disabled) {
      radioClass = `${radioClass} ${styles.disabled}`;
    }
    return (
      <label className={radioClass} key={option.id}>
        <input
          type="radio"
          name={name}
          value={option.id}
          checked={option.selected}
          disabled={disabled}
          onChange={onChange}
          data-testid={option.id}
        />
        <span>{option.label}</span>
      </label>
    );
  });
  let className = styles.radiogroup;
  if (vertical) {
    className = `${className} ${styles.vertical}`;
  }
  return (
    <div className={className} data-testid={dataTestId}>
      {opts}
    </div>
  );
};
Radio.propTypes = propTypes;
