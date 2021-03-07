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
import { MemoryRouter } from 'react-router-dom';
import { Border } from 'stories/common/Border';
import { View } from 'views/View';

import styles from './ViewStory.module.css';

export const ViewStory = () => {
  return (
    <div className={styles.viewStory}>
      <div className={styles.regular}>
        Regular view
        <Border>
          <MemoryRouter>
            <View>
              <div className={styles.placeholder}></div>
            </View>
          </MemoryRouter>
        </Border>
      </div>
      <div className={styles.condensed}>
        Condensed view
        <Border>
          <MemoryRouter>
            <View condensed>
              <div className={styles.placeholder}></div>
            </View>
          </MemoryRouter>
        </Border>
      </div>
    </div>
  );
};
