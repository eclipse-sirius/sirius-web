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
import { Group } from 'properties/group/Group';
import React from 'react';
import styles from './Page.module.css';

export const Page = ({ projectId, formId, page, widgetSubscriptions }) => {
  return (
    <div className={styles.page}>
      {page.groups.map((group) => {
        return (
          <Group
            projectId={projectId}
            formId={formId}
            group={group}
            widgetSubscriptions={widgetSubscriptions}
            key={group.id}
          />
        );
      })}
    </div>
  );
};
