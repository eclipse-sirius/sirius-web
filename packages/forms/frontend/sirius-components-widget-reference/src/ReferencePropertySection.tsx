/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
  DRAG_SOURCES_TYPE,
  SelectionEntry,
  ServerContext,
  useMultiToast,
} from '@eclipse-sirius/sirius-components-core';
import { PropertySectionComponentProps, PropertySectionLabel } from '@eclipse-sirius/sirius-components-forms';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles } from '@material-ui/core/styles';
import ArrowDownwardIcon from '@material-ui/icons/ArrowDownward';
import ArrowUpwardIcon from '@material-ui/icons/ArrowUpward';
import DeleteIcon from '@material-ui/icons/Delete';
import { useContext, useEffect } from 'react';
import {
  GQLEditReferenceData,
  GQLEditReferencePayload,
  GQLEditReferenceVariables,
  GQLErrorPayload,
  GQLReferenceWidget,
  GQLSuccessPayload,
} from './ReferenceWidgetFragment.types';

const useStyles = makeStyles((_) => ({
  root: {
    overflow: 'auto',
    maxHeight: 300,
  },
}));

export const editReferenceMutation = gql`
  mutation editReference($input: EditReferenceInput!) {
    editReference(input: $input) {
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

const isErrorPayload = (payload: GQLEditReferencePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditReferencePayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const ReferencePropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLReferenceWidget>) => {
  const { httpOrigin } = useContext(ServerContext);

  const [editReference, { loading, error, data }] = useMutation<GQLEditReferenceData, GQLEditReferenceVariables>(
    editReferenceMutation
  );

  const onDelete = (valueId: string) => {
    let newValueIds = [];
    if (widget.manyValued) {
      newValueIds = widget.referenceValues.map((rv) => rv.id).filter((id) => id !== valueId);
    }
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
        newValueIds,
      },
    };
    editReference({ variables });
  };

  const onMoveUp = (valueId: string) => {
    const valueIds = widget.referenceValues.map((rv) => rv.id);
    const index = valueIds.findIndex((id) => id === valueId);
    const newValueIds = widget.referenceValues.map((rv) => rv.id);
    if (index > 0) {
      const predecessor = valueIds[index - 1];
      newValueIds[index - 1] = valueId;
      newValueIds[index] = predecessor;
    }
    if (index !== -1) {
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          newValueIds,
        },
      };
      editReference({ variables });
    }
  };

  const onMoveDown = (valueId: string) => {
    const valueIds = widget.referenceValues.map((rv) => rv.id);
    const index = valueIds.findIndex((id) => id === valueId);
    const newValueIds = widget.referenceValues.map((rv) => rv.id);
    if (index < newValueIds.length - 1) {
      const successor = valueIds[index + 1];
      newValueIds[index + 1] = valueId;
      newValueIds[index] = successor;
    }
    if (index !== -1) {
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          newValueIds,
        },
      };
      editReference({ variables });
    }
  };

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editReference: editMultiValuedReference } = data;
        if (isErrorPayload(editMultiValuedReference) || isSuccessPayload(editMultiValuedReference)) {
          addMessages(editMultiValuedReference.messages);
        }
      }
    }
  }, [loading, error, data]);

  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
  };

  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
  };

  const handleDrop = (event: React.DragEvent) => {
    event.preventDefault();
    if (readOnly) {
      addErrorMessage('This widget is currently read-only');
    } else {
      const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
      if (dragSourcesStringified) {
        const sources = JSON.parse(dragSourcesStringified);
        if (Array.isArray(sources) && sources.length > 0) {
          const entries = sources as SelectionEntry[];
          const semanticElementIds = entries
            .filter((entry) => entry.kind.startsWith('siriusComponents://semantic?'))
            .map((entry) => entry.id);
          const valueIds = widget.referenceValues.map((referenceValue) => referenceValue.id);

          let newValueIds = [];
          if (widget.manyValued) {
            newValueIds = [...valueIds, ...semanticElementIds];
          } else {
            // We let the backend decide how to handle it if there are multiple values.
            newValueIds = [...semanticElementIds];
          }

          const variables = {
            input: {
              id: crypto.randomUUID(),
              editingContextId,
              representationId: formId,
              referenceWidgetId: widget.id,
              newValueIds,
            },
          };
          editReference({ variables });
        }
      }
    }
  };

  let items;
  if (widget.referenceValues.length === 0) {
    items = (
      <ListItem>
        <ListItemText data-testid="reference-value-none">None</ListItemText>
      </ListItem>
    );
  } else {
    items = widget.referenceValues.map((item, index) => (
      <ListItem key={item.id}>
        <ListItemIcon>
          {item.iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + item.iconURL} /> : null}
        </ListItemIcon>
        <ListItemText data-testid={`reference-value-${item.id}`}>{item.label}</ListItemText>
        {widget.manyValued ? (
          <>
            <IconButton
              aria-label="up"
              disabled={readOnly || widget.readOnly || index === 0}
              onClick={() => onMoveUp(item.id)}>
              <ArrowUpwardIcon />
            </IconButton>
            <IconButton
              aria-label="down"
              disabled={readOnly || widget.readOnly || index === widget.referenceValues.length - 1}
              onClick={() => onMoveDown(item.id)}>
              <ArrowDownwardIcon />
            </IconButton>
          </>
        ) : null}
        <IconButton aria-label="delete" disabled={readOnly || widget.readOnly} onClick={() => onDelete(item.id)}>
          <DeleteIcon />
        </IconButton>
      </ListItem>
    ));
  }

  const classes = useStyles();

  return (
    <div>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        data-testid={widget.label}
      />

      {readOnly || widget.readOnly ? (
        <List dense className={classes.root}>
          {items}
        </List>
      ) : (
        <div onDragEnter={handleDragEnter} onDragOver={handleDragOver} onDrop={handleDrop}>
          <List dense className={classes.root}>
            {items}
          </List>
        </div>
      )}
    </div>
  );
};
