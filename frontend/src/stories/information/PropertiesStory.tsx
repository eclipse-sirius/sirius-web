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

export const PropertiesStory = () => {
  return (
    <div className={styles.documentation}>
      <Text className={styles.title}>Properties</Text>
      <Text className={styles.body}>
        All the properties of our concepts. Only the properties relevant for the end users are considered. This document
        does not describe how the concepts and their properties should be stored or manipulated by the server.
      </Text>

      <Text className={styles.subtitle}>Common rules</Text>
      <Text className={styles.body}>
        All the identifiers have to be unique and stable over time. They should also be opaque and thus not leak any
        information regarding the concepts manipulated (URI fragments, nsURIs, etc). All the main concepts should be
        linked to their creator and their creation date stored too. Those properties may be used to find or filter those
        entities while searching them. Entities should be identified by their identifier at all time. Since entities are
        identified by their identifier, most of the time a name should not be unique on the whole server. Use label if
        the string representation of an entity is computed and name if it isn't. All input must be trimmed. Everything
        supported in unicode must be allowed in text input (emojis in project name for example).
      </Text>

      <Text className={styles.subtitle}>Project</Text>
      <Text className={styles.details}>Properties</Text>
      <ul className={styles.list}>
        <li>id</li>
        <li>name</li>
      </ul>
      <Text className={styles.body}>
        The name of a project is not supposed to be unique. Projects can easily be renamed over time.
      </Text>
      <Text className={styles.details}>Validation</Text>
      <ul className={styles.list}>
        <li>The name must contain between 3 and 20 characters</li>
      </ul>

      <Text className={styles.subtitle}>Document</Text>
      <Text className={styles.details}>Properties</Text>
      <ul className={styles.list}>
        <li>id</li>
        <li>name</li>
        <li>containing project</li>
        <li>contains object</li>
      </ul>
      <Text className={styles.details}>Validation</Text>
      <ul className={styles.list}>
        <li>The name must contain between 3 and 20 characters</li>
      </ul>

      <Text className={styles.subtitle}>Object</Text>
      <Text className={styles.details}>Properties</Text>
      <ul className={styles.list}>
        <li>id</li>
        <li>label</li>
        <li>properties</li>
      </ul>
      <Text className={styles.body}>An object can contain various properties which will be determined at runtime.</Text>

      <Text className={styles.subtitle}>Representation</Text>
      <Text className={styles.details}>Properties</Text>
      <ul className={styles.list}>
        <li>id</li>
        <li>label</li>
        <li>located in a document</li>
        <li>target object</li>
      </ul>
      <Text className={styles.body}>
        Representations have a label instead of a name since the label is computed and while it can be changed, the new
        value sent by the end users may be transformed in order to produce the new label. On the other hand, modifying a
        name is straightforward, there are no transformations involved.
      </Text>
    </div>
  );
};
