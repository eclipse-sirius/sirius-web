/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { SelectStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SelectWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<SelectStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough, gridLayout }) => {
    const {
      gridTemplateColumns,
      gridTemplateRows,
      labelGridColumn,
      labelGridRow,
      widgetGridColumn,
      widgetGridRow,
      gap,
    } = {
      ...gridLayout,
    };
    return {
      style: {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : undefined,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : undefined,
        fontSize: fontSize ? fontSize : undefined,
        fontStyle: italic ? 'italic' : undefined,
        fontWeight: bold ? 'bold' : undefined,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      },
      selected: {
        color: theme.palette.primary.main,
      },
      propertySection: {
        display: 'grid',
        gridTemplateColumns,
        gridTemplateRows,
        alignItems: 'center',
        gap: gap ?? '',
      },
      propertySectionLabel: {
        gridColumn: labelGridColumn,
        gridRow: labelGridRow,
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
      },
      propertySectionWidget: {
        gridColumn: widgetGridColumn,
        gridRow: widgetGridRow,
      },
    };
  }
);

export const SelectWidget = ({ widget }: SelectWidgetProps) => {
  const props: SelectStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyles(props);

  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();
  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    let cleanup = () => {};

    if (selection.entries.find((entry) => entry.id === widget.id)) {
      const timeoutId = setTimeout(function () {
        if (ref.current) {
          // added a timeout to compensate for a problem with MaterialUI's SelectInput component on focus while the element is not yet rendered
          ref.current.focus();
        }
      }, 50);

      setSelected(true);

      cleanup = () => clearTimeout(timeoutId);
    } else {
      setSelected(false);
    }

    return cleanup;
  }, [selection, widget]);

  return (
    <div className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <div className={classes.propertySectionWidget}>
        <Select
          variant="standard"
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
    </div>
  );
};
