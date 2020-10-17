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
import PropTypes from 'prop-types';
import React from 'react';
import { subscriberPropType } from './propTypes';
import styles from './Subscriber.module.css';

export const SMALL = 'small';
export const MEDIUM = 'medium';
export const LARGE = 'large';

export const DARK = 'dark';
export const LIGHT = 'light';

const propTypes = {
  subscriber: subscriberPropType.isRequired,
  size: PropTypes.oneOf([SMALL, MEDIUM, LARGE]).isRequired,
  kind: PropTypes.oneOf([DARK, LIGHT]).isRequired,
};

export const Subscriber = ({ subscriber, size, kind }) => {
  let className = styles.subscriber;

  if (size === SMALL) {
    className = `${className} ${styles.small}`;
  } else if (size === MEDIUM) {
    className = `${className} ${styles.medium}`;
  } else if (size === LARGE) {
    className = `${className} ${styles.large}`;
  }

  if (kind === DARK) {
    className = `${className} ${styles.dark}`;
  } else if (kind === LIGHT) {
    className = `${className} ${styles.light}`;
  }

  let bottomBar = <div className={styles.bottomBar}></div>;
  if (size === SMALL) {
    bottomBar = null;
  }

  return (
    <div className={className}>
      <div className={styles.externalCircle} title={subscriber.username}>
        <div className={styles.internalCircle}>
          <div className={styles.username}>{subscriber.username.charAt(0)}</div>
        </div>
      </div>
      {bottomBar}
    </div>
  );
};
Subscriber.propTypes = propTypes;
