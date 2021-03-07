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
import { useMutation } from '@apollo/client';
import { Radio } from 'core/radio/Radio';
import { Text } from 'core/text/Text';
import { Permission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';
import { editRadioMutation } from './mutations';
import styles from './PropertySection.module.css';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  formId: PropTypes.string.isRequired,
  widgetId: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  options: PropTypes.array.isRequired,
};

export const RadioPropertySection = ({ projectId, formId, widgetId, label, options }) => {
  const [editRadio] = useMutation(editRadioMutation);

  const onChange = async (event) => {
    const newValue = event.target.value;
    const variables = {
      input: {
        projectId,
        representationId: formId,
        radioId: widgetId,
        newValue,
      },
    };
    await editRadio({ variables });
  };

  return (
    <>
      <Text className={styles.label}>{label}</Text>
      <Permission requiredAccessLevel="EDIT">
        <Radio name={label} options={options} onChange={onChange} data-testid={label} />
      </Permission>
    </>
  );
};
RadioPropertySection.propTypes = propTypes;
