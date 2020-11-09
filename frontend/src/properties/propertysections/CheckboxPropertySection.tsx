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
import { Checkbox } from 'core/checkbox/Checkbox';
import { Text } from 'core/text/Text';
import { Permission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';
import { editCheckboxMutation } from './mutations';
import styles from './PropertySection.module.css';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  formId: PropTypes.string.isRequired,
  widgetId: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  value: PropTypes.bool.isRequired,
};

export const CheckboxPropertySection = ({ projectId, formId, widgetId, label, value }) => {
  const [editCheckbox] = useMutation(editCheckboxMutation, {}, 'editCheckbox');
  const onChange = async (event) => {
    const newValue = event.target.checked;
    const variables = {
      input: {
        projectId,
        representationId: formId,
        checkboxId: widgetId,
        newValue,
      },
    };
    await editCheckbox(variables);
  };

  return (
    <>
      <Text className={styles.label}>{label}</Text>
      <Permission requiredAccessLevel="EDIT">
        <Checkbox name={label} label="" checked={value} onChange={onChange} data-testid={label} />
      </Permission>
    </>
  );
};
CheckboxPropertySection.propTypes = propTypes;
