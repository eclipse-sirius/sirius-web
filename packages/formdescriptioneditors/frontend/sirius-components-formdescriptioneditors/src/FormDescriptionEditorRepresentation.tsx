/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { PropertySectionContext } from '@eclipse-sirius/sirius-components-forms';
import Avatar from '@material-ui/core/Avatar';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import ArrowDropDownCircleIcon from '@material-ui/icons/ArrowDropDownCircle';
import BarChartIcon from '@material-ui/icons/BarChart';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import ImageIcon from '@material-ui/icons/Image';
import LabelOutlinedIcon from '@material-ui/icons/LabelOutlined';
import LinkIcon from '@material-ui/icons/Link';
import PieChartIcon from '@material-ui/icons/PieChart';
import RadioButtonCheckedIcon from '@material-ui/icons/RadioButtonChecked';
import TextFieldsIcon from '@material-ui/icons/TextFields';
import TextFormatIcon from '@material-ui/icons/TextFormat';
import ViewAgendaIcon from '@material-ui/icons/ViewAgenda';
import ViewColumnIcon from '@material-ui/icons/ViewColumn';
import WebIcon from '@material-ui/icons/Web';
import { useMachine } from '@xstate/react';
import React, { useContext, useEffect } from 'react';
import { formDescriptionEditorEventSubscription } from './FormDescriptionEditorEventFragment';
import {
  GQLFormDescriptionEditorEventInput,
  GQLFormDescriptionEditorEventSubscription,
  GQLFormDescriptionEditorEventVariables,
} from './FormDescriptionEditorEventFragment.types';
import {
  FormDescriptionEditorRepresentationContext,
  FormDescriptionEditorRepresentationEvent,
  formDescriptionEditorRepresentationMachine,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  InitializeRepresentationEvent,
  SchemaValue,
  ShowToastEvent,
} from './FormDescriptionEditorRepresentationMachine';
import { Button } from './icons/Button';
import { PageList } from './PageList';

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
  selection,
  setSelection,
}: RepresentationComponentProps) => {
  const classes = useFormDescriptionEditorStyles();

  const [{ value, context }, dispatch] = useMachine<
    FormDescriptionEditorRepresentationContext,
    FormDescriptionEditorRepresentationEvent
  >(formDescriptionEditorRepresentationMachine);
  const { toast, formDescriptionEditorRepresentation } = value as SchemaValue;
  const { id, formDescriptionEditor, subscribers, message } = context;

  const input: GQLFormDescriptionEditorEventInput = {
    id,
    editingContextId,
    formDescriptionEditorId: representationId,
  };
  const variables: GQLFormDescriptionEditorEventVariables = { input };
  const { propertySectionsRegistry } = useContext(PropertySectionContext);
  const { error } = useSubscription<GQLFormDescriptionEditorEventSubscription, GQLFormDescriptionEditorEventVariables>(
    gql(formDescriptionEditorEventSubscription(propertySectionsRegistry.getWidgetContributions())),
    {
      variables,
      fetchPolicy: 'no-cache',
      skip: formDescriptionEditorRepresentation !== 'ready',
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
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartPage}>
            <WebIcon />
            <Typography variant="caption" gutterBottom>
              Page
            </Typography>
          </div>
          <Typography gutterBottom>Groups</Typography>
          <div
            id="Group"
            data-testid="FormDescriptionEditor-Group"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartGroup}>
            <ViewAgendaIcon />
            <Typography variant="caption" gutterBottom>
              Group
            </Typography>
          </div>
          <Typography gutterBottom>Widgets</Typography>
          <div
            id="BarChart"
            data-testid="FormDescriptionEditor-BarChart"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <BarChartIcon />
            <Typography variant="caption" gutterBottom>
              BarChart
            </Typography>
          </div>
          <div
            id="Button"
            data-testid="FormDescriptionEditor-Button"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <Button width={'24px'} height={'24px'} color={'secondary'} />
            <Typography variant="caption" gutterBottom>
              Button
            </Typography>
          </div>
          <div
            id="Checkbox"
            data-testid="FormDescriptionEditor-Checkbox"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <CheckBoxIcon />
            <Typography variant="caption" gutterBottom>
              Checkbox
            </Typography>
          </div>
          <div
            id="FlexboxContainer"
            data-testid="FormDescriptionEditor-FlexboxContainer"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <ViewColumnIcon width={'24px'} height={'24px'} color={'secondary'} />
            <Typography variant="caption" gutterBottom align="center">
              Flexbox Container
            </Typography>
          </div>
          <div
            id="Image"
            data-testid="FormDescriptionEditor-Image"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <ImageIcon width={'24px'} height={'24px'} color={'secondary'} />
            <Typography variant="caption" gutterBottom>
              Image
            </Typography>
          </div>
          <div
            id="Label"
            data-testid="FormDescriptionEditor-Label"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <LabelOutlinedIcon />
            <Typography variant="caption" gutterBottom>
              Label
            </Typography>
          </div>
          <div
            id="Link"
            data-testid="FormDescriptionEditor-Link"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <LinkIcon />
            <Typography variant="caption" gutterBottom>
              Link
            </Typography>
          </div>
          <div
            id="List"
            data-testid="FormDescriptionEditor-List"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <FormatListBulletedIcon />
            <Typography variant="caption" gutterBottom>
              List
            </Typography>
          </div>
          <div
            id="MultiSelect"
            data-testid="FormDescriptionEditor-MultiSelect"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <ArrowDropDownCircleIcon />
            <Typography variant="caption" gutterBottom>
              MultiSelect
            </Typography>
          </div>
          <div
            id="PieChart"
            data-testid="FormDescriptionEditor-PieChart"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <PieChartIcon />
            <Typography variant="caption" gutterBottom>
              PieChart
            </Typography>
          </div>
          <div
            id="Radio"
            data-testid="FormDescriptionEditor-Radio"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <RadioButtonCheckedIcon />
            <Typography variant="caption" gutterBottom>
              Radio
            </Typography>
          </div>
          <div
            id="RichText"
            data-testid="FormDescriptionEditor-RichText"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <TextFormatIcon />
            <Typography variant="caption" gutterBottom>
              RichText
            </Typography>
          </div>
          <div
            id="Select"
            data-testid="FormDescriptionEditor-Select"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <ArrowDropDownCircleIcon />
            <Typography variant="caption" gutterBottom>
              Select
            </Typography>
          </div>
          <div
            id="TextArea"
            data-testid="FormDescriptionEditor-TextArea"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <TextFieldsIcon />
            <Typography variant="caption" gutterBottom>
              Textarea
            </Typography>
          </div>
          <div
            id="Textfield"
            data-testid="FormDescriptionEditor-Textfield"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStartWidget}>
            <TextFieldsIcon />
            <Typography variant="caption" gutterBottom>
              Textfield
            </Typography>
          </div>
          {propertySectionsRegistry.getWidgetContributions().map((customWidget) => {
            return (
              <div
                id={customWidget.name}
                key={customWidget.name}
                data-testid={`FormDescriptionEditor-${customWidget.name}`}
                draggable="true"
                className={classes.widgetKind}
                onDragStart={handleDragStartWidget}>
                {customWidget.icon}
                <Typography variant="caption" gutterBottom>
                  {customWidget.name}
                </Typography>
              </div>
            );
          })}
        </div>
        <div className={classes.preview}>
          <div className={classes.body}>
            <PageList
              editingContextId={editingContextId}
              representationId={representationId}
              formDescriptionEditor={formDescriptionEditor}
              selection={selection}
              setSelection={setSelection}
            />
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
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </div>
  );
};
