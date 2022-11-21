/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import Checkbox from '@material-ui/core/Checkbox';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import {
  GQLEditMultiSelectMutationData,
  GQLEditMultiSelectPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusMutationData,
  MultiSelectPropertySectionProps,
  MultiSelectStyleProps,
} from './MultiSelectPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<Theme, MultiSelectStyleProps>(() => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : 'inherit'),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

export const editMultiSelectMutation = gql`
  mutation editMultiSelect($input: EditMultiSelectInput!) {
    editMultiSelect(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEditMultiSelectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const MultiSelectPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: MultiSelectPropertySectionProps) => {
  const props: MultiSelectStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyle(props);

  const [message, setMessage] = useState(null);
  const [isFocused, setFocus] = useState(false);

  const [editMultiSelect, { loading, error, data }] =
    useMutation<GQLEditMultiSelectMutationData>(editMultiSelectMutation);
  const onChange = (event) => {
    const newValues = event.target.value as string[];
    const variables = {
      input: {
        id: uuid(),
        editingContextId,
        representationId: formId,
        selectId: widget.id,
        newValues,
      },
    };
    editMultiSelect({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editMultiSelect } = data;
        if (isErrorPayload(editMultiSelect)) {
          setMessage(editMultiSelect.message);
        }
      }
    }
  }, [loading, error, data]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const variables = {
      input: {
        id: uuid(),
        editingContextId,
        representationId: formId,
        widgetId: widget.id,
        selected,
      },
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          const { message } = updateWidgetFocus;
          setMessage(message);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => {
    if (!isFocused) {
      setFocus(true);
      sendUpdateWidgetFocus(true);
    }
  };

  const onBlur = () => {
    setFocus(false);
    sendUpdateWidgetFocus(false);
  };

  return (
    <FormControl error={widget.diagnostics.length > 0}>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <Select
        value={widget.values}
        onChange={onChange}
        displayEmpty
        onFocus={onFocus}
        onBlur={onBlur}
        fullWidth
        data-testid={widget.label}
        disabled={readOnly}
        renderValue={(selected) =>
          widget.options
            .filter((option) => (selected as string[]).includes(option.id))
            .map((option) => option.label)
            .join(', ')
        }
        multiple
        inputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }>
        {widget.options.map((option) => (
          <MenuItem key={option.id} value={option.id}>
            <Checkbox checked={widget.values.indexOf(option.id) > -1} />
            <ListItemText
              primary={option.label}
              primaryTypographyProps={
                widget.style
                  ? {
                      className: classes.style,
                    }
                  : {}
              }
            />
          </MenuItem>
        ))}
      </Select>
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </FormControl>
  );
};
