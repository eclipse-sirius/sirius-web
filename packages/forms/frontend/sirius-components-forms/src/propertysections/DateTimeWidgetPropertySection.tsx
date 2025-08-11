/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import TextField from '@mui/material/TextField';
import { FocusEvent, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLDateTime } from '../form/FormEventFragments.types';
import {
  DataTimeWidgetPropertySectionState,
  DateTimeStyleProps,
  GQLEditDateTimeInput,
  GQLEditDateTimeMutationData,
  GQLEditDateTimeMutationVariables,
} from './DateTimeWidgetPropertySection.types';
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<DateTimeStyleProps>()(
  (theme, { backgroundColor, foregroundColor, italic, bold, gridLayout }) => {
    const {
      gridTemplateColumns,
      gridTemplateRows,
      labelGridColumn,
      labelGridRow,
      widgetGridColumn,
      widgetGridRow,
      gap,
    } = {
      ...gridLayout,
    };
    return {
      inputStyle: {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : undefined,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : undefined,
        fontStyle: italic ? 'italic' : undefined,
        fontWeight: bold ? 'bold' : undefined,
      },
      propertySectionLabel: {
        marginTop: theme.spacing(0.5),
        marginBottom: theme.spacing(0.5),
        gridColumn: labelGridColumn,
        gridRow: labelGridRow,
        display: 'flex',
        flexDirection: 'row',
        gap: theme.spacing(2),
        alignItems: 'center',
      },
      input: {},
      propertySection: {
        display: 'grid',
        gridTemplateColumns,
        gridTemplateRows,
        alignItems: 'center',
        gap: gap ?? '',
      },
      propertySectionWidget: {
        gridColumn: widgetGridColumn,
        gridRow: widgetGridRow,
      },
    };
  }
);

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
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyle(props);

  const [state, setState] = useState<DataTimeWidgetPropertySectionState>({
    editedValue: widget.stringValue,
  });

  useEffect(() => {
    setState((prevState) => ({ ...prevState, editedValue: widget.stringValue }));
  }, [widget.stringValue]);

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
        newValue: convertToUTCDateTimeString(widget.type, state.editedValue),
      };
      const variables: GQLEditDateTimeMutationVariables = {
        input,
      };
      editDateTime({ variables });
    }
  };
  useReporting(mutationEditDateTimeResult, (data: GQLEditDateTimeMutationData) => data?.editDateTime);

  const onKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if ('Enter' === event.key) {
      sendEditedValue();
    }
  };

  const onBlur = () => {
    sendEditedValue();
  };

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    if (value != null) {
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
      }}
      className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={mutationEditDateTimeResult.loading} />
      </div>
      <div className={classes.propertySectionWidget}>
        <TextField
          variant="standard"
          id="datetime"
          disabled={readOnly || widget.readOnly}
          value={convertToLocalDateTimeString(widget.type, state.editedValue)}
          type={type}
          error={widget.diagnostics.length > 0}
          helperText={widget.diagnostics[0]?.message}
          InputProps={
            widget.style
              ? {
                  className: classes.inputStyle,
                }
              : {}
          }
          inputProps={{
            'data-testid': `datetime-${widget.label}`,
            className: classes.input,
          }}
          onChange={onChange}
          onKeyPress={onKeyPress}
        />
      </div>
    </div>
  );
};

const convertToLocalDateTimeString = (dateTimeType: string, dateTimeString: string): string => {
  let formattedDateTimeString = '';
  if (dateTimeString) {
    const dateTime = new Date(dateTimeString);

    // Get the local date and time components
    const year = String(dateTime.getFullYear()).padStart(4, '0');
    const month = String(dateTime.getMonth() + 1).padStart(2, '0');
    const day = String(dateTime.getDate()).padStart(2, '0');
    const hours = String(dateTime.getHours()).padStart(2, '0');
    const minutes = String(dateTime.getMinutes()).padStart(2, '0');

    if (dateTimeType === 'DATE') {
      formattedDateTimeString = `${year}-${month}-${day}`;
    } else if (dateTimeType === 'TIME') {
      formattedDateTimeString = `${hours}:${minutes}`;
    } else {
      formattedDateTimeString = `${year}-${month}-${day}T${hours}:${minutes}`;
    }
  }
  return formattedDateTimeString;
};

const convertToUTCDateTimeString = (dateTimeType: string, dateTimeString: string): string => {
  const dateTime = new Date(dateTimeString);

  if (dateTimeString.length == 0) {
    return '';
  }
  // Get the local date and time components
  const year = String(dateTime.getUTCFullYear()).padStart(4, '0');
  const month = String(dateTime.getUTCMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getUTCDate()).padStart(2, '0');
  const hours = String(dateTime.getUTCHours()).padStart(2, '0');
  const minutes = String(dateTime.getUTCMinutes()).padStart(2, '0');
  const seconds = String(dateTime.getUTCSeconds()).padStart(2, '0');

  if (dateTimeType === 'DATE') {
    return `${year}-${month}-${day}`;
  } else if (dateTimeType === 'TIME') {
    return `${hours}:${minutes}`;
  }

  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}Z`;
};
