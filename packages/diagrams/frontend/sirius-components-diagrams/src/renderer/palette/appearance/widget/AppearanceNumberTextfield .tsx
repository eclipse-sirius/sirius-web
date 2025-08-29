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
import { useState } from 'react';
import { TextFieldAppearanceProperty } from '../property-component/TextFieldAppearanceProperty';
import { AppearanceNumberTextfieldProps, AppearanceNumberTextfieldState } from './AppearanceNumberTextfield.types';

export const AppearanceNumberTextfield = ({
  icon,
  label,
  initialValue,
  isDisabled,
  onEdit,
  onReset,
}: AppearanceNumberTextfieldProps) => {
  const [state, setState] = useState<AppearanceNumberTextfieldState>({
    value: initialValue,
  });

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

  return (
    <TextFieldAppearanceProperty
      icon={icon}
      label={label}
      value={state.value}
      onChange={handleOnChange}
      onKeyDown={handleOnKeyDownEdit}
      onBlur={() => onEdit(state.value)}
      onReset={onReset}
      isDisabled={isDisabled}
    />
  );
};
