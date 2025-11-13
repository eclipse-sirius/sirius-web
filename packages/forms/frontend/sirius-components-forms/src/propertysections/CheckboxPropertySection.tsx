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
import Checkbox from '@mui/material/Checkbox';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
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
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<CheckboxStyleProps>()((theme, { color, gridLayout }) => {
  const { gridTemplateColumns, gridTemplateRows, labelGridColumn, labelGridRow, widgetGridColumn, widgetGridRow, gap } =
    {
      ...gridLayout,
    };

  return {
    checkboxStyle: {
      color: color ? getCSSColor(color, theme) : theme.palette.primary.light,
      padding: '0',
    },
    disabled: {},
    propertySection: {
      display: 'grid',
      gridTemplateColumns: gridTemplateColumns ?? 'min-content 1fr',
      gridTemplateRows,
      alignItems: 'center',
      gap: gap ?? theme.spacing(1),
    },
    propertySectionLabel: {
      gridColumn: labelGridColumn ?? '2/3',
      gridRow: labelGridRow,
      display: 'flex',
      flexDirection: 'row',
      gap: theme.spacing(2),
      alignItems: 'center',
    },
    propertySectionWidget: {
      gridColumn: widgetGridColumn,
      gridRow: widgetGridRow ?? '1/2',
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
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyle(props);
  const { t } = useTranslation('sirius-components-forms', { keyPrefix: 'checkboxPropertySection' });

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
    if (error) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (data) {
      const { editCheckbox } = data;
      if (isErrorPayload(editCheckbox) || isSuccessPayload(editCheckbox)) {
        addMessages(editCheckbox.messages);
      }
    }
  }, [error, data]);

  return (
    <FormControl classes={{ root: classes.propertySection }} error={widget.diagnostics.length > 0}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={loading} />
      </div>
      <div className={classes.propertySectionWidget}>
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
  );
};
