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
import { LinkButton } from 'core/linkbutton/LinkButton';
import { Select } from 'core/select/Select';
import { Representation } from 'icons';
import PropTypes from 'prop-types';
import React from 'react';
import { AreaContainer } from './AreaContainer';
import styles from './RepresentationsArea.module.css';

const propTypes = {
  maxDisplay: PropTypes.number.isRequired,
  representations: PropTypes.array.isRequired,
  setSelections: PropTypes.func.isRequired,
};

export const RepresentationsArea = ({ representations, maxDisplay, setSelections }) => {
  // Representations list
  let representationsButtons =
    representations.length > 0
      ? representations.slice(0, maxDisplay).map((representation) => {
          return (
            <LinkButton
              key={representation.id}
              label={representation.label}
              data-testid={representation.id}
              onClick={() =>
                setSelections([{ id: representation.id, label: representation.label, kind: representation.__typename }])
              }>
              <Representation title="" className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // More select
  const moreName = 'moreRepresentations';
  const moreLabel = 'More Representations...';
  let moreSelect =
    representations.length > maxDisplay ? (
      <Select
        onChange={(event) => {
          const representation = representations.find((candidate) => candidate.id === event.target.value);
          setSelections([{ id: representation.id, label: representation.label, kind: representation.__typename }]);
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, representations.slice(maxDisplay)].flat()}
        data-testid={moreName}
      />
    ) : null;

  return (
    <AreaContainer title="Open an existing Representation" subtitle="Select the representation to open">
      {representationsButtons}
      {moreSelect}
    </AreaContainer>
  );
};

RepresentationsArea.propTypes = propTypes;
