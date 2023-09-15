/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { getCSSColor, ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import Chip from '@material-ui/core/Chip';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import { makeStyles, Theme, useTheme } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { useContext, useEffect, useState } from 'react';
import { GQLReferenceValue, GQLReferenceWidgetStyle } from '../ReferenceWidgetFragment.types';
import {
  GQLFormDescription,
  GQLGetReferenceValueOptionsQueryData,
  GQLGetReferenceValueOptionsQueryVariables,
  GQLRepresentationDescription,
  ValuedReferenceAutocompleteProps,
  ValuedReferenceAutocompleteState,
} from './ValuedReferenceAutocomplete.types';

const useStyles = makeStyles<Theme, GQLReferenceWidgetStyle>((theme) => ({
  optionLabel: {
    paddingLeft: theme.spacing(0.5),
  },
  referenceValueStyle: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
  endAdornmentButton: {
    position: 'absolute',
    display: 'flex',
    right: theme.spacing(2.5),
    '& > *': {
      padding: 0,
    },
  },
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
}));

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
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const props: GQLReferenceWidgetStyle = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyles(props);
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

  const getOnlyNewValueIds = (updatedValues) => {
    if (widget.referenceValues?.length > 0) {
      return updatedValues
        .filter((updatedValue) => widget.referenceValues.some((value) => value.id !== updatedValue.id))
        .map((value) => value.id);
    } else {
      return updatedValues.map((value) => value.id);
    }
  };

  const handleAutocompleteChange = (_event, updatedValues, reason) => {
    if (reason === 'remove-option') {
      handleRemoveReferenceValue(updatedValues);
    } else {
      const newValueIds = getOnlyNewValueIds(updatedValues);
      if (widget.reference.manyValued) {
        addReferenceValues(newValueIds);
      } else {
        setReferenceValue(newValueIds[0]);
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
      getOptionLabel={(option: GQLReferenceValue) => option.label}
      value={widget.referenceValues}
      getOptionSelected={(option, value) => option.id === value.id}
      onChange={handleAutocompleteChange}
      renderOption={(option: GQLReferenceValue) => (
        <>
          {option.iconURL.length > 0 && (
            <div className={classes.iconContainer}>
              {option.iconURL.map((icon, index) => (
                <img
                  height="16"
                  width="16"
                  key={index}
                  alt={option.kind}
                  src={httpOrigin + icon}
                  className={classes.icon}
                  style={{ zIndex: index }}></img>
              ))}
            </div>
          )}
          <span className={classes.optionLabel} data-testid={`option-${option.label}`}>
            {option.label}
          </span>
        </>
      )}
      disableClearable
      renderTags={(value, getTagProps) =>
        value.map((option, index) => (
          <Chip
            key={index}
            classes={{ label: classes.referenceValueStyle }}
            label={option.label}
            data-testid={`reference-value-${option.label}`}
            icon={
              option.iconURL.length > 0 && (
                <div className={classes.iconContainer}>
                  {option.iconURL.map((icon, index) => (
                    <img
                      height="16"
                      width="16"
                      key={index}
                      alt={option.kind}
                      src={httpOrigin + icon}
                      className={classes.icon}
                      style={{ zIndex: index }}></img>
                  ))}
                </div>
              )
            }
            clickable={!readOnly && !widget.readOnly}
            onClick={() => optionClickHandler(option)}
            {...getTagProps({ index })}
          />
        ))
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
