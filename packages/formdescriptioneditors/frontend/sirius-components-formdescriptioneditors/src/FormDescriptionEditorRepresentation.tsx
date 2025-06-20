/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  useData,
} from '@eclipse-sirius/sirius-components-core';
import { widgetContributionExtensionPoint } from '@eclipse-sirius/sirius-components-forms';
import ViewAgendaIcon from '@mui/icons-material/ViewAgenda';
import WebIcon from '@mui/icons-material/Web';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import React, { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { GQLFormDescriptionEditorRefreshedEventPayload } from './FormDescriptionEditorEventFragment.types';
import {
  FormDescriptionEditorRepresentationState,
  WidgetDescriptor,
} from './FormDescriptionEditorRepresentation.types';
import { PageList } from './PageList';
import { coreWidgets } from './coreWidgets';
import { FormDescriptionEditorContextProvider } from './hooks/FormDescriptionEditorContext';
import { ForIcon } from './icons/ForIcon';
import { IfIcon } from './icons/IfIcon';
import { useFormDescriptionEditorEventSubscription } from './useFormDescriptionEditorEventSubscription';
import { GQLFormDescriptionEditorEventPayload } from './useFormDescriptionEditorEventSubscription.types';

const useFormDescriptionEditorStyles = makeStyles()((theme) => ({
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

const isFormDescriptionEditorRefreshedEventPayload = (
  payload: GQLFormDescriptionEditorEventPayload | null
): payload is GQLFormDescriptionEditorRefreshedEventPayload =>
  payload && payload.__typename === 'FormDescriptionEditorRefreshedEventPayload';

export const FormDescriptionEditorRepresentation = ({
  editingContextId,
  representationId,
  readOnly,
}: RepresentationComponentProps) => {
  const { classes } = useFormDescriptionEditorStyles();
  const noop = () => {};

  const [state, setState] = useState<FormDescriptionEditorRepresentationState>({
    formDescriptionEditor: null,
  });

  const {
    loading,
    payload: formDescriptionEditorEventPayload,
    complete,
  } = useFormDescriptionEditorEventSubscription(editingContextId, representationId);

  useEffect(() => {
    if (isFormDescriptionEditorRefreshedEventPayload(formDescriptionEditorEventPayload)) {
      setState((prevState) => ({
        ...prevState,
        formDescriptionEditor: formDescriptionEditorEventPayload.formDescriptionEditor,
      }));
    }
  }, [formDescriptionEditorEventPayload]);

  const { data: widgetContributions } = useData(widgetContributionExtensionPoint);
  const allWidgets: WidgetDescriptor[] = [...coreWidgets];
  widgetContributions.forEach((widgetContribution) => {
    allWidgets.push({
      name: widgetContribution.name,
      label: widgetContribution.name,
      icon: widgetContribution.icon,
    });
  });
  allWidgets.sort((a, b) => (a.label || a.name).localeCompare(b.label || b.name));

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
  if (state.formDescriptionEditor) {
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

  if (complete) {
    content = (
      <div className={classes.main + ' ' + classes.noFormDescriptionEditor}>
        <Typography variant="h5" align="center" data-testid="FormDescriptionEditor-complete-message">
          The form description editor does not exist
        </Typography>
      </div>
    );
  }

  return (
    <Box>
      {!state.formDescriptionEditor || loading ? (
        <div className={classes.formDescriptionEditor}>
          <RepresentationLoadingIndicator />
        </div>
      ) : null}

      <FormDescriptionEditorContextProvider
        editingContextId={editingContextId}
        representationId={representationId}
        readOnly={readOnly}
        formDescriptionEditor={state.formDescriptionEditor}>
        <div className={classes.formDescriptionEditor}>
          <div className={classes.header}>
            <Typography>Form</Typography>
          </div>
          {readOnly ? <div className={classes.disabledOverlay}>{content}</div> : content}
        </div>
      </FormDescriptionEditorContextProvider>
    </Box>
  );
};
