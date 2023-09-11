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
import { getTextDecorationLineValue, ListStyleProps } from '@eclipse-sirius/sirius-components-forms';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import DeleteIcon from '@material-ui/icons/Delete';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import ImageIcon from '@material-ui/icons/Image';
import { useEffect, useRef, useState } from 'react';
import { ListWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, ListStyleProps>((theme) => ({
  style: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  icon: {
    width: '16px',
    height: '16px',
    marginRight: theme.spacing(2),
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

export const ListWidget = ({ widget, selection }: ListWidgetProps) => {
  const props: ListStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyles(props);

  const [selected, setSelected] = useState<Boolean>(false);
  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [widget, selection]);

  return (
    <div onFocus={() => setSelected(true)} onBlur={() => setSelected(false)} ref={ref} tabIndex={0}>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <Table size="small">
        <TableBody>
          <TableRow>
            <TableCell>
              {' '}
              <Typography color="textPrimary" className={classes.style}>
                <ImageIcon className={classes.icon} />
                Value 1
              </Typography>
            </TableCell>
            <TableCell align="right">
              <IconButton aria-label="deleteListItem">
                <DeleteIcon />
              </IconButton>
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell>
              {' '}
              <Typography color="textPrimary" className={classes.style}>
                <ImageIcon className={classes.icon} />
                Value 2
              </Typography>
            </TableCell>
            <TableCell align="right">
              <IconButton aria-label="deleteListItem">
                <DeleteIcon />
              </IconButton>
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell>
              {' '}
              <Typography color="textPrimary" className={classes.style}>
                <ImageIcon className={classes.icon} />
                Value 3
              </Typography>
            </TableCell>
            <TableCell align="right">
              <IconButton aria-label="deleteListItem">
                <DeleteIcon />
              </IconButton>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  );
};
