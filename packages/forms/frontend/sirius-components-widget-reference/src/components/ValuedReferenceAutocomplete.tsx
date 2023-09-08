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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import Chip from '@material-ui/core/Chip';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import TextField from '@material-ui/core/TextField';
import { Theme, makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { useContext } from 'react';
import {
  GQLReferenceValue,
  GQLReferenceWidgetStyle,
  GQLRemoveReferenceValueMutationVariables,
} from '../ReferenceWidgetFragment.types';
import { ValuedReferenceAutocompleteProps } from './ValuedReferenceAutocomplete.types';

const useStyles = makeStyles<Theme, GQLReferenceWidgetStyle>((theme) => ({
  optionLabel: {
    paddingLeft: theme.spacing(0.5),
  },
  referenceValueStyle: {
    color: ({ color }) => (color ? color : null),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

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
  editReference,
  optionClickHandler,
  clearReference,
  removeReferenceValue,
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

  const handleRemoveReferenceValue = (updatedValues) => {
    // looking for the missing one
    const removedValues = widget.referenceValues.filter(
      (value) => !updatedValues.find((n: GQLReferenceValue) => n.id === value.id)
    );
    if (removedValues.length == 1) {
      const variables: GQLRemoveReferenceValueMutationVariables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          referenceValueId: removedValues[0].id,
        },
      };
      removeReferenceValue({ variables });
    }
  };

  const handleAutocompleteChange = (_event, newValue, reason) => {
    if (reason === 'remove-option') {
      handleRemoveReferenceValue(newValue);
    } else {
      let newValueIds: string[] = newValue.map((value: GQLReferenceValue) => value.id);
      if (!widget.reference.manyValued && widget.referenceValues.length > 0) {
        // For mono-valued reference, we only keep the new one
        newValueIds = newValueIds.filter((newValue) => widget.referenceValues.some((value) => value.id !== newValue));
      }
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          newValueIds,
        },
      };
      editReference({ variables });
    }
  };

  const handleClearClick = () => {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
      },
    };
    clearReference({ variables });
  };

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
      options={widget.referenceOptions}
      getOptionLabel={(option: GQLReferenceValue) => option.label}
      value={widget.referenceValues}
      getOptionSelected={(option, value) => option.id === value.id}
      onChange={handleAutocompleteChange}
      renderOption={(option: GQLReferenceValue) => (
        <>
          {option.iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + option.iconURL} /> : null}
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
            icon={option.iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + option.iconURL} /> : null}
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
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <>
                {params.InputProps.endAdornment}
                <InputAdornment position="end">
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
                    onClick={handleClearClick}>
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
