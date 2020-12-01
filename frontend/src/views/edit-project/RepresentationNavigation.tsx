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
import { Exit } from 'icons';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './RepresentationNavigation.module.css';

const propTypes = {
  representations: PropTypes.array.isRequired,
  displayedRepresentation: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
};
export const RepresentationNavigation = ({
  representations,
  displayedRepresentation,
  setSelection,
  closeRepresentation,
}) => {
  return (
    <ul className={styles.representationNavigation}>
      {representations.map((representation) => {
        let labelClassName = styles.label;
        const isSelected = representation.id === displayedRepresentation?.id;
        if (isSelected) {
          labelClassName = `${labelClassName} ${styles.selected}`;
        }
        const { id, label, kind } = representation;
        return (
          <li
            key={representation.id}
            className={styles.item}
            data-testid={`representation-tab-${label}`}
            data-testselected={isSelected}>
            <div onClick={() => setSelection({ id, label, kind })}>
              <Text className={labelClassName}>{label}</Text>
            </div>

            <Exit className={styles.exitIcon} title="Exit" onClick={() => closeRepresentation(representation.id)} />
          </li>
        );
      })}
    </ul>
  );
};
RepresentationNavigation.propTypes = propTypes;
