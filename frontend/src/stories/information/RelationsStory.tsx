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

export const RelationsStory = () => {
  return (
    <div className={styles.documentation}>
      <Text className={styles.title}>Relations</Text>
      <Text className={styles.body}>Bird's eye view of the relationships between our concepts.</Text>
      <Text className={styles.subtitle}>User</Text>
      <Text className={styles.body}>
        Users are connected to pretty much all the other concepts. It will help us provide meaningful information to our
        end users. As such they are connected to the following concepts:
      </Text>
      <ul className={styles.list}>
        <li>Project#creator</li>
        <li>Document#creator</li>
        <li>Representation#creator</li>
      </ul>

      <Text className={styles.subtitle}>Project</Text>
      <Text className={styles.body}>
        Projects are the main container of the work to be done. They will be connected to their main artifacts. Projects
        are directly connected to representation in order to visualize quickly the content of the project. This shortcut
        to the navigation is useful to let users work more efficiently.
      </Text>
      <ul className={styles.list}>
        <li>Document</li>
        <li>Representation</li>
      </ul>

      <Text className={styles.subtitle}>Document</Text>
      <Text className={styles.body}>
        Documents are the main unit of work but we do not want to promote them as a main concept that the user should
        worry about. As such, they are mainly a passive container of the business data.
      </Text>
      <ul className={styles.list}>
        <li>Object</li>
      </ul>

      <Text className={styles.subtitle}>Object</Text>
      <Text className={styles.body}>Objects are related to each others and contained in a document.</Text>

      <Text className={styles.subtitle}>Representation</Text>
      <Text className={styles.body}>
        Representation are connected in various ways to the business data that they are displaying.
      </Text>
      <ul className={styles.list}>
        <li>Object</li>
      </ul>
    </div>
  );
};
