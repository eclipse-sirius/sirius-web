/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import FormControl from '@mui/material/FormControl';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormHelperText from '@mui/material/FormHelperText';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import Typography from '@mui/material/Typography';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLRadio } from '../form/FormEventFragments.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditRadioInput,
  GQLEditRadioMutationData,
  GQLEditRadioMutationVariables,
  GQLEditRadioPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  RadioStyleProps,
} from './RadioPropertySection.types';

export const editRadioMutation = gql`
  mutation editRadio($input: EditRadioInput!) {
    editRadio(input: $input) {
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

const useRadioPropertySectionStyles = makeStyles<RadioStyleProps>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    radioGroupRoot: {
      flexDirection: 'row',
    },
    style: {
      color: color ? getCSSColor(color, theme) : undefined,
      fontSize: fontSize ? fontSize : undefined,
      fontStyle: italic ? 'italic' : undefined,
      fontWeight: bold ? 'bold' : undefined,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
    },
    radio: {
      paddingBottom: theme.spacing(0.5),
      paddingTop: theme.spacing(0.5),
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      gap: theme.spacing(2),
      alignItems: 'center',
    },
  })
);

const isErrorPayload = (payload: GQLEditRadioPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditRadioPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const RadioPropertySection: PropertySectionComponent<GQLRadio> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLRadio>) => {
  const props: RadioStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useRadioPropertySectionStyles(props);

  const [editRadio, { loading, error, data }] = useMutation<GQLEditRadioMutationData>(editRadioMutation);

  const onChange = (event) => {
    const newValue = event.target.value;
    const input: GQLEditRadioInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      radioId: widget.id,
      newValue,
    };
    const variables: GQLEditRadioMutationVariables = { input };
    editRadio({ variables });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { editRadio } = data;
      if (isErrorPayload(editRadio) || isSuccessPayload(editRadio)) {
        addMessages(editRadio.messages);
      }
    }
  }, [error, data]);

  const selectedOption = widget.options.find((option) => option.selected);
  return (
    <FormControl error={widget.diagnostics.length > 0}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={loading} />
      </div>
      <RadioGroup
        classes={{ root: classes.radioGroupRoot }}
        aria-label={widget.label}
        name={widget.label}
        value={selectedOption ? selectedOption.id : null}
        onChange={onChange}>
        {widget.options.map((option) => (
          <FormControlLabel
            value={option.id}
            control={<Radio className={classes.radio} color="primary" data-testid={option.label} />}
            label={
              <Typography
                classes={
                  widget.style
                    ? {
                        root: classes.style,
                      }
                    : {}
                }>
                {option.label}
              </Typography>
            }
            key={option.id}
            disabled={readOnly || widget.readOnly}
          />
        ))}
      </RadioGroup>
      <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    </FormControl>
  );
};
