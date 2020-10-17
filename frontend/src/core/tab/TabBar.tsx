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
import React, { useState } from 'react';

import { ActiveContext } from './Tab';

import styles from './TabBar.module.css';

export const TabBar = ({ children }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const headers = children.map((child, index) => {
    const active = activeIndex === index;
    return (
      <li className={styles.tab} onClick={() => setActiveIndex(index)} key={index}>
        <ActiveContext.Provider value={active}>{child}</ActiveContext.Provider>
      </li>
    );
  });
  const content = children[activeIndex].props.children;
  return (
    <div className={styles.tabBar}>
      <nav>
        <ul className={styles.tabs}>{headers}</ul>
        <hr className={styles.border} />
      </nav>
      {content}
    </div>
  );
};
