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
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useEffect, useRef, useState } from 'react';
import {
  getTextDecorationLineValue,
  readBooleanStyleProperty,
  readNumericStyleProperty,
  readStyleProperty,
} from './styleProperties';
import { WidgetProps } from './WidgetEntry.types';

export interface StyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

const useStyle = makeStyles<Theme, StyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : 'inherit'),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const SelectWidget = ({ widget, selection }: WidgetProps) => {
  const props: StyleProps = {
    backgroundColor: readStyleProperty(widget, 'backgroundColor'),
    foregroundColor: readStyleProperty(widget, 'foregroundColor'),
    fontSize: readNumericStyleProperty(widget, 'fontSize'),
    italic: readBooleanStyleProperty(widget, 'italic'),
    bold: readBooleanStyleProperty(widget, 'bold'),
    underline: readBooleanStyleProperty(widget, 'underline'),
    strikeThrough: readBooleanStyleProperty(widget, 'strikeThrough'),
  };
  const classes = useStyle(props);

  const [selected, setSelected] = useState<boolean>(false);

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  return (
    <div>
      <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
        {widget.label}
      </Typography>
      <Select
        data-testid={widget.label}
        label={widget.label}
        fullWidth
        value="value1"
        inputRef={ref}
        inputProps={{ className: classes.style }}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}>
        <MenuItem value="" classes={{ root: classes.style }}>
          <em>None</em>
        </MenuItem>
        <MenuItem value="value1" classes={{ root: classes.style }}>
          Value 1
        </MenuItem>
        <MenuItem value="value2" classes={{ root: classes.style }}>
          Value 2
        </MenuItem>
        <MenuItem value="value3" classes={{ root: classes.style }}>
          Value 3
        </MenuItem>
      </Select>
    </div>
  );
};
