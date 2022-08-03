/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { Textarea, Widget } from 'form/Form.types';
import { PropertySectionLabel } from 'properties/propertysections/PropertySectionLabel';
import {
  GQLEditTextfieldInput,
  GQLEditTextfieldMutationData,
  GQLEditTextfieldMutationVariables,
  GQLEditTextfieldPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
  TextfieldPropertySectionProps,
} from 'properties/propertysections/TextfieldPropertySection.types';
import {
  ChangeValueEvent,
  InitializeEvent,
  SchemaValue,
  ShowToastEvent,
  TextfieldPropertySectionContext,
  TextfieldPropertySectionEvent,
  textfieldPropertySectionMachine,
} from 'properties/propertysections/TextfieldPropertySectionMachine';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import { getTextDecorationLineValue } from './WidgetOperations';

export interface StyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

const useStyle = makeStyles<Theme, StyleProps>(() => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : 'inherit'),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

export const editTextfieldMutation = gql`
  mutation editTextfield($input: EditTextfieldInput!) {
    editTextfield(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isTextarea = (widget: Widget): widget is Textarea => widget.__typename === 'Textarea';
const isErrorPayload = (payload: GQLEditTextfieldPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

/**
 * Defines the content of a Textfield property section.
 * The content is submitted when the focus is lost and when pressing the "Enter" key.
 */
export const TextfieldPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: TextfieldPropertySectionProps) => {
  const props: StyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyle(props);

  const [{ value: schemaValue, context }, dispatch] = useMachine<
    TextfieldPropertySectionContext,
    TextfieldPropertySectionEvent
  >(textfieldPropertySectionMachine);
  const { textfieldPropertySection, toast } = schemaValue as SchemaValue;
  const { value, message } = context;

  useEffect(() => {
    const initializeEvent: InitializeEvent = { type: 'INITIALIZE', value: widget.stringValue };
    dispatch(initializeEvent);
  }, [dispatch, widget.stringValue]);

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    const changeValueEvent: ChangeValueEvent = { type: 'CHANGE_VALUE', value };
    dispatch(changeValueEvent);
  };

  const [editTextfield, { loading: updateTextfieldLoading, data: updateTextfieldData, error: updateTextfieldError }] =
    useMutation<GQLEditTextfieldMutationData, GQLEditTextfieldMutationVariables>(editTextfieldMutation);
  const sendEditedValue = () => {
    if (textfieldPropertySection === 'edited') {
      const input: GQLEditTextfieldInput = {
        id: uuid(),
        editingContextId,
        representationId: formId,
        textfieldId: widget.id,
        newValue: value,
      };
      const variables: GQLEditTextfieldMutationVariables = { input };
      editTextfield({ variables });
    }
  };

  useEffect(() => {
    if (!updateTextfieldLoading) {
      let hasError = false;
      if (updateTextfieldError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);

        hasError = true;
      }
      if (updateTextfieldData) {
        const { editTextfield } = updateTextfieldData;
        if (isErrorPayload(editTextfield)) {
          const { message } = editTextfield;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);

          hasError = true;
        }
      }

      if (hasError) {
        const initializeEvent: InitializeEvent = { type: 'INITIALIZE', value: widget.stringValue };
        dispatch(initializeEvent);
      }
    }
  }, [updateTextfieldLoading, updateTextfieldData, updateTextfieldError, widget, dispatch]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);
  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: uuid(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          const { message } = updateWidgetFocus;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError, dispatch]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
    sendEditedValue();
  };

  const onKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if ('Enter' === event.key && !event.shiftKey) {
      event.preventDefault();
      sendEditedValue();
    }
  };

  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <TextField
        name={widget.label}
        placeholder={widget.label}
        value={value}
        margin="dense"
        multiline={isTextarea(widget)}
        maxRows={isTextarea(widget) ? 4 : 1}
        fullWidth
        onChange={onChange}
        onBlur={onBlur}
        onFocus={onFocus}
        onKeyPress={onKeyPress}
        data-testid={widget.label}
        disabled={readOnly}
        error={widget.diagnostics.length > 0}
        helperText={widget.diagnostics[0]?.message}
        InputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }
      />
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' })}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => dispatch({ type: 'HIDE_TOAST' })}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
