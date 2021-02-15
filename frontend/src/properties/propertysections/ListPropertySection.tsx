/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import { Permission } from 'project/Permission';
import { ListPropertySectionProps } from 'properties/propertysections/ListPropertySection.types';
import { PropertySectionLabel } from 'properties/propertysections/PropertySectionLabel';
import React from 'react';

const useListPropertySectionStyles = makeStyles((theme) => ({
  table: {
    borderStyle: 'solid',
    borderWidth: '1px',
    borderColor: theme.palette.divider,
  },
}));

export const ListPropertySection = ({ widget, subscribers }: ListPropertySectionProps) => {
  const classes = useListPropertySectionStyles();

  let items = widget.items;
  if (items.length === 0) {
    items.push({
      id: 'none',
      imageURL: '',
      label: 'None',
    });
  }

  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <Permission requiredAccessLevel="EDIT">
        <Table className={classes.table}>
          <TableBody>
            {widget.items.map((item) => (
              <TableRow key={item.id}>
                <TableCell>{item.label}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Permission>
    </div>
  );
};
