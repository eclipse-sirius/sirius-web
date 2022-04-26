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
import { useSubscription } from '@apollo/client';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import TextFieldsIcon from '@material-ui/icons/TextFields';
import { useMachine } from '@xstate/react';
import {
  GQLFormDescriptionEditorEventInput,
  GQLFormDescriptionEditorEventSubscription,
  GQLFormDescriptionEditorEventVariables,
  GQLFormDescriptionEditorWidget,
  Kind,
} from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainer.types';
import {
  FormDescriptionEditorWebSocketContainerContext,
  FormDescriptionEditorWebSocketContainerEvent,
  formDescriptionEditorWebSocketContainerMachine,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  InitializeRepresentationEvent,
  SchemaValue,
  ShowToastEvent,
} from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainerMachine';
import { WidgetEntry } from 'formdescriptioneditor/WidgetEntry';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import { RepresentationComponentProps } from 'workbench/Workbench.types';

export const formDescriptionEditorEventSubscription = gql`
  subscription formDescriptionEditorEvent($input: FormDescriptionEditorEventInput!) {
    formDescriptionEditorEvent(input: $input) {
      __typename
      ... on ErrorPayload {
        id
        message
      }
      ... on SubscribersUpdatedEventPayload {
        id
        subscribers {
          username
        }
      }
      ... on FormDescriptionEditorRefreshedEventPayload {
        id
        formDescriptionEditor {
          id
          metadata {
            kind
            label
            description {
              id
            }
          }
          widgets {
            id
            label
            kind
          }
        }
      }
    }
  }
`;

const useFormDescriptionEditorStyles = makeStyles((theme) => ({
  formDescriptionEditor: {
    display: 'flex',
    flexDirection: 'column',
    width: '100%',
  },
  header: {
    padding: '4px 8px 4px 8px',
    display: 'flex',
    flexDirection: 'row',
  },
  main: {
    display: 'flex',
    flexDirection: 'row',
    height: '100%',
    borderTop: '1px solid grey',
  },
  widgets: {
    display: 'flex',
    flexDirection: 'column',
    borderRight: '1px solid grey',
    padding: '4px 8px 4px 8px',
  },
  widgetKind: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    marginTop: '8px',
    marginBottom: '8px',
    cursor: 'move',
  },
  preview: {
    width: '100%',
    padding: '4px 8px 4px 8px',
  },
  body: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
  },
  bottomDropArea: {
    height: '100px',
  },
  dragOver: {
    border: 'dashed 1px red',
  },
  subscribers: {
    marginLeft: 'auto',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
  avatar: {
    fontSize: '1rem',
    width: theme.spacing(3),
    height: theme.spacing(3),
  },
  noFormDescriptionEditor: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyItems: 'center',
  },
}));

const isKind = (value: string): value is Kind => {
  return value === 'Textfield';
};

