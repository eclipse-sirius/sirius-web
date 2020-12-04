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

import { Text } from 'core/text/Text';

import styles from '../common/Text.module.css';

export const OverviewStory = () => {
  return (
    <div className={styles.documentation}>
      <Text className={styles.title}>Overview</Text>
      <Text className={styles.body}>
        Our URLs will be listed in this document along with their goals. Our URLs should not leak any kind of
        information, as such someone not authorized to use our product should not be able to find out any kind of
        information from the simple view of one of our URL.
      </Text>
      <Text className={styles.body}>
        URLs used to create new entities should use the segment /new/ to separate where the entity will be created from
        the kind of entity to create. Our URLs should not be needlessly complicated but we do not expect end-users to
        remember them especially since we will heavily rely on opaque identifiers which will be complex to remember and
        discover on purpose.
      </Text>

      <Text className={styles.subtitle}>Homepage</Text>
      <Text className={styles.details}>URL: localhost:8080</Text>
      <Text className={styles.body}>
        Our homepage where non identified end users can learn more about our product. Identified users should be able to
        see their dashboards. Once an end user has been identified, various concepts will be visible such as their own
        projects.
      </Text>

      <Text className={styles.subtitle}>Project list</Text>
      <Text className={styles.details}>URL: localhost:8080/projects</Text>
      <Text className={styles.body}>Used to see all the available projects.</Text>

      <Text className={styles.subtitle}>Project details</Text>
      <Text className={styles.details}>URL: localhost:8080/projects/:projectId</Text>
      <Text className={styles.body}>The main page of a project where some project metadata can be seen.</Text>

      <Text className={styles.subtitle}>Project edition</Text>
      <Text className={styles.details}>URL: localhost:8080/projects/:projectId/edit</Text>
      <Text className={styles.body}>
        The page where a project is edited. It provides support to view and edit the business data of the project.
      </Text>

      <Text className={styles.subtitle}>Project creation</Text>
      <Text className={styles.details}>URL: localhost:8080/new/project</Text>
      <Text className={styles.body}>
        Used to create a new project. Will redirect the user to the project details page after the creation.
      </Text>

      <Text className={styles.subtitle}>Document creation</Text>
      <Text className={styles.details}>URL: localhost:8080/projects/:projectId/new/document</Text>
      <Text className={styles.body}>
        Used to create a new model inside a project. Will be removed soon in favor of an action in the page used to edit
        a project.
      </Text>
    </div>
  );
};
