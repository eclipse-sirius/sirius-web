/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { Representation } from 'icons/Representation';
import { RepresentationAreaProps } from 'onboarding/RepresentationsArea.types';
import React from 'react';
import { AreaContainer } from './AreaContainer';
import styles from './RepresentationsArea.module.css';

export const RepresentationsArea = ({ representations, setSelection }: RepresentationAreaProps) => {
  // Representations list
  let representationsButtons =
    representations.length > 0
      ? representations.slice(0, 5).map((representation) => {
          return (
            <LinkButton
              key={representation.id}
              label={representation.label}
              data-testid={representation.id}
              onClick={() =>
                setSelection({ id: representation.id, label: representation.label, kind: representation.kind })
              }>
              <Representation title={representation.label} className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // More select
  const moreName = 'moreRepresentations';
  const moreLabel = 'More Representations...';
  let moreSelect =
    representations.length > 5 ? (
      <Select
        onChange={(event) => {
          const representation = representations.find((candidate) => candidate.id === event.target.value);
          setSelection({ id: representation.id, label: representation.label, kind: representation.kind });
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, representations.slice(5)].flat()}
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
