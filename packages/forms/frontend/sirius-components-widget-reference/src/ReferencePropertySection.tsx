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
import {
  getTextDecorationLineValue,
  PropertySectionComponentProps,
  PropertySectionLabel,
  useClickHandler,
} from '@eclipse-sirius/sirius-components-forms';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles, Theme } from '@material-ui/core/styles';
import ArrowDownwardIcon from '@material-ui/icons/ArrowDownward';
import ArrowUpwardIcon from '@material-ui/icons/ArrowUpward';
import DeleteIcon from '@material-ui/icons/Delete';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { useContext, useEffect, useState } from 'react';
import { BrowseModal } from './modals/BrowseModal';
import {
  GQLClickReferenceValueMutationData,
  GQLClickReferenceValueMutationVariables,
  GQLEditReferenceData,
  GQLEditReferenceInput,
  GQLEditReferencePayload,
  GQLEditReferenceVariables,
  GQLErrorPayload,
  GQLReferenceValue,
  GQLReferenceWidget,
  GQLReferenceWidgetStyle,
  GQLSuccessPayload,
} from './ReferenceWidgetFragment.types';

const useStyles = makeStyles<Theme, GQLReferenceWidgetStyle>(() => ({
  root: {
    overflow: 'auto',
    maxHeight: 300,
  },
  style: {
    color: ({ color }) => (color ? color : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  canBeSelectedItem: {
    '&:hover': {
      textDecoration: 'underline',
      cursor: 'pointer',
    },
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

export const clickReferenceValueMutation = gql`
  mutation clickReferenceValue($input: ClickReferenceValueInput!) {
    clickReferenceValue(input: $input) {
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
  const props: GQLReferenceWidgetStyle = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyles(props);
  const { httpOrigin } = useContext(ServerContext);

  const [editReference, { loading, error, data }] = useMutation<GQLEditReferenceData, GQLEditReferenceVariables>(
    editReferenceMutation
  );

  const [clickReferenceValue, { loading: clickLoading, error: clickError, data: clickData }] = useMutation<
    GQLClickReferenceValueMutationData,
    GQLClickReferenceValueMutationVariables
  >(clickReferenceValueMutation);

  const onDelete = (valueId: string) => {
    let newValueIds = [];
    if (widget.reference.manyValued) {
      newValueIds = widget.referenceValues.map((rv) => rv.id).filter((id) => id !== valueId);
    }
    const variables: { input: GQLEditReferenceInput } = {
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

  const onReferenceValueSimpleClick = (item: GQLReferenceValue) => {
    const variables: GQLClickReferenceValueMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
        referenceValueId: item.id,
        clickEventKind: 'SINGLE_CLICK',
      },
    };
    clickReferenceValue({ variables });
  };
  const onReferenceValueDoubleClick = (item: GQLReferenceValue) => {
    const variables: GQLClickReferenceValueMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
        referenceValueId: item.id,
        clickEventKind: 'DOUBLE_CLICK',
      },
    };
    clickReferenceValue({ variables });
  };

  const clickHandler = useClickHandler<GQLReferenceValue>(onReferenceValueSimpleClick, onReferenceValueDoubleClick);

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
  useEffect(() => {
    if (!clickLoading) {
      if (clickError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (clickData) {
        const { clickReferenceValue } = clickData;
        if (isErrorPayload(clickReferenceValue) || isSuccessPayload(clickReferenceValue)) {
          addMessages(clickReferenceValue.messages);
        }
      }
    }
  }, [clickLoading, clickError, clickData]);

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
          if (widget.reference.manyValued) {
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
        <ListItemText data-testid="reference-value-none" classes={{ primary: classes.style }}>
          None
        </ListItemText>
        <IconButton aria-label="more" size="small" disabled={readOnly || widget.readOnly} onClick={() => onBrowse()}>
          <MoreHorizIcon />
        </IconButton>
      </ListItem>
    );
  } else {
    items = widget.referenceValues.map((item, index) => (
      <ListItem key={item.id}>
        <ListItemIcon>
          {item.iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + item.iconURL} /> : null}
        </ListItemIcon>
        <ListItemText
          data-testid={`reference-value-${item.label}`}
          onClick={() => (readOnly || widget.readOnly || !item.hasClickAction ? {} : clickHandler(item))}
          classes={{
            primary: `${!readOnly && !widget.readOnly && item.hasClickAction ? classes.canBeSelectedItem : ''} ${
              classes.style
            }`,
          }}>
          {item.label}
        </ListItemText>
        {widget.reference.manyValued ? (
          <>
            <IconButton
              aria-label="up"
              size="small"
              disabled={readOnly || widget.readOnly || index === 0}
              onClick={() => onMoveUp(item.id)}>
              <ArrowUpwardIcon />
            </IconButton>
            <IconButton
              aria-label="down"
              size="small"
              disabled={readOnly || widget.readOnly || index === widget.referenceValues.length - 1}
              onClick={() => onMoveDown(item.id)}>
              <ArrowDownwardIcon />
            </IconButton>
          </>
        ) : null}
        <IconButton
          aria-label="more"
          size="small"
          title="Select an object"
          data-testid={`${widget.label}-more`}
          disabled={readOnly || widget.readOnly}
          onClick={() => onBrowse()}>
          <MoreHorizIcon />
        </IconButton>
        <IconButton
          aria-label="delete"
          size="small"
          title="Unset the object"
          data-testid={`${widget.label}-delete`}
          disabled={readOnly || widget.readOnly}
          onClick={() => onDelete(item.id)}>
          <DeleteIcon />
        </IconButton>
      </ListItem>
    ));
  }

  const [modalDisplayed, setModalDisplayed] = useState<string | null>(null);

  const onBrowse = () => {
    setModalDisplayed('browse');
  };

  let modal: JSX.Element | null = null;
  if (modalDisplayed === 'browse') {
    const addSelectedElements = (selectedElementIds: string[]): void => {
      setModalDisplayed(null);
      if (selectedElementIds) {
        const valueIds: string[] = widget.referenceValues.map((referenceValue) => referenceValue.id);

        let newValueIds: string[] = [];
        if (widget.reference.manyValued) {
          newValueIds = [...valueIds, ...selectedElementIds];
        } else {
          newValueIds = [...selectedElementIds];
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
    };
    modal = <BrowseModal editingContextId={editingContextId} onClose={addSelectedElements} widget={widget} />;
  }

  return (
    <>
      <div>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          subscribers={subscribers}
          data-testid={widget.label + '_' + widget.reference.typeName + '.' + widget.reference.referenceName}
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
        <Divider />
      </div>
      {modal}
    </>
  );
};
