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
import { useAuth } from 'auth/useAuth';
import PropTypes from 'prop-types';
import React from 'react';
import { subscriberPropType } from './propTypes';
import { DARK, LARGE, LIGHT, MEDIUM, SMALL, Subscriber } from './Subscriber';
import styles from './Subscribers.module.css';

const propTypes = {
  subscribers: PropTypes.arrayOf(subscriberPropType).isRequired,
  size: PropTypes.oneOf([SMALL, MEDIUM, LARGE]).isRequired,
  kind: PropTypes.oneOf([DARK, LIGHT]).isRequired,
  limit: PropTypes.number.isRequired,
};
const defaultProps = {
  subscribers: [],
  limit: Infinity,
};
export const Subscribers = ({ subscribers, size, kind, limit }) => {
  const { username } = useAuth() as any;

  const subscribersToConsider = subscribers.filter((subscriber) => subscriber.username !== username);
  let subscribersDisplayed = subscribersToConsider;
  if (subscribers.length > limit) {
    subscribersDisplayed = [];
    for (let index = 0; index < Math.min(subscribers.length, limit - 1); index++) {
      if (index < subscribers.length) {
        subscribersDisplayed.push(subscribers[index]);
      }
    }
    const remainingSubscribers = subscribers.length - subscribersDisplayed.length;
    subscribersDisplayed.push({ username: `${remainingSubscribers} additional user(s)` });
  }

  return (
    <div className={styles.subscribers}>
      {subscribersDisplayed
        .filter((subscriber) => subscriber.username !== username)
        .map((subscriber) => (
          <Subscriber subscriber={subscriber} size={size} kind={kind} key={subscriber.username} />
        ))}
    </div>
  );
};
Subscribers.propTypes = propTypes;
Subscribers.defaultProps = defaultProps;
