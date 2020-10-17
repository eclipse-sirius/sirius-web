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
import PropTypes from 'prop-types';
import React from 'react';
import { View } from 'views/View';
import styles from './ProjectsErrorView.module.css';
import { ProjectsViewContainer } from './ProjectsViewContainer';

const propTypes = {
  message: PropTypes.string.isRequired,
};

export const ProjectsErrorView = ({ message }) => {
  return (
    <View>
      <ProjectsViewContainer>
        <div className={styles.errorMessage}>
          <Text className={styles.message}>{message}</Text>
        </div>
      </ProjectsViewContainer>
    </View>
  );
};
ProjectsErrorView.propTypes = propTypes;
