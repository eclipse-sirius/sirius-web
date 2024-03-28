/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLSelect } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditSelectMutationData,
  GQLEditSelectPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusMutationData,
  SelectStyleProps,
} from './SelectPropertySection.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<Theme, SelectStyleProps>((theme) => ({
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

export const editSelectMutation = gql`
  mutation editSelect($input: EditSelectInput!) {
    editSelect(input: $input) {
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

const isErrorPayload = (payload: GQLEditSelectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditSelectPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SelectPropertySection: PropertySectionComponent<GQLSelect> = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLSelect>) => {
  const props: SelectStyleProps = {
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

  const [editSelect, { loading, error, data }] = useMutation<GQLEditSelectMutationData>(editSelectMutation);
  const onChange = (event) => {
    const newValue = event.target.value;
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        selectId: widget.id,
        newValue,
      },
    };
    editSelect({ variables });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editSelect } = data;
        if (isErrorPayload(editSelect) || isSuccessPayload(editSelect)) {
          addMessages(editSelect.messages);
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
        value={widget.value || ''}
        onChange={onChange}
        displayEmpty
        onFocus={onFocus}
        onBlur={onBlur}
        fullWidth
        data-testid={widget.label}
        disabled={readOnly || widget.readOnly}
        inputProps={
          widget.style
            ? {
                className: classes.style,
              }
            : {}
        }>
        <MenuItem
          value=""
          classes={
            widget.style
              ? {
                  root: classes.style,
                }
              : {}
          }>
          <em>None</em>
        </MenuItem>
        {widget.options.map((option) => (
          <MenuItem
            value={option.id}
            key={option.id}
            classes={
              widget.style
                ? {
                    root: classes.style,
                  }
                : {}
            }>
            {option.iconURL.length > 0 && (
              <ListItemIcon className={classes.iconRoot}>
                <IconOverlay iconURL={option.iconURL} alt={option.label} />
              </ListItemIcon>
            )}

            {option.label}
          </MenuItem>
        ))}
      </Select>
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    </FormControl>
  );
};
