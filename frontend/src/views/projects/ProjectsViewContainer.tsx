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
import PropTypes from 'prop-types';
import React from 'react';
import styles from './ProjectsViewContainer.module.css';

const propTypes = {
  children: PropTypes.node.isRequired,
};

export const ProjectsViewContainer = ({ children }) => {
  return (
    <div className={styles.projectsViewContainer}>
      <div className={styles.header}>
        <Text className={styles.headline}>Projects</Text>
        <div className={styles.actions}>
          <ActionButton to="/new/project" label="New" data-testid="create" />
          <ActionButton to="/upload/project" label="Upload" data-testid="upload" />
        </div>
      </div>
      <div className={styles.content}>{children}</div>
    </div>
  );
};
ProjectsViewContainer.propTypes = propTypes;
