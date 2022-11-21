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
import { getTextDecorationLineValue, LabelStyleProps } from '@eclipse-sirius/sirius-components-forms';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useEffect, useRef, useState } from 'react';
import { LabelWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, LabelStyleProps>((theme) => ({
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

export const LabelWidget = ({ widget, selection }: LabelWidgetProps) => {
  const props: LabelStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyles(props);

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
      <Typography
        ref={ref}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        tabIndex={0}
        className={classes.style}>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit
      </Typography>
    </div>
  );
};
