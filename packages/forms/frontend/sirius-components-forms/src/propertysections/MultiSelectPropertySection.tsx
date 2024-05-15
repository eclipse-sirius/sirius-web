/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import Checkbox from '@mui/material/Checkbox';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLMultiSelect } from '../form/FormEventFragments.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { LoadingIndicator } from './LoadingIndicator';
import {
  GQLEditMultiSelectMutationData,
  GQLEditMultiSelectPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  MultiSelectStyleProps,
} from './MultiSelectPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<MultiSelectStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough, gridLayout }) => {
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
      style: {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : undefined,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : undefined,
        fontSize: fontSize ? fontSize : undefined,
        fontStyle: italic ? 'italic' : undefined,
        fontWeight: bold ? 'bold' : undefined,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
        paddingTop: theme.spacing(0.5),
        paddingBottom: theme.spacing(0.5),
      },
      iconRoot: {
        minWidth: theme.spacing(1),
      },
      propertySection: {
        display: 'grid',
        gridTemplateColumns,
        gridTemplateRows,
        alignItems: 'center',
        gap: gap ?? '',
      },
      propertySectionLabel: {
        gridColumn: labelGridColumn,
        gridRow: labelGridRow,
        display: 'flex',
        flexDirection: 'row',
        gap: theme.spacing(2),
        alignItems: 'center',
      },
      propertySectionWidget: {
        gridColumn: widgetGridColumn,
        gridRow: widgetGridRow,
      },
    };
  }
);

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

const isErrorPayload = (payload: GQLEditMultiSelectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLEditMultiSelectPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const MultiSelectPropertySection: PropertySectionComponent<GQLMultiSelect> = ({
  editingContextId,
  formId,
  widget,
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
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyle(props);
  const { t } = useTranslation('sirius-components-forms');

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
    if (error) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (data) {
      const { editMultiSelect } = data;
      if (isErrorPayload(editMultiSelect) || isSuccessPayload(editMultiSelect)) {
        addMessages(editMultiSelect.messages);
      }
    }
  }, [error, data]);

  return (
    <FormControl error={widget.diagnostics.length > 0} className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={loading} />
      </div>
      <div className={classes.propertySectionWidget}>
        <Select
          variant="standard"
          value={widget.values}
          onChange={onChange}
          displayEmpty
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
                  <IconOverlay iconURLs={option.iconURL} alt={option.label} />
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
      </div>
    </FormControl>
  );
};
