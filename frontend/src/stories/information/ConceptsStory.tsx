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

export const ConceptsStory = () => {
  return (
    <div className={styles.documentation}>
      <Text className={styles.title}>Concepts</Text>
      <Text className={styles.body}>
        All the information related to the various concepts manipulated by the application.
      </Text>
      <Text className={styles.subtitle}>User</Text>
      <Text className={styles.body}>Individual using our application.</Text>
      <Text className={styles.subtitle}>Project</Text>
      <Text className={styles.body}>Used as a namespace of the work to be done by the user.</Text>
      <Text className={styles.subtitle}>Document</Text>
      <Text className={styles.body}>
        Unit of organisation of the work. Can be used to help organise the various parts of the work. Used to help users
        move from legacy applications to the web. Contains the business data of the end users. The name document by
        itself may not be relevant to the business of the end users. As such customization of this name by the specifier
        may be required in the future. Try to refrain from using the name of this internal concept as much as possible
        in the user interface for now.
      </Text>
      <Text className={styles.subtitle}>Object</Text>
      <Text className={styles.body}>
        Atom of data for the end user. We cannot really make any hypothesis on its structure.
      </Text>
      <Text className={styles.subtitle}>Representation</Text>
      <Text className={styles.body}>
        Projection of the raw data contained in the documents and objects into a graphical representation. Mostly used
        to describe diagrams, trees, forms. Core business feature.
      </Text>
    </div>
  );
};
