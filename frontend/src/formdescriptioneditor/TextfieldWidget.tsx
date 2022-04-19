/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import TextField from '@material-ui/core/TextField';
import { WidgetProps } from 'formdescriptioneditor/WidgetEntry.types';
import React from 'react';

export const TextfieldWidget = ({ widget }: WidgetProps) => {
  return (
    <TextField
      data-testid={widget.label}
      label={widget.label}
      InputProps={{
        readOnly: true,
      }}
      value="Lorem ipsum dolor sit amet, consectetur adipiscing elit"
    />
  );
};
