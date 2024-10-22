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
import { MultiSelectStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import Checkbox from '@mui/material/Checkbox';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { MultiSelectWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<MultiSelectStyleProps>()(
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

export const MultiSelectWidget = ({ widget }: MultiSelectWidgetProps) => {
  const props: MultiSelectStyleProps = {
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
    </div>
  );
};
