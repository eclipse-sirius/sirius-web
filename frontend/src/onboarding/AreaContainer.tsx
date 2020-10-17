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
import { Banner } from 'core/banner/Banner';
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './AreaContainer.module.css';

const propTypes = {
  title: PropTypes.string.isRequired,
  subtitle: PropTypes.string.isRequired,
  banner: PropTypes.node,
  children: PropTypes.node,
};

export const AreaContainer = ({ title, subtitle, banner, children }) => {
  let bannerContent = banner ? <Banner data-testid="banner" content={banner} /> : null;
  return (
    <div className={styles.container}>
      <div className={styles.titleContainer}>
        <Text className={styles.title}>{title}</Text>
        <Text className={styles.subtitle}>{subtitle}</Text>
      </div>
      <div className={styles.content}>{children}</div>
      <div className={styles.bannerArea}>{bannerContent}</div>
    </div>
  );
};
AreaContainer.propTypes = propTypes;
