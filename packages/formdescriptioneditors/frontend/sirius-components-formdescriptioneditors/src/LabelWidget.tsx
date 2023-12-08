/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { getTextDecorationLineValue, LabelStyleProps } from '@eclipse-sirius/sirius-components-forms';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { LabelWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, LabelStyleProps>((theme) => ({
  style: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : undefined),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : undefined),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'unset'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'unset'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const LabelWidget = ({ widget, selection }: LabelWidgetProps) => {
  const props: LabelStyleProps = {
    color: widget.style?.color ?? undefined,
    fontSize: widget.style?.fontSize ?? undefined,
    italic: widget.style?.italic ?? undefined,
    bold: widget.style?.bold ?? undefined,
    underline: widget.style?.underline ?? undefined,
    strikeThrough: widget.style?.strikeThrough ?? undefined,
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
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
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
