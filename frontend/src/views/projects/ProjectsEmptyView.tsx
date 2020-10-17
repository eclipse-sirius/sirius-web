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
import { ActionButton } from 'core/button/Button';
import { Text } from 'core/text/Text';
import React from 'react';
import { View } from 'views/View';
import styles from './ProjectsEmptyView.module.css';

export const ProjectsEmptyView = () => {
  return (
    <View>
      <div className={styles.projectsEmptyView}>
        <div className={styles.emptyContainer}>
          <Text className={styles.title}>There are no projects yet</Text>
          <Text className={styles.subtitle}>Start creating your first project and it will appear here</Text>
          <div className={styles.actions}>
            <ActionButton to="/new/project" label="New" data-testid="create" />
            <ActionButton to="/upload/project" label="Upload" data-testid="upload" />
          </div>
        </div>
      </div>
    </View>
  );
};
