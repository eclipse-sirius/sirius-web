/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import { IconOverlay, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Autocomplete from '@mui/material/Autocomplete';
import Chip from '@mui/material/Chip';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import { useTheme } from '@mui/material/styles';
import { HTMLAttributes, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { GQLReferenceValue, GQLReferenceWidgetStyle } from '../ReferenceWidgetFragment.types';
import {
  GQLFormDescription,
  GQLGetReferenceValueOptionsQueryData,
  GQLGetReferenceValueOptionsQueryVariables,
  GQLRepresentationDescription,
  ValuedReferenceAutocompleteProps,
  ValuedReferenceAutocompleteState,
} from './ValuedReferenceAutocomplete.types';

const useStyles = makeStyles<GQLReferenceWidgetStyle>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    optionLabel: {
      paddingLeft: theme.spacing(0.5),
    },
    referenceValueStyle: {
      color: color ? getCSSColor(color, theme) : undefined,
      fontSize: fontSize ? fontSize : undefined,
      fontStyle: italic ? 'italic' : 'unset',
      fontWeight: bold ? 'bold' : 'unset',
      textDecorationLine: getTextDecorationLineValue(underline ?? null, strikeThrough ?? null),
    },
    endAdornmentButton: {
      position: 'absolute',
      display: 'flex',
      right: theme.spacing(2.5),
      '& > *': {
        padding: 0,
      },
    },
  })
);

const getReferenceValueOptionsQuery = gql`
  query getReferenceValueOptions($editingContextId: ID!, $representationId: ID!, $referenceWidgetId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on FormDescription {
              referenceValueOptions(referenceWidgetId: $referenceWidgetId) {
                id
                label
                kind
                iconURL
              }
            }
          }
        }
      }
    }
  }
`;

const isFormDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLFormDescription => representationDescription.__typename === 'FormDescription';

