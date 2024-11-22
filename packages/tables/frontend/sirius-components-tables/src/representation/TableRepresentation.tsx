/*******************************************************************************
 * Copyright (c) 2024 CEA List.
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
import { RepresentationComponentProps } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { TableContent } from '../table/TableContent';
import { useTableSubscription } from './useTableSubscription';

const useTableRepresentationStyles = makeStyles()((theme) => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: theme.spacing(8),
  },
}));

export const TableRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
  const { classes } = useTableRepresentationStyles();
  const { complete, table } = useTableSubscription(editingContextId, representationId);
  let completeMessage: JSX.Element | null = null;
  if (complete) {
    completeMessage = (
      <div className={classes.complete}>
        <Typography variant="h6" align="center">
          The table does not exist anymore
        </Typography>
      </div>
    );
  }
  return (
    <div data-testid={'table-representation'}>
      {table !== null && !complete ? (
        <TableContent
          editingContextId={editingContextId}
          representationId={representationId}
          table={table}
          readOnly={readOnly}
        />
      ) : null}
      {completeMessage}
    </div>
  );
};
