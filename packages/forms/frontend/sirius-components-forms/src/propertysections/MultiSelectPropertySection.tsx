/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { IconOverlay, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Checkbox from '@material-ui/core/Checkbox';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLMultiSelect } from '../form/FormEventFragments.types';
import {
  GQLEditMultiSelectMutationData,
  GQLEditMultiSelectPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusPayload,
  MultiSelectStyleProps,
} from './MultiSelectPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<Theme, MultiSelectStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? getCSSColor(backgroundColor, theme) : null),
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  iconRoot: {
    minWidth: theme.spacing(3),
  },
}));

export const editMultiSelectMutation = gql`
  mutation editMultiSelect($input: EditMultiSelectInput!) {
    editMultiSelect(input: $input) {
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

const updateWidgetFocusMutation = gql`
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

const isErrorPayload = (payload: GQLEditMultiSelectPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLEditMultiSelectPayload | GQLUpdateWidgetFocusPayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

export const MultiSelectPropertySection: PropertySectionComponent<GQLMultiSelect> = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLMultiSelect>) => {
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

  const [isFocused, setFocus] = useState(false);

  const [editMultiSelect, { loading, error, data }] =
    useMutation<GQLEditMultiSelectMutationData>(editMultiSelectMutation);
  const onChange = (event) => {
    const newValues = event.target.value as string[];
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        selectId: widget.id,
        newValues,
      },
    };
    editMultiSelect({ variables });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editMultiSelect } = data;
        if (isErrorPayload(editMultiSelect) || isSuccessPayload(editMultiSelect)) {
          addMessages(editMultiSelect.messages);
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
        id: crypto.randomUUID(),
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
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
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
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
      />
      <Select
        value={widget.values}
        onChange={onChange}
        displayEmpty
        onFocus={onFocus}
        onBlur={onBlur}
        fullWidth
        data-testid={widget.label}
        disabled={readOnly || widget.readOnly}
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
            {option.iconURL.length > 0 && (
              <ListItemIcon className={classes.iconRoot}>
                <IconOverlay iconURL={option.iconURL} alt={option.label} />
              </ListItemIcon>
            )}
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
    </FormControl>
  );
};
