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
import { M, S, Spacing } from 'core/spacing/Spacing';
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './PageList.module.css';

const propTypes = {
  pages: PropTypes.array.isRequired,
};

const PageEntry = ({ label }) => {
  return (
    <div>
      <Spacing right={M} left={M}>
        <Text className={styles.label}>{label}</Text>
      </Spacing>
    </div>
  );
};

export const PageList = ({ pages }) => {
  return (
    <div className={styles.pagelist}>
      <Spacing top={S} bottom={S}>
        {pages.map((page) => {
          return <PageEntry key={page.id} label={page.label} />;
        })}
      </Spacing>
    </div>
  );
};
PageList.propTypes = propTypes;
