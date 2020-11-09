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
import { NoIcon } from 'icons';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './List.module.css';
import { httpOrigin } from 'common/URL';

const propTypes = {
  items: PropTypes.array.isRequired,
  disabled: PropTypes.bool,
};

const defaultProps = {
  items: [],
};

export const List = ({ items, disabled }) => {
  const content = items.map((item) => (
    <li className={styles.item} key={item.id}>
      {item.imageURL ? (
        <img height="16" width="16" alt={item.kind} src={httpOrigin + item.imageURL}></img>
      ) : (
        <NoIcon title={item.kind} />
      )}
      <Text className={styles.label}>{item.label}</Text>
    </li>
  ));

  let className = styles.list;
  if (disabled) {
    className = `${className} ${styles.disabled}`;
  }

  return <ul className={className}>{content}</ul>;
};
List.propTypes = propTypes;
List.defaultProps = defaultProps;
