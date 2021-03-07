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
import { DARK, MEDIUM } from 'core/subscriber/Subscriber';
import { Subscribers } from 'core/subscriber/Subscribers';
import { Text } from 'core/text/Text';
import { Page } from 'properties/page/Page';
import { PageList } from 'properties/pagelist/PageList';
import React from 'react';
import styles from './Properties.module.css';

export const Properties = ({ projectId, form, subscribers, widgetSubscriptions }) => {
  const { id, label, pages } = form;
  const currentPage = pages[0];

  let content;
  if (pages.length > 1) {
    content = (
      <div className={styles.container}>
        <PageList pages={pages} />
        <Page projectId={projectId} formId={id} page={currentPage} widgetSubscriptions={widgetSubscriptions} />
      </div>
    );
  } else {
    content = <Page projectId={projectId} formId={id} page={currentPage} widgetSubscriptions={widgetSubscriptions} />;
  }
  return (
    <div data-testid="properties" className={styles.properties}>
      <div className={styles.header} data-testid="header">
        <Text className={styles.label}>{label}</Text>
        <Subscribers subscribers={subscribers} size={MEDIUM} kind={DARK} limit={3} />
      </div>
      {content}
    </div>
  );
};
