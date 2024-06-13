/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { getCSSColor, useReporting } from '@eclipse-sirius/sirius-components-core';
import TextField from '@material-ui/core/TextField';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { FocusEvent, useEffect, useState } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLDateTime } from '../form/FormEventFragments.types';
import {
  DataTimeWidgetPropertySectionState,
  DateTimeStyleProps,
  GQLEditDateTimeInput,
  GQLEditDateTimeMutationData,
  GQLEditDateTimeMutationVariables,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
} from './DateTimeWidgetPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<Theme, DateTimeStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? getCSSColor(backgroundColor, theme) : null),
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
  },
  textfield: {
    marginTop: theme.spacing(0.5),
    marginBottom: theme.spacing(0.5),
  },
}));

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const editDateTimeMutation = gql`
  mutation editDateTime($input: EditDateTimeInput!) {
    editDateTime(input: $input) {
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

export const DateTimeWidgetPropertySection: PropertySectionComponent<GQLDateTime> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLDateTime>) => {
  const props: DateTimeStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
  };
  const classes = useStyle(props);

  const [state, setState] = useState<DataTimeWidgetPropertySectionState>({
    editedValue: widget.stringValue,
  });

  useEffect(() => {
    setState((prevState) => ({ ...prevState, editedValue: widget.stringValue }));
  }, [widget.stringValue]);

  const [updateWidgetFocus, mutationUpdateWidgetFocusResult] = useMutation<
    GQLUpdateWidgetFocusMutationData,
    GQLUpdateWidgetFocusMutationVariables
  >(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
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
  useReporting(mutationUpdateWidgetFocusResult, (data: GQLUpdateWidgetFocusMutationData) => data?.updateWidgetFocus);

  const [editDateTime, mutationEditDateTimeResult] = useMutation<
    GQLEditDateTimeMutationData,
    GQLEditDateTimeMutationVariables
  >(editDateTimeMutation);

  const sendEditedValue = () => {
    if (widget.stringValue !== state.editedValue) {
      const input: GQLEditDateTimeInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        widgetId: widget.id,
        newValue: convertToUTCDateTimeString(state.editedValue),
      };
      const variables: GQLEditDateTimeMutationVariables = {
        input,
      };
      editDateTime({ variables });
    }
  };
  useReporting(mutationEditDateTimeResult, (data: GQLEditDateTimeMutationData) => data?.editDateTime);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
    sendEditedValue();
  };

  const onKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if ('Enter' === event.key) {
      sendEditedValue();
    }
  };

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    if (value?.length > 0) {
      setState((prevState) => ({ ...prevState, editedValue: value }));
    }
  };

  let type = 'datetime-local';
  if (widget.type === 'DATE') {
    type = 'date';
  } else if (widget.type === 'TIME') {
    type = 'time';
  }

  return (
    <div
      onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
        if (!event.currentTarget.contains(event.relatedTarget)) {
          onBlur();
        }
      }}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <TextField
        id="datetime"
        disabled={readOnly || widget.readOnly}
        value={convertToLocalDateTimeString(widget.type, state.editedValue)}
        type={type}
        className={classes.textfield}
        InputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }
        inputProps={{
          'data-testid': `datetime-${widget.label}`,
          className: classes.input,
        }}
        onChange={onChange}
        onFocus={onFocus}
        onKeyPress={onKeyPress}
      />
    </div>
  );
};

const convertToLocalDateTimeString = (dateTimeType: string, dateTimeString: string): string => {
  const dateTime = new Date(dateTimeString);

  // Get the local date and time components
  const year = String(dateTime.getFullYear()).padStart(4, '0');
  const month = String(dateTime.getMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getDate()).padStart(2, '0');
  const hours = String(dateTime.getHours()).padStart(2, '0');
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');

  if (dateTimeType === 'DATE') {
    return `${year}-${month}-${day}`;
  } else if (dateTimeType === 'TIME') {
    return `${hours}:${minutes}`;
  }
  return `${year}-${month}-${day}T${hours}:${minutes}`;
};

const convertToUTCDateTimeString = (dateTimeString: string): string => {
  const dateTime = new Date(dateTimeString);

  // Get the local date and time components
  const year = String(dateTime.getUTCFullYear()).padStart(4, '0');
  const month = String(dateTime.getUTCMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getUTCDate()).padStart(2, '0');
  const hours = String(dateTime.getUTCHours()).padStart(2, '0');
  const minutes = String(dateTime.getUTCMinutes()).padStart(2, '0');
  const seconds = String(dateTime.getUTCSeconds()).padStart(2, '0');

  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}Z`;
};