export const ValuedReferenceAutocomplete = ({
  editingContextId,
  formId,
  widget,
  readOnly,
  onDragEnter,
  onDragOver,
  onDrop,
  onMoreClick,
  onCreateClick,
  optionClickHandler,
  clearReference,
  removeReferenceValue,
  addReferenceValues,
  setReferenceValue,
}: ValuedReferenceAutocompleteProps) => {
  const props: GQLReferenceWidgetStyle = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyles(props);
  const theme = useTheme();

  const { addErrorMessage } = useMultiToast();
  const [state, setState] = useState<ValuedReferenceAutocompleteState>({ open: false, options: null });
  const loading = state.open && state.options === null;

  const [
    getReferenceValueOptions,
    {
      loading: childReferenceValueOptionsLoading,
      data: childReferenceValueOptionsData,
      error: childReferenceValueOptionsError,
    },
  ] = useLazyQuery<GQLGetReferenceValueOptionsQueryData, GQLGetReferenceValueOptionsQueryVariables>(
    getReferenceValueOptionsQuery
  );

  useEffect(() => {
    if (!childReferenceValueOptionsLoading) {
      if (childReferenceValueOptionsError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (childReferenceValueOptionsData) {
        const representationDescription: GQLRepresentationDescription =
          childReferenceValueOptionsData.viewer.editingContext.representation.description;
        if (isFormDescription(representationDescription)) {
          setState((prevState) => {
            return {
              ...prevState,
              options: representationDescription.referenceValueOptions,
            };
          });
        }
      }
    }
  }, [childReferenceValueOptionsLoading, childReferenceValueOptionsData, childReferenceValueOptionsError]);

  useEffect(() => {
    if (loading) {
      getReferenceValueOptions({
        variables: {
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
        },
      });
    }
  }, [loading]);

  const handleRemoveReferenceValue = (updatedValues: GQLReferenceValue[]) => {
    widget.referenceValues.forEach((value) => {
      if (!updatedValues.find((updateValue) => updateValue.id === value.id)) {
        removeReferenceValue(value.id); // this should only be called once, since we can remove only one chip at a time
      }
    });
  };

  const getOnlyNewValueIds = (updatedValues: GQLReferenceValue[]): string[] => {
    if (widget.referenceValues?.length > 0) {
      return updatedValues
        .filter((updatedValue) => widget.referenceValues.some((value) => value.id !== updatedValue.id))
        .map((value) => value.id);
    } else {
      return updatedValues.map((value) => value.id);
    }
  };

  const handleAutocompleteChange = (_event, updatedValues: GQLReferenceValue[], reason: string) => {
    if (reason === 'removeOption') {
      handleRemoveReferenceValue(updatedValues);
    } else {
      const newValueIds = getOnlyNewValueIds(updatedValues);
      if (widget.reference.manyValued) {
        addReferenceValues(newValueIds);
      } else {
        setReferenceValue(newValueIds[0] ?? null);
      }
    }
  };

  useEffect(() => {
    if (!state.open) {
      setState((prevState) => {
        return {
          ...prevState,
          options: null,
        };
      });
    }
  }, [widget]);

  let placeholder: string;
  if (widget.reference.manyValued) {
    placeholder = 'Values';
  } else {
    placeholder = widget.referenceValues.length > 0 ? '' : 'Value';
  }
  return (
    <Autocomplete
      data-testid={widget.label}
      multiple
      filterSelectedOptions
      disabled={readOnly || widget.readOnly}
      open={state.open}
      onOpen={() =>
        setState((prevState) => {
          return {
            ...prevState,
            open: true,
          };
        })
      }
      onClose={() =>
        setState((prevState) => {
          return {
            ...prevState,
            open: false,
          };
        })
      }
      loading={loading}
      options={state.options || []}
      getOptionKey={(option: GQLReferenceValue) => option.id}
      getOptionLabel={(option: GQLReferenceValue) => option.label}
      value={widget.referenceValues}
      isOptionEqualToValue={(option, value) => option.id === value.id}
      onChange={handleAutocompleteChange}
      renderOption={(props: HTMLAttributes<HTMLLIElement>, option: GQLReferenceValue) => (
        <li {...props} key={option.id}>
          <IconOverlay iconURL={option.iconURL} alt={option.kind} />
          <span className={classes.optionLabel} data-testid={`option-${option.label}`}>
            {option.label}
          </span>
        </li>
      )}
      disableClearable
      renderTags={(value, getTagProps) =>
        value.map((option, index) => {
          const { key, onDelete, ...tagProps } = getTagProps({ index });
          return (
            <Chip
              key={key}
              classes={{ label: classes.referenceValueStyle }}
              label={option.label}
              data-testid={`reference-value-${option.label}`}
              icon={
                <div>
                  <IconOverlay iconURL={option.iconURL} alt={option.kind} />
                </div>
              }
              clickable
              onClick={() => optionClickHandler(option)}
              {...tagProps}
              disabled={false}
              onDelete={readOnly || widget.readOnly ? undefined : onDelete}
            />
          );
        })
      }
      renderInput={(params) => (
        <TextField
          {...params}
          variant="standard"
          placeholder={placeholder}
          error={widget.diagnostics.length > 0}
          helperText={widget.diagnostics[0]?.message}
          InputProps={{
            ...params.InputProps,
            style: { paddingRight: theme.spacing(10) }, // Offset required to prevent values from being displayed below the buttons
            endAdornment: (
              <>
                {params.InputProps.endAdornment}
                <InputAdornment position="end" className={classes.endAdornmentButton}>
                  <IconButton
                    aria-label="edit"
                    size="small"
                    title="Edit"
                    disabled={readOnly || widget.readOnly}
                    data-testid={`${widget.label}-more`}
                    onClick={onMoreClick}>
                    <MoreHorizIcon />
                  </IconButton>
                  <IconButton
                    aria-label="add"
                    size="small"
                    title="Create an object"
                    disabled={readOnly || widget.readOnly}
                    data-testid={`${widget.label}-add`}
                    onClick={onCreateClick}>
                    <AddIcon />
                  </IconButton>
                  <IconButton
                    aria-label="clear"
                    size="small"
                    title="Clear"
                    disabled={readOnly || widget.readOnly}
                    data-testid={`${widget.label}-clear`}
                    onClick={clearReference}>
                    <DeleteIcon />
                  </IconButton>
                </InputAdornment>
              </>
            ),
          }}
        />
      )}
      onDragEnter={onDragEnter}
      onDragOver={onDragOver}
      onDrop={onDrop}
    />
  );
};
