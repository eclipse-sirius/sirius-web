/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useEffect, useState } from 'react';
import { TextFieldAppearanceProperty } from '../property-component/TextFieldAppearanceProperty';
import { AppearanceNumberTextfieldProps, AppearanceNumberTextfieldState } from './AppearanceNumberTextfield.types';

export const AppearanceNumberTextfield = ({
  icon,
  label,
  initialValue,
  disabled,
  onEdit,
  onReset,
}: AppearanceNumberTextfieldProps) => {
  const [state, setState] = useState<AppearanceNumberTextfieldState>({
    value: initialValue,
    focused: false,
  });

  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      value: prevState.focused ? prevState.value : initialValue,
    }));
  }, [initialValue]);

  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = Number(event.target.value);
    if (!isNaN(newValue) && isFinite(newValue)) {
      setState((prevState) => ({
        ...prevState,
        value: newValue,
      }));
    }
  };

  const handleOnKeyDownEdit = (event) => {
    if (event.code === 'Enter') {
      onEdit(state.value);
    }
  };

  const handleOnFocus = (_event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      focused: true,
    }));
  };

  const handleOnBlur = () => {
    if (state.value != initialValue) {
      onEdit(state.value);
    }
    setState((prevState) => ({
      ...prevState,
      focused: false,
    }));
  };

  return (
    <TextFieldAppearanceProperty
      icon={icon}
      label={label}
      value={state.value}
      onChange={handleOnChange}
      onKeyDown={handleOnKeyDownEdit}
      onBlur={handleOnBlur}
      onFocus={handleOnFocus}
      onReset={onReset}
      disabled={disabled}
    />
  );
};
