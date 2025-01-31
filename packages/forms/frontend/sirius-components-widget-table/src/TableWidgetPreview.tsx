/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { PreviewWidgetProps } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_ColumnDef, useMaterialReactTable } from 'material-react-table';
import { useEffect, useMemo, useState, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';

type TableElement = {
  column1Value: string;
  column2Value: string;
};

const data: TableElement[] = [
  {
    column1Value: 'row1.column1',
    column2Value: 'row1.column2',
  },
  {
    column1Value: 'row2.column1',
    column2Value: 'row2.column2',
  },
];

const useStyles = makeStyles()((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const TableWidgetPreview = ({ widget }: PreviewWidgetProps) => {
  const { classes } = useStyles();

  const [selected, setSelected] = useState<Boolean>(false);
  const { selection } = useSelection();
  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [widget, selection]);

  const columns = useMemo<MRT_ColumnDef<TableElement>[]>(
    () => [
      {
        accessorKey: 'column1Value',
        header: 'Column1',
        size: 150,
      },
      {
        accessorKey: 'column2Value',
        header: 'Column2',
        size: 150,
      },
    ],
    []
  );

  const table = useMaterialReactTable({
    columns,
    data,
    enablePagination: false,
    enableSorting: false,
    enableHiding: false,
    enableColumnFilters: false,
    enableGlobalFilter: false,
  });

  return (
    <>
      <div onFocus={() => setSelected(true)} onBlur={() => setSelected(false)} ref={ref} tabIndex={0}>
        <div className={classes.propertySectionLabel}>
          <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
            {widget.label}
          </Typography>
          {widget.hasHelpText ? (
            <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />
          ) : null}
        </div>
      </div>
      <div>
        <MaterialReactTable table={table} />
      </div>
    </>
  );
};
