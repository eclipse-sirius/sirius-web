/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { getCSSColor, useSelection } from '@eclipse-sirius/sirius-components-core';
import { DateTimeStyleProps } from '@eclipse-sirius/sirius-components-forms';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { DataTimeWidgetState } from './DateTimeWidget.types';
import { DateTimeWidgetProps } from './WidgetEntry.types';

const useDataTimeWidgetStyles = makeStyles<Theme, DateTimeStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? getCSSColor(backgroundColor, theme) : null),
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
  },
  textfield: {
    marginTop: theme.spacing(0.5),
    marginBottom: theme.spacing(0.5),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const DateTimeWidget = ({ widget }: DateTimeWidgetProps) => {
  const props: DateTimeStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
  };
  const classes = useDataTimeWidgetStyles(props);

  const [state, setState] = useState<DataTimeWidgetState>({
    selected: false,
  });
  const { selection } = useSelection();
  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setState((prevState) => ({ ...prevState, selected: true }));
    } else {
      setState((prevState) => ({ ...prevState, selected: false }));
    }
  }, [selection, widget]);

  const { value, type } = getValueAndType(widget.type);

  return (
    <div>
      <div>
        <Typography variant="subtitle2" className={state.selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <TextField
        id="datetime"
        type={type}
        value={value}
        className={classes.textfield}
        onFocus={() => setState((prevState) => ({ ...prevState, selected: true }))}
        onBlur={() => setState((prevState) => ({ ...prevState, selected: false }))}
        InputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }
        inputProps={{
          'data-testid': `datetime-${widget.label}`,
          className: classes.input,
        }}
      />
    </div>
  );
};

const getValueAndType = (dateTimeType: string): { value: string; type: string } => {
  const dateTime = new Date();

  // Get the local date and time components
  const year = String(dateTime.getFullYear()).padStart(4, '0');
  const month = String(dateTime.getMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getDate()).padStart(2, '0');
  const hours = String(dateTime.getHours()).padStart(2, '0');
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');

  if (dateTimeType === 'DATE') {
    return { value: `${year}-${month}-${day}`, type: 'date' };
  } else if (dateTimeType === 'TIME') {
    return { value: `${hours}:${minutes}`, type: 'time' };
  }
  return { value: `${year}-${month}-${day}T${hours}:${minutes}`, type: 'datetime-local' };
};
