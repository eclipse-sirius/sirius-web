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

import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import DeleteIcon from '@material-ui/icons/Delete';
import ImageIcon from '@material-ui/icons/Image';
import { useEffect, useRef, useState } from 'react';
import { WidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles((theme) => ({
  icon: {
    width: '16px',
    height: '16px',
    marginRight: theme.spacing(2),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const ListWidget = ({ widget, selection }: WidgetProps) => {
  const [selected, setSelected] = useState<Boolean>(false);

  const classes = useStyles();
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
      <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
        {widget.label}
      </Typography>
      <Table size="small">
        <TableBody>
          <TableRow>
            <TableCell>
              {' '}
              <Typography color="textPrimary">
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
              <Typography color="textPrimary">
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
              <Typography color="textPrimary">
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
