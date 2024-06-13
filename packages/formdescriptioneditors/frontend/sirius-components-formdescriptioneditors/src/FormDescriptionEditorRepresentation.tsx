/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { gql, useSubscription } from '@apollo/client';
import { RepresentationComponentProps, Toast } from '@eclipse-sirius/sirius-components-core';
import { PropertySectionContext, PropertySectionContextValue } from '@eclipse-sirius/sirius-components-forms';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import ViewAgendaIcon from '@material-ui/icons/ViewAgenda';
import WebIcon from '@material-ui/icons/Web';
import { useMachine } from '@xstate/react';
import React, { useContext, useEffect } from 'react';
import { formDescriptionEditorEventSubscription } from './FormDescriptionEditorEventFragment';
import {
  GQLFormDescriptionEditorEventInput,
  GQLFormDescriptionEditorEventSubscription,
  GQLFormDescriptionEditorEventVariables,
} from './FormDescriptionEditorEventFragment.types';
import { WidgetDescriptor } from './FormDescriptionEditorRepresentation.types';
import {
  FormDescriptionEditorRepresentationContext,
  FormDescriptionEditorRepresentationEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  InitializeRepresentationEvent,
  SchemaValue,
  ShowToastEvent,
  formDescriptionEditorRepresentationMachine,
} from './FormDescriptionEditorRepresentationMachine';
import { PageList } from './PageList';
import { coreWidgets } from './coreWidgets';
import { FormDescriptionEditorContextProvider } from './hooks/FormDescriptionEditorContext';
import { ForIcon } from './icons/ForIcon';
import { IfIcon } from './icons/IfIcon';

