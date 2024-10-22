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
import Checkbox from '@mui/material/Checkbox';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
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
import { getFlexProperties } from './FlexboxHelper';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<CheckboxStyleProps>()((theme, { color, flexProps }) => {
  const { flexDirectionCSS, alignItems, gap, labelFlex, valueFlex } = getFlexProperties(flexProps, 'row-reverse');

  return {
    checkboxStyle: {
      color: color ? getCSSColor(color, theme) : theme.palette.primary.light,
      padding: '0',
    },
    disabled: {},
    propertySection: {
      display: 'flex',
      flexDirection: flexDirectionCSS,
      alignItems,
      gap: gap ?? theme.spacing(0.5),
    },
    propertySectionValue: {
      flex: valueFlex ?? '',
    },
    propertySectionLabel: {
      flex: labelFlex ?? '1 1 auto',
    },
  };
});

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
    flexProps: widget.style?.widgetFlexboxLayout ?? null,
  };
  const { classes } = useStyle(props);

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
    //   <div
    //   onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
    //     if (!event.currentTarget.contains(event.relatedTarget)) {
    //       onBlur();
    //     }
    //   }}
    //   className={classes.propertysection}>
    //   <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
    //   <TextField
    //     variant="standard"
    //     id="datetime"
    //     disabled={readOnly || widget.readOnly}
    //     value={convertToLocalDateTimeString(widget.type, state.editedValue)}
    //     type={type}
    //     className={classes.textfield}
    //     InputProps={
    //       widget.style
    //         ? {
    //             className: classes.style,
    //           }
    //         : {}
    //     }
    //     inputProps={{
    //       'data-testid': `datetime-${widget.label}`,
    //       className: classes.input,
    //     }}
    //     onChange={onChange}
    //     onKeyPress={onKeyPress}
    //   />
    // </div>
    <FormControl classes={{ root: classes.propertySection }} error={widget.diagnostics.length > 0}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        {/* <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText> */}
      </div>
      <div className={classes.propertySectionValue}>
        <Checkbox
          name={widget.label}
          color="default"
          checked={widget.booleanValue}
          onChange={onChange}
          data-testid={widget.label}
          disabled={readOnly || widget.readOnly}
          classes={{ root: classes.checkboxStyle, disabled: classes.disabled }}
        />
        {widget.diagnostics[0]?.message ? <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText> : null}
      </div>
    </FormControl>
    // <FormControl classes={{ root: classes.formControl }} error={widget.diagnostics.length > 0}>
    //   <FormControlLabel
    //     labelPlacement={widget.style?.labelPlacement ?? 'end'}
    //     label={<PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />}
    //     control={
    //       <Checkbox
    //         name={widget.label}
    //         color="default"
    //         checked={widget.booleanValue}
    //         onChange={onChange}
    //         data-testid={widget.label}
    //         disabled={readOnly || widget.readOnly}
    //         classes={{ root: classes.style, disabled: classes.disabled }}
    //       />
    //     }
    //   />
    //   <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText>
    // </FormControl>
  );
};
