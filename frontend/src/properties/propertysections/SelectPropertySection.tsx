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
import { useMutation } from 'common/GraphQLHooks';
import { Select } from 'core/select/Select';
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import { editSelectMutation } from './mutations';
import styles from './PropertySection.module.css';

const propTypes = {
  label: PropTypes.string.isRequired,
  value: PropTypes.string,
  options: PropTypes.array.isRequired,
};

export const SelectPropertySection = ({ projectId, formId, widgetId, label, value, options }) => {
  const optionsWithEmptySelection = [{ id: '', label: '' }].concat(options);

  const [editSelect] = useMutation(editSelectMutation, {}, 'editSelect');
  const onChange = async (event) => {
    const newValue = event.target.value;
    const variables = {
      input: {
        projectId,
        representationId: formId,
        selectId: widgetId,
        newValue,
      },
    };
    await editSelect(variables);
  };

  return (
    <>
      <Text className={styles.label}>{label}</Text>
      <Select name={label} options={optionsWithEmptySelection} value={value} onChange={onChange} data-testid={label} />
    </>
  );
};
SelectPropertySection.propTypes = propTypes;