const useFormDescriptionEditorStyles = makeStyles((theme) => ({
  formDescriptionEditor: {
    display: 'flex',
    flexDirection: 'column',
    width: '100%',
    overflowX: 'auto',
  },
  hover: {
    borderColor: theme.palette.primary.main,
  },
  header: {
    padding: '4px 8px 4px 8px',
    display: 'flex',
    flexDirection: 'row',
  },
  disabledOverlay: {
    opacity: 0.5,
  },
  main: {
    display: 'flex',
    flexDirection: 'row',
    height: '100%',
    borderTop: '1px solid grey',
    overflowY: 'auto',
  },
  widgets: {
    display: 'flex',
    flexDirection: 'column',
    borderRight: '1px solid grey',
    padding: '4px 8px 4px 8px',
    overflowY: 'auto',
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
    overflowY: 'auto',
  },
  body: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
    overflowX: 'auto',
  },
  bottomDropArea: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    borderRadius: '10px',
    color: 'gray',
    height: '60px',
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
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

export const FormDescriptionEditorRepresentation = ({
  editingContextId,
  representationId,
  readOnly,
}: RepresentationComponentProps) => {
  const classes = useFormDescriptionEditorStyles();
  const noop = () => {};

  const [{ value, context }, dispatch] = useMachine<
    FormDescriptionEditorRepresentationContext,
    FormDescriptionEditorRepresentationEvent
  >(formDescriptionEditorRepresentationMachine);
  const { toast, formDescriptionEditorRepresentation } = value as SchemaValue;
  const { id, formDescriptionEditor, message } = context;

  const { propertySectionsRegistry } = useContext<PropertySectionContextValue>(PropertySectionContext);
  const allWidgets: WidgetDescriptor[] = [...coreWidgets];
  propertySectionsRegistry.getWidgetContributions().forEach((widgetContribution) => {
    allWidgets.push({ name: widgetContribution.name, label: widgetContribution.name, icon: widgetContribution.icon });
  });
  allWidgets.sort((a, b) => (a.label || a.name).localeCompare(b.label || b.name));

  const input: GQLFormDescriptionEditorEventInput = {
    id,
    editingContextId,
    formDescriptionEditorId: representationId,
  };
  const variables: GQLFormDescriptionEditorEventVariables = { input };
  const { error } = useSubscription<GQLFormDescriptionEditorEventSubscription, GQLFormDescriptionEditorEventVariables>(
    gql(formDescriptionEditorEventSubscription(propertySectionsRegistry.getWidgetContributions())),
    {
      variables,
      fetchPolicy: 'no-cache',
      skip: formDescriptionEditorRepresentation !== 'ready',
      onData: ({ data }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: data,
        };
        dispatch(handleDataEvent);
      },
      onComplete: () => {
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
    if (formDescriptionEditorRepresentation === 'loading') {
      const initializeRepresentationEvent: InitializeRepresentationEvent = {
        type: 'INITIALIZE',
      };
      dispatch(initializeRepresentationEvent);
    }
  }, [formDescriptionEditorRepresentation, dispatch]);

  const handleDragStartPage: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('draggedElementId', event.currentTarget.id);
    event.dataTransfer.setData('draggedElementType', 'Page');
  };

  const handleDragStartGroup: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('draggedElementId', event.currentTarget.id);
    event.dataTransfer.setData('draggedElementType', 'Group');
  };

  const handleDragStartWidget: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('draggedElementId', event.currentTarget.id);
    event.dataTransfer.setData('draggedElementType', 'Widget');
  };

  let content: JSX.Element | null = null;

  if (formDescriptionEditorRepresentation === 'ready' && formDescriptionEditor) {
    content = (
      <div className={classes.main}>
        <div className={classes.widgets}>
          <Typography gutterBottom>Pages</Typography>
          <div
            id="Page"
            data-testid="FormDescriptionEditor-Page"
            draggable={!readOnly}
            className={classes.widgetKind}
            onDragStart={readOnly ? noop : handleDragStartPage}>
            <WebIcon />
            <Typography variant="caption" gutterBottom>
              Page
            </Typography>
          </div>
          <Typography gutterBottom>Groups</Typography>
          <div
            id="Group"
            data-testid="FormDescriptionEditor-Group"
            draggable={!readOnly}
            className={classes.widgetKind}
            onDragStart={readOnly ? noop : handleDragStartGroup}>
            <ViewAgendaIcon />
            <Typography variant="caption" gutterBottom>
              Group
            </Typography>
          </div>
          <Typography gutterBottom>Controls</Typography>
          <div
            id="FormElementFor"
            data-testid="FormDescriptionEditor-FormElementFor"
            draggable={!readOnly}
            className={classes.widgetKind}
            onDragStart={readOnly ? noop : handleDragStartWidget}>
            <ForIcon />
            <Typography variant="caption" gutterBottom>
              For
            </Typography>
          </div>
          <div
            id="FormElementIf"
            data-testid="FormDescriptionEditor-FormElementIf"
            draggable={!readOnly}
            className={classes.widgetKind}
            onDragStart={readOnly ? noop : handleDragStartWidget}>
            <IfIcon />
            <Typography variant="caption" gutterBottom>
              If
            </Typography>
          </div>
          <Typography gutterBottom>Widgets</Typography>

          {allWidgets.map((widgetDescriptor) => {
            return (
              <div
                id={widgetDescriptor.name}
                key={widgetDescriptor.name}
                data-testid={`FormDescriptionEditor-${widgetDescriptor.name}`}
                draggable={!readOnly}
                className={classes.widgetKind}
                onDragStart={readOnly ? noop : handleDragStartWidget}>
                {widgetDescriptor.icon}
                <Typography variant="caption" gutterBottom align="center">
                  {widgetDescriptor.label || widgetDescriptor.name}
                </Typography>
              </div>
            );
          })}
        </div>
        <div className={classes.preview}>
          <div className={classes.body}>
            <PageList />
          </div>
        </div>
      </div>
    );
  }

  if (formDescriptionEditorRepresentation === 'complete') {
    content = (
      <div className={classes.main + ' ' + classes.noFormDescriptionEditor}>
        <Typography variant="h5" align="center" data-testid="FormDescriptionEditor-complete-message">
          The form description editor does not exist
        </Typography>
      </div>
    );
  }

  return (
    <FormDescriptionEditorContextProvider
      editingContextId={editingContextId}
      representationId={representationId}
      readOnly={readOnly}
      formDescriptionEditor={formDescriptionEditor}>
      <div className={classes.formDescriptionEditor}>
        <div className={classes.header}>
          <Typography>Form</Typography>
        </div>
        {readOnly ? <div className={classes.disabledOverlay}>{content}</div> : content}
        <Toast
          message={message}
          open={toast === 'visible'}
          onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        />
      </div>
    </FormDescriptionEditorContextProvider>
  );
};
