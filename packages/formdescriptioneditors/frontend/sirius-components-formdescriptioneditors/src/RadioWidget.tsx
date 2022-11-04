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
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
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
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

const useRadioPropertySectionStyles = makeStyles<Theme, StyleProps>((theme) => ({
  radioGroupRoot: {
    flexDirection: 'row',
  },
  style: {
    color: ({ color }) => (color ? color : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const RadioWidget = ({ widget, selection }: WidgetProps) => {
  const props: StyleProps = {
    color: readStyleProperty(widget, 'color'),
    fontSize: readNumericStyleProperty(widget, 'fontSize'),
    italic: readBooleanStyleProperty(widget, 'italic'),
    bold: readBooleanStyleProperty(widget, 'bold'),
    underline: readBooleanStyleProperty(widget, 'underline'),
    strikeThrough: readBooleanStyleProperty(widget, 'strikeThrough'),
  };
  const classes = useRadioPropertySectionStyles(props);

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
      <RadioGroup
        data-testid={widget.label}
        aria-label={widget.label}
        name={widget.label}
        defaultValue={'value2'}
        row
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}>
        <FormControlLabel
          value={'value1'}
          control={<Radio color="primary" data-testid={'value1'} />}
          label={
            <Typography
              classes={{
                root: classes.style,
              }}>
              Value 1
            </Typography>
          }
          checked={false}
        />
        <FormControlLabel
          value={'value2'}
          control={<Radio color="primary" data-testid={'value2'} inputRef={ref} />}
          label={
            <Typography
              classes={{
                root: classes.style,
              }}>
              Value 2
            </Typography>
          }
          checked={true}
        />
        <FormControlLabel
          value={'value3'}
          control={<Radio color="primary" data-testid={'value2'} />}
          label={
            <Typography
              classes={{
                root: classes.style,
              }}>
              Value 3
            </Typography>
          }
          checked={false}
        />
      </RadioGroup>
    </div>
  );
};
