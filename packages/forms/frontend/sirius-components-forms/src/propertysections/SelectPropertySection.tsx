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
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLSelect } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditSelectMutationData,
  GQLEditSelectPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  SelectStyleProps,
} from './SelectPropertySection.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<SelectStyleProps>()(
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
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : null,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : null,
        fontSize: fontSize ? fontSize : null,
        fontStyle: italic ? 'italic' : null,
        fontWeight: bold ? 'bold' : null,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      },
      iconRoot: {
        minWidth: theme.spacing(3),
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
        alignItems: 'center',
      },
      propertySectionWidget: {
        gridColumn: widgetGridColumn,
        gridRow: widgetGridRow,
      },
    };
  }
);

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

const isErrorPayload = (payload: GQLEditSelectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditSelectPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SelectPropertySection: PropertySectionComponent<GQLSelect> = ({
  editingContextId,
  formId,
  widget,
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
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyle(props);

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

  return (
    <FormControl error={widget.diagnostics.length > 0} className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      </div>
      <div className={classes.propertySectionWidget}>
        <Select
          variant="standard"
          value={widget.value || ''}
          onChange={onChange}
          displayEmpty
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
      </div>
    </FormControl>
  );
};
