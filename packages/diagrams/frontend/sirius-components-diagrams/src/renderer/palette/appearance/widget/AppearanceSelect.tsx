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

import { SelectChangeEvent } from '@mui/material/Select';
import { useState } from 'react';
import { SelectAppearanceProperty } from '../property-component/SelectAppearanceProperty';
import { AppearanceSelectProps, AppearanceSelectState } from './AppearanceSelect.types';

export const AppearanceSelect = ({
  icon,
  label,
  initialValue,
  options,
  isDisabled,
  onEdit,
  onReset,
}: AppearanceSelectProps) => {
  const [state, setState] = useState<AppearanceSelectState>({
    value: initialValue,
  });

  const handleOnChange = (event: SelectChangeEvent<string>) => {
    const newValue = event.target.value;
    setState((prevState) => ({
      ...prevState,
      value: newValue,
    }));
    onEdit(newValue);
  };

  return (
    <SelectAppearanceProperty
      icon={icon}
      label={label}
      value={state.value}
      onChange={handleOnChange}
      options={options}
      onReset={onReset}
      isDisabled={isDisabled}
    />
  );
};
