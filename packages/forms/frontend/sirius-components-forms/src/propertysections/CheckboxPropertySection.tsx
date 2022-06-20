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
import Checkbox from '@material-ui/core/Checkbox';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@material-ui/core/FormGroup';
import FormHelperText from '@material-ui/core/FormHelperText';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import {
  CheckboxPropertySectionProps,
  GQLEditCheckboxInput,
  GQLEditCheckboxMutationData,
  GQLEditCheckboxMutationVariables,
  GQLEditCheckboxPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
} from './CheckboxPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

export interface StyleProps {
  color: string | null;
}

const useStyle = makeStyles<Theme, StyleProps>((theme) => ({
  style: {
    color: ({ color }) => (color ? color : theme.palette.primary.light),
    '&.Mui-checked': {
      color: ({ color }) => (color ? color : theme.palette.primary.light),
    },
  },
}));

export const editCheckboxMutation = gql`
  mutation editCheckbox($input: EditCheckboxInput!) {
    editCheckbox(input: $input) {
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

const isErrorPayload = (payload: GQLEditCheckboxPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const CheckboxPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: CheckboxPropertySectionProps) => {
  const props: StyleProps = {
    color: widget.style?.color ?? null,
  };
  const classes = useStyle(props);

  const [message, setMessage] = useState(null);

  const [editCheckbox, { loading, error, data }] = useMutation<GQLEditCheckboxMutationData>(editCheckboxMutation);
  const onChange = (event) => {
    const newValue = event.target.checked;
    const input: GQLEditCheckboxInput = {
      id: uuid(),
      editingContextId,
      representationId: formId,
      checkboxId: widget.id,
      newValue,
    };
    const variables: GQLEditCheckboxMutationVariables = {
      input,
    };
    editCheckbox({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editCheckbox } = data;
        if (isErrorPayload(editCheckbox)) {
          setMessage(editCheckbox.message);
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

  return (
    <FormControl error={widget.diagnostics.length > 0}>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <FormGroup row>
        <Checkbox
          name={widget.label}
          color="default"
          checked={widget.booleanValue}
          onChange={onChange}
          onFocus={onFocus}
          onBlur={onBlur}
          data-testid={widget.label}
          disabled={readOnly}
          classes={{ root: classes.style }}
        />
      </FormGroup>
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
