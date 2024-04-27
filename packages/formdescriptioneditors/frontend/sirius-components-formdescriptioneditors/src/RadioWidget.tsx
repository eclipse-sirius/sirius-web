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
import { RadioStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import FormControlLabel from '@mui/material/FormControlLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { RadioWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<RadioStyleProps>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    style: {
      color: color ? getCSSColor(color, theme) : null,
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

export const RadioWidget = ({ widget }: RadioWidgetProps) => {
  const props: RadioStyleProps = {
    color: widget.style?.color ?? null,
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
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
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
              classes={
                widget.style
                  ? {
                      root: classes.style,
                    }
                  : {}
              }>
              {'Value 1'}
            </Typography>
          }
          checked={false}
        />
        <FormControlLabel
          value={'value2'}
          control={<Radio color="primary" data-testid={'value2'} inputRef={ref} />}
          label={
            <Typography
              classes={
                widget.style
                  ? {
                      root: classes.style,
                    }
                  : {}
              }>
              {'Value 2'}
            </Typography>
          }
          checked={true}
        />
        <FormControlLabel
          value={'value3'}
          control={<Radio color="primary" data-testid={'value2'} />}
          label={
            <Typography
              classes={
                widget.style
                  ? {
                      root: classes.style,
                    }
                  : {}
              }>
              {'Value 3'}
            </Typography>
          }
          checked={false}
        />
      </RadioGroup>
    </div>
  );
};
