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
import { getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Checkbox from '@material-ui/core/Checkbox';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useEffect } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLCheckbox } from '../form/FormEventFragments.types';
import {
  CheckboxStyleProps,
  GQLEditCheckboxInput,
  GQLEditCheckboxMutationData,
  GQLEditCheckboxMutationVariables,
  GQLEditCheckboxPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
} from './CheckboxPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<Theme, CheckboxStyleProps>((theme) => ({
  formControl: {
    alignItems: 'flex-start',
  },
  style: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : theme.palette.primary.light),
    paddingTop: theme.spacing(0.5),
    paddingBottom: theme.spacing(0.5),
  },
  disabled: {},
}));

export const editCheckboxMutation = gql`
  mutation editCheckbox($input: EditCheckboxInput!) {
    editCheckbox(input: $input) {
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

const isErrorPayload = (payload: GQLEditCheckboxPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditCheckboxPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const CheckboxPropertySection: PropertySectionComponent<GQLCheckbox> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLCheckbox>) => {
  const props: CheckboxStyleProps = {
    color: widget.style?.color ?? null,
  };
  const classes = useStyle(props);

  const [editCheckbox, { loading, error, data }] = useMutation<GQLEditCheckboxMutationData>(editCheckboxMutation);
  const onChange = (event) => {
    const newValue = event.target.checked;
    const input: GQLEditCheckboxInput = {
      id: crypto.randomUUID(),
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

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editCheckbox } = data;
        if (isErrorPayload(editCheckbox) || isSuccessPayload(editCheckbox)) {
          addMessages(editCheckbox.messages);
        }
      }
    }
  }, [loading, error, data]);

  return (
    <FormControl classes={{ root: classes.formControl }} error={widget.diagnostics.length > 0}>
      <FormControlLabel
        labelPlacement={widget.style?.labelPlacement ?? 'end'}
        label={<PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />}
        control={
          <Checkbox
            name={widget.label}
            color="default"
            checked={widget.booleanValue}
            onChange={onChange}
            data-testid={widget.label}
            disabled={readOnly || widget.readOnly}
            classes={{ root: classes.style, disabled: classes.disabled }}
          />
        }
      />
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    </FormControl>
  );
};
