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
import React, { useEffect, useRef } from 'react';

export const TextfieldWidget = ({ widget, selection }: WidgetProps) => {
  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
    }
  }, [selection, widget]);

  return (
    <TextField
      data-testid={widget.label}
      label={widget.label}
      inputRef={ref}
      InputProps={{
        readOnly: true,
      }}
      value="Lorem ipsum dolor sit amet, consectetur adipiscing elit"
    />
  );
};
