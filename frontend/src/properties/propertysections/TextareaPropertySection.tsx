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
import { Text } from 'core/text/Text';
import { Textarea } from 'core/textarea/Textarea';
import PropTypes from 'prop-types';
import { PropertySectionSubscribers } from 'properties/propertysections/PropertySectionSubscribers';
import React, { useEffect, useState } from 'react';
import styles from './PropertySection.module.css';
import { editTextfieldMutation, updateWidgetFocusMutation } from './mutations';
import { Permission } from 'project/Permission';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  formId: PropTypes.string.isRequired,
  widgetId: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
};

/**
 * Defines the content of a Textarea property section.
 * As this widget contains multiple lines, the "Tnter" key will add a line delimeter.
 * The content is submitted when the focus is lost.
 */
export const TextareaPropertySection = ({ label, value, readOnly, projectId, formId, widgetId, subscribers }) => {
  const initialState = {
    inputValue: value,
    edited: false,
  };

  const [state, setState] = useState(initialState);
  const { inputValue, edited } = state;

  useEffect(() => {
    setState({ inputValue: value, edited: false });
  }, [value]);

  const onChange = (event) => {
    const newText = event.target.value;
    setState((prevState) => {
      const newState = { ...prevState };
      newState.inputValue = newText;
      newState.edited = true;
      return newState;
    });
  };

  const [editTextfield] = useMutation(editTextfieldMutation);
  const sendEditedValue = async () => {
    if (edited) {
      const variables = {
        input: {
          projectId,
          representationId: formId,
          textfieldId: widgetId,
          newValue: inputValue,
        },
      };
      await editTextfield({ variables });
    }
  };

  const [updateWidgetFocus] = useMutation(updateWidgetFocusMutation);
  const sendUpdateWidgetFocus = async (selected) => {
    const variables = {
      input: {
        projectId,
        representationId: formId,
        widgetId,
        selected,
      },
    };
    await updateWidgetFocus({ variables });
  };

  const onFocus = async () => {
    await sendUpdateWidgetFocus(true);
  };
  const onBlur = async () => {
    await sendUpdateWidgetFocus(false);
    await sendEditedValue();
  };

  return (
    <>
      <PropertySectionSubscribers subscribers={subscribers}>
        <Text className={styles.label}>{label}</Text>
      </PropertySectionSubscribers>
      <Permission requiredAccessLevel="EDIT">
        <Textarea
          name={label}
          placeholder={label}
          value={inputValue}
          rows={4}
          onChange={onChange}
          onBlur={onBlur}
          onFocus={onFocus}
          readOnly={readOnly}
          data-testid={label}
        />
      </Permission>
    </>
  );
};
TextareaPropertySection.propTypes = propTypes;
