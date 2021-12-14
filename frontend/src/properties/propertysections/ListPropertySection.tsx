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
import { useMutation } from '@apollo/client';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import DeleteIcon from '@material-ui/icons/Delete';
import { httpOrigin } from 'common/URL';
import { ListItem } from 'form/Form.types';
import gql from 'graphql-tag';
import {
  GQLDeleteListItemMutationData,
  GQLDeleteListItemPayload,
  GQLErrorPayload,
  ListPropertySectionProps,
} from 'properties/propertysections/ListPropertySection.types';
import { PropertySectionLabel } from 'properties/propertysections/PropertySectionLabel';
import React, { MouseEvent, useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';

const deleteListItemMutation = gql`
  mutation deleteListItem($input: DeleteListItemInput!) {
    deleteListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const useListPropertySectionStyles = makeStyles((theme) => ({
  cell: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  icon: {
    width: '16px',
    height: '16px',
    marginRight: theme.spacing(2),
  },
  canBeSelectedItem: {
    '&:hover': {
      textDecoration: 'underline',
      cursor: 'pointer',
    },
  },
}));

const NONE_WIDGET_ITEM_ID = 'none';

const isErrorPayload = (payload: GQLDeleteListItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
export const ListPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
  setSelection,
}: ListPropertySectionProps) => {
  const classes = useListPropertySectionStyles();
  const [message, setMessage] = useState<string | null>(null);

  let items = [...widget.items];
  if (items.length === 0) {
    items.push({
      id: NONE_WIDGET_ITEM_ID,
      imageURL: '',
      label: 'None',
      kind: 'Unknown',
      deletable: false,
    });
  }

  const [deleteListItem, { loading: deleteLoading, error: deleteError, data: deleteData }] =
    useMutation<GQLDeleteListItemMutationData>(deleteListItemMutation);

  const onDelete = (event: MouseEvent<HTMLElement>, item: ListItem) => {
    const variables = {
      input: {
        id: uuid(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        listItemId: item.id,
      },
    };
    deleteListItem({ variables });
  };

  useEffect(() => {
    if (!deleteLoading) {
      if (deleteError) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (deleteData) {
        const { deleteListItem } = deleteData;
        if (isErrorPayload(deleteListItem)) {
          setMessage(deleteListItem.message);
        }
      }
    }
  }, [deleteLoading, deleteError, deleteData]);

  const onRowClick = (item: ListItem) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
  };

  const getTableCellContent = (item: ListItem): JSX.Element => {
    return (
      <Typography
        className={classes.canBeSelectedItem}
        onClick={() => onRowClick(item)}
        color="textPrimary"
        data-testid={`representation-${item.id}`}
      >
        {item.imageURL ? (
          <img className={classes.icon} width="16" height="16" alt={item.label} src={httpOrigin + item.imageURL} />
        ) : null}
        {item.label}
      </Typography>
    );
  };

  return (
    <FormControl error={widget.diagnostics.length > 0} fullWidth>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <Table size="small">
        <TableBody>
          {items.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{getTableCellContent(item)}</TableCell>
              <TableCell align="right">
                <IconButton
                  aria-label="deleteListItem"
                  onClick={(event) => onDelete(event, item)}
                  disabled={readOnly || !item.deletable}
                  data-testid={`delete-representation-${item.id}`}
                >
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </FormControl>
  );
};
