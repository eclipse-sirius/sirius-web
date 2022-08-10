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
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditRadioInput,
  GQLEditRadioMutationData,
  GQLEditRadioMutationVariables,
  GQLEditRadioPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
  RadioPropertySectionProps,
} from './RadioPropertySection.types';

export const editRadioMutation = gql`
  mutation editRadio($input: EditRadioInput!) {
    editRadio(input: $input) {
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
export interface StyleProps {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

const useRadioPropertySectionStyles = makeStyles<Theme, StyleProps>(() => ({
  radioGroupRoot: {
    flexDirection: 'row',
  },
  style: {
    color: ({ color }) => (color ? color : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

const isErrorPayload = (payload: GQLEditRadioPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const RadioPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: RadioPropertySectionProps) => {
  const props: StyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useRadioPropertySectionStyles(props);

  const [message, setMessage] = useState(null);

  const [editRadio, { loading, error, data }] = useMutation<GQLEditRadioMutationData>(editRadioMutation);

  const onChange = (event) => {
    const newValue = event.target.value;
    const input: GQLEditRadioInput = {
      id: uuid(),
      editingContextId,
      representationId: formId,
      radioId: widget.id,
      newValue,
    };
    const variables: GQLEditRadioMutationVariables = { input };
    editRadio({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editRadio } = data;
        if (isErrorPayload(editRadio)) {
          setMessage(editRadio.message);
        }
      }
    }
  }, [loading, error, data]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData>(updateWidgetFocusMutation);
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

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => sendUpdateWidgetFocus(false);

  const selectedOption = widget.options.find((option) => option.selected);
  return (
    <FormControl error={widget.diagnostics.length > 0}>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <RadioGroup
        classes={{ root: classes.radioGroupRoot }}
        aria-label={widget.label}
        name={widget.label}
        value={selectedOption ? selectedOption.id : null}
        onChange={onChange}
      >
        {widget.options.map((option) => (
          <FormControlLabel
            value={option.id}
            control={<Radio color="primary" onFocus={onFocus} onBlur={onBlur} data-testid={option.label} />}
            label={
              <Typography
                classes={
                  widget.style
                    ? {
                        root: classes.style,
                      }
                    : {}
                }
              >
                {option.label}
              </Typography>
            }
            key={option.id}
            disabled={readOnly}
          />
        ))}
      </RadioGroup>
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
