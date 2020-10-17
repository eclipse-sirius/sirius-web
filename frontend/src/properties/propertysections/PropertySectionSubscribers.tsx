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

import { SMALL, DARK } from 'core/subscriber/Subscriber';
import { Subscribers } from 'core/subscriber/Subscribers';

import styles from './PropertySectionSubscribers.module.css';

export const PropertySectionSubscribers = ({ children, subscribers }) => {
  return (
    <div className={styles.container}>
      {children}
      <Subscribers subscribers={subscribers} size={SMALL} kind={DARK} limit={1} />
    </div>
  );
};
