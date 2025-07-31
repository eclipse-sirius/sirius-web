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
import { Color } from '../Color';
import { TextFieldAppearanceProperty } from '../property-component/TextFieldAppearanceProperty';
import { AppearanceColorPickerProps, AppearanceColorPickerState } from './AppearanceColorPicker.types';

export const AppearanceColorPicker = ({
  label,
  initialValue,
  isDisabled,
  onEdit,
  onReset,
}: AppearanceColorPickerProps) => {
  const [state, setState] = useState<AppearanceColorPickerState>({
    value: initialValue,
  });

  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      value: event.target.value,
    }));
  };

  const handleOnKeyDownEdit = (event) => {
    if (event.code === 'Enter') {
      onEdit(state.value);
    }
  };

  return (
    <TextFieldAppearanceProperty
      icon={<Color value={state.value} />}
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
