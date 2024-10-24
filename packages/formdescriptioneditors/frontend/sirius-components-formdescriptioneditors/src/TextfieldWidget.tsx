/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { TextfieldStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TextfieldWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<TextfieldStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough }) => ({
    style: {
      backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : null,
      color: foregroundColor ? getCSSColor(foregroundColor, theme) : null,
      fontSize: fontSize ? fontSize : null,
      fontStyle: italic ? 'italic' : null,
      fontWeight: bold ? 'bold' : null,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
    },
    selected: {
      color: theme.palette.primary.main,
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
    },
  })
);

export const TextfieldWidget = ({ widget }: TextfieldWidgetProps) => {
  const props: TextfieldStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyles(props);

  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();
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
        {widget.hasHelpText ? <HelpOutlineOutlined color="inherit" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <TextField
        variant="standard"
        data-testid={widget.label}
        fullWidth
        inputRef={ref}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        InputProps={
          widget.style
            ? {
                className: classes.style,
                readOnly: true,
              }
            : { readOnly: true }
        }
        value="Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      />
    </div>
  );
};
