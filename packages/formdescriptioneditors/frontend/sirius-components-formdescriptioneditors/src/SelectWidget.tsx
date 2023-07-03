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
import { getTextDecorationLineValue, SelectStyleProps } from '@eclipse-sirius/sirius-components-forms';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { SelectWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, SelectStyleProps>((theme) => ({
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

export const SelectWidget = ({ widget, selection }: SelectWidgetProps) => {
  const props: SelectStyleProps = {
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

      setSelected(true);

      cleanup = () => clearTimeout(timeoutId);
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
        fullWidth
        value="value1"
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
        <MenuItem
          value=""
          classes={
            widget.style
              ? {
                  root: classes.style,
                }
              : {}
          }>
          <em>None</em>
        </MenuItem>
        <MenuItem
          value="value1"
          classes={
            widget.style
              ? {
                  root: classes.style,
                }
              : {}
          }>
          Value 1
        </MenuItem>
        <MenuItem
          value="value2"
          classes={
            widget.style
              ? {
                  root: classes.style,
                }
              : {}
          }>
          Value 2
        </MenuItem>
        <MenuItem
          value="value3"
          classes={
            widget.style
              ? {
                  root: classes.style,
                }
              : {}
          }>
          Value 3
        </MenuItem>
      </Select>
    </div>
  );
};
