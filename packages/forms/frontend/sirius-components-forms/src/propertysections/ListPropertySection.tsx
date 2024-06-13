/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import {
  IconOverlay,
  getCSSColor,
  useDeletionConfirmationDialog,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';
import { MouseEvent, useEffect } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLList, GQLListItem } from '../form/FormEventFragments.types';
import {
  GQLClickListItemMutationData,
  GQLClickListItemMutationVariables,
  GQLDeleteListItemMutationData,
  GQLDeleteListItemPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  ListStyleProps,
} from './ListPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { useClickHandler } from './useClickHandler';

export const deleteListItemMutation = gql`
  mutation deleteListItem($input: DeleteListItemInput!) {
    deleteListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const clickListItemMutation = gql`
  mutation clickListItem($input: ClickListItemInput!) {
    clickListItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const useListPropertySectionStyles = makeStyles<Theme, ListStyleProps>((theme) => ({
  cell: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: theme.spacing(0.25),
    paddingBottom: theme.spacing(0.25),
  },
  canBeSelectedItem: {
    '&:hover': {
      textDecoration: 'underline',
      cursor: 'pointer',
    },
  },
  style: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    flexGrow: 1,
  },
}));

const NONE_WIDGET_ITEM_ID = 'none';

const isErrorPayload = (payload: GQLDeleteListItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDeleteListItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const ListPropertySection: PropertySectionComponent<GQLList> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLList>) => {
  const props: ListStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useListPropertySectionStyles(props);
  const theme = useTheme();
  const { setSelection } = useSelection();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  let items = [...widget.items];
  if (items.length === 0) {
    items.push({
      id: NONE_WIDGET_ITEM_ID,
      iconURL: [],
      label: 'None',
      kind: 'Unknown',
      deletable: false,
    });
  }

  const [deleteListItem, { loading: deleteLoading, error: deleteError, data: deleteData }] =
    useMutation<GQLDeleteListItemMutationData>(deleteListItemMutation);

  const [clickListItem, { loading: clickLoading, error: clickError, data: clickData }] = useMutation<
    GQLClickListItemMutationData,
    GQLClickListItemMutationVariables
  >(clickListItemMutation);

  const onDelete = (_: MouseEvent<HTMLElement>, item: GQLListItem) => {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        listItemId: item.id,
      },
    };
    showDeletionConfirmation(() => {
      deleteListItem({ variables });
    });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!deleteLoading) {
      if (deleteError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (deleteData) {
        const { deleteListItem } = deleteData;
        if (isErrorPayload(deleteListItem) || isSuccessPayload(deleteListItem)) {
          addMessages(deleteListItem.messages);
        }
      }
    }
  }, [deleteLoading, deleteError, deleteData]);

  useEffect(() => {
    if (!clickLoading) {
      if (clickError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (clickData) {
        const { clickListItem } = clickData;
        if (isErrorPayload(clickListItem) || isSuccessPayload(clickListItem)) {
          addMessages(clickListItem.messages);
        }
      }
    }
  }, [clickLoading, clickError, clickData]);
  const onSimpleClick = (item: GQLListItem) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
    const variables: GQLClickListItemMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        listItemId: item.id,
        clickEventKind: 'SINGLE_CLICK',
      },
    };

    clickListItem({ variables });
  };
  const onDoubleClick = (item: GQLListItem) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
    const variables: GQLClickListItemMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        listId: widget.id,
        listItemId: item.id,
        clickEventKind: 'DOUBLE_CLICK',
      },
    };
    clickListItem({ variables });
  };

  const clickHandler = useClickHandler<GQLListItem>(onSimpleClick, onDoubleClick);

  const getTableCellContent = (item: GQLListItem): JSX.Element => {
    return (
      <>
        <IconOverlay iconURL={item.iconURL} alt={item.label} customIconStyle={{ marginRight: theme.spacing(2) }} />
        <Typography
          className={`${readOnly || widget.readOnly ? '' : classes.canBeSelectedItem} ${classes.style}`}
          onClick={() => (readOnly || widget.readOnly ? {} : clickHandler(item))}
          color="textPrimary"
          data-testid={`representation-${item.label}`}>
          {item.label}
        </Typography>
        <IconButton
          aria-label="deleteListItem"
          onClick={(event) => onDelete(event, item)}
          disabled={readOnly || !item.deletable || widget.readOnly}
          size="small"
          data-testid={`delete-representation-${item.label}`}>
          <DeleteIcon />
        </IconButton>
      </>
    );
  };

  return (
    <FormControl error={widget.diagnostics.length > 0} fullWidth>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <Table size="small" data-testid={`table-${widget.label}`}>
        <TableBody>
          {items.map((item) => (
            <TableRow key={item.id}>
              <TableCell className={classes.cell}>{getTableCellContent(item)}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    </FormControl>
  );
};
