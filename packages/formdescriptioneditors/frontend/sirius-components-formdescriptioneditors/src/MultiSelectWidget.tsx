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
import { getTextDecorationLineValue, MultiSelectStyleProps } from '@eclipse-sirius/sirius-components-forms';
import Checkbox from '@material-ui/core/Checkbox';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { MultiSelectWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, MultiSelectStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : null),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
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

export const MultiSelectWidget = ({ widget, selection }: MultiSelectWidgetProps) => {
  const props: MultiSelectStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
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
    let cleanup = undefined;

    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      const timeoutId = setTimeout(function () {
        // added a timeout to compensate for a problem with MaterialUI's SelectInput component on focus while the element is not yet rendered
        ref.current.focus();
      }, 50);
      cleanup = () => clearTimeout(timeoutId);
      setSelected(true);
    } else {
      setSelected(false);
    }

    return cleanup;
  }, [selection, widget]);

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <Select
        data-testid={widget.label}
        label={widget.label}
        multiple
        fullWidth
        value={['value1', 'value3']}
        renderValue={() => 'Value 1, Value 3'}
        inputRef={ref}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        inputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }>
        <MenuItem key={'value1'} value={'value1'}>
          <Checkbox checked={true} />
          <ListItemText
            primary={'Value 1'}
            primaryTypographyProps={
              widget.style
                ? {
                    className: classes.style,
                  }
                : {}
            }
          />
        </MenuItem>
        <MenuItem key={'value2'} value={'value2'}>
          <Checkbox checked={false} />
          <ListItemText
            primary={'Value 2'}
            primaryTypographyProps={
              widget.style
                ? {
                    className: classes.style,
                  }
                : {}
            }
          />
        </MenuItem>
        <MenuItem key={'value3'} value={'value3'}>
          <Checkbox checked={true} />
          <ListItemText
            primary={'Value 3'}
            primaryTypographyProps={
              widget.style
                ? {
                    className: classes.style,
                  }
                : {}
            }
          />
        </MenuItem>
      </Select>
    </div>
  );
};