export const FormDescriptionEditorWebSocketContainer = ({
  editingContextId,
  representationId,
  selection,
  setSelection,
}: RepresentationComponentProps) => {
  const classes = useFormDescriptionEditorStyles();

  const [{ value, context }, dispatch] = useMachine<
    FormDescriptionEditorWebSocketContainerContext,
    FormDescriptionEditorWebSocketContainerEvent
  >(formDescriptionEditorWebSocketContainerMachine);
  const { toast, formDescriptionEditorWebSocketContainer } = value as SchemaValue;
  const { id, formDescriptionEditor, subscribers, message } = context;

  const input: GQLFormDescriptionEditorEventInput = {
    id,
    editingContextId,
    formDescriptionEditorId: representationId,
  };
  const variables: GQLFormDescriptionEditorEventVariables = { input };

  const { error } = useSubscription<GQLFormDescriptionEditorEventSubscription, GQLFormDescriptionEditorEventVariables>(
    formDescriptionEditorEventSubscription,
    {
      variables,
      fetchPolicy: 'no-cache',
      skip: formDescriptionEditorWebSocketContainer !== 'ready',
      onSubscriptionData: ({ subscriptionData }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: subscriptionData,
        };
        dispatch(handleDataEvent);
      },
      onSubscriptionComplete: () => {
        dispatch({ type: 'HANDLE_COMPLETE' });
      },
    }
  );

  useEffect(() => {
    if (error) {
      const message: string = 'An error has occurred while trying to retrieve the form description editor';
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
      dispatch({ type: 'HANDLE_COMPLETE' });
    }
  }, [error, dispatch]);

  useEffect(() => {
    if (formDescriptionEditorWebSocketContainer === 'loading') {
      const initializeRepresentationEvent: InitializeRepresentationEvent = {
        type: 'INITIALIZE',
      };
      dispatch(initializeRepresentationEvent);
    }
  }, [formDescriptionEditorWebSocketContainer, dispatch]);

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('text/plain', event.currentTarget.id);
  };
  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('text/plain');
    const newWidget: GQLFormDescriptionEditorWidget = {
      id: uuid(),
      label: id,
      kind: isKind(id) ? id : 'Textfield',
    };
    const updatedWidgets: GQLFormDescriptionEditorWidget[] = formDescriptionEditor.widgets;
    updatedWidgets.push(newWidget);
  };
  const handleDropBefore = (event: React.DragEvent<HTMLDivElement>, widget: GQLFormDescriptionEditorWidget) => {
    const id: string = event.dataTransfer.getData('text/plain');
    const newWidget: GQLFormDescriptionEditorWidget = {
      id: uuid(),
      label: id,
      kind: isKind(id) ? id : 'Textfield',
    };
    const updatedWidgets: GQLFormDescriptionEditorWidget[] = formDescriptionEditor.widgets;
    let index: number = updatedWidgets.indexOf(widget);
    if (index === -1) {
      updatedWidgets.push(newWidget);
    } else {
      updatedWidgets.splice(index, 0, newWidget);
    }
  };

  let content: JSX.Element | null = null;

  if (formDescriptionEditorWebSocketContainer === 'ready' && formDescriptionEditor) {
    content = (
      <div className={classes.main}>
        <div className={classes.widgets}>
          <Typography gutterBottom>Widgets</Typography>
          <div
            id="Textfield"
            data-testid="FormDescriptionEditor-Textfield"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStart}
          >
            <TextFieldsIcon />
            <Typography variant="caption" gutterBottom>
              Textfield
            </Typography>
          </div>
        </div>
        <div className={classes.preview}>
          <Typography>Preview</Typography>
          <div className={classes.body}>
            {formDescriptionEditor.widgets.map((widget) => (
              <WidgetEntry
                key={widget.id}
                widget={widget}
                selection={selection}
                setSelection={setSelection}
                onDropBefore={handleDropBefore}
              />
            ))}
            <div
              data-testid="FormDescriptionEditor-DropArea"
              className={classes.bottomDropArea}
              onDragEnter={handleDragEnter}
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              onDrop={handleDrop}
            />
          </div>
        </div>
      </div>
    );
  }

  if (formDescriptionEditorWebSocketContainer === 'complete') {
    content = (
      <div className={classes.main + ' ' + classes.noFormDescriptionEditor}>
        <Typography variant="h5" align="center" data-testid="FormDescriptionEditor-complete-message">
          The form description editor does not exist
        </Typography>
      </div>
    );
  }

  return (
    <div className={classes.formDescriptionEditor}>
      <div className={classes.header}>
        <Typography>Form</Typography>
        <div className={classes.subscribers}>
          {subscribers.map((subscriber) => (
            <Tooltip title={subscriber.username} arrow key={subscriber.username}>
              <Avatar classes={{ root: classes.avatar }}>{subscriber.username.substring(0, 1).toUpperCase()}</Avatar>
            </Tooltip>
          ))}
        </div>
      </div>
      {content}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
