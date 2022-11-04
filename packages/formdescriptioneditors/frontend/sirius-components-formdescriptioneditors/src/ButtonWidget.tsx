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
import Button from '@material-ui/core/Button';
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

interface ButtonPropertySectionStyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
  iconOnly: boolean;
}

const useStyle = makeStyles<Theme, ButtonPropertySectionStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.light),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.main),
      color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
      fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
      fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
  },
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const ButtonWidget = ({ widget, selection }: WidgetProps) => {
  const props: ButtonPropertySectionStyleProps = {
    backgroundColor: readStyleProperty(widget, 'backgroundColor'),
    foregroundColor: readStyleProperty(widget, 'foregroundColor'),
    fontSize: readNumericStyleProperty(widget, 'fontSize'),
    italic: readBooleanStyleProperty(widget, 'italic'),
    bold: readBooleanStyleProperty(widget, 'bold'),
    underline: readBooleanStyleProperty(widget, 'underline'),
    strikeThrough: readBooleanStyleProperty(widget, 'strikeThrough'),
    iconOnly: false, // Can not be determined precisely here
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
      <Button
        data-testid={widget.label}
        variant="contained"
        color="primary"
        classes={{ root: classes.style }}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        ref={ref}>
        Lorem
      </Button>
    </div>
  );
};
