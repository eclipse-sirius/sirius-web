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

import styles from './Form.module.css';

const propTypes = {
  children: PropTypes.node.isRequired,
  onSubmit: PropTypes.func.isRequired,
  encType: PropTypes.string,
};
export const Form = ({ children, onSubmit, encType }) => {
  const props = {
    className: styles.form,
    onSubmit,
    encType: undefined,
  };
  if (encType) {
    props.encType = encType;
  }
  return <form {...props}>{children}</form>;
};
Form.propTypes = propTypes;
