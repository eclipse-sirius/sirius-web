/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { styled } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import { forwardRef, useImperativeHandle, useRef } from 'react';
import { DeckCardInputProps } from './DeckCardInput.types';

/**
 * Inspired from react-trello InlineInput component.
 */
export const DeckCardInput = forwardRef(({ value, placeholder, onSave, style }: DeckCardInputProps, ref) => {
  const StyledTextField = styled(TextField)({
    '& input': {
      padding: '0px 0px 0px 0px',
    },
    '& input:focus': {
      backgroundColor: 'white',
      boxShadow: 'inset 0 0 0 2px #0079bf',
    },
  });
  const onFocus = (e) => e.target.select();
  const textInput = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);
  // This is the way to select all text if mouse clicked
  const onMouseDown = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (document.activeElement != e.target) {
      e.preventDefault();
      textInput.current?.focus();
    }
  };

  const onBlur = () => {
    updateValue();
  };

  const onKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (e.key == 'Enter') {
      textInput.current?.blur();
      e.preventDefault();
    }

    if (e.key == 'Escape') {
      setValue(value);
      textInput.current?.blur();
      e.preventDefault();
    }
    if (e.key == 'Tab') {
      textInput.current?.blur();
    }
  };

  const getValue = () => textInput.current?.value ?? '';
  const setValue = (value) => {
    if (textInput.current) {
      textInput.current.value = value;
    }
  };

  const updateValue = () => {
    if (getValue() != value) {
      onSave(getValue());
    }
  };

  const handleOnClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    e.stopPropagation();
  };

  useImperativeHandle(ref, () => textInput.current);
  return (
    <StyledTextField
      inputRef={textInput}
      onMouseDown={onMouseDown}
      onFocus={onFocus}
      onBlur={onBlur}
      onKeyDown={onKeyDown}
      onClick={handleOnClick}
      placeholder={value?.length == 0 ? undefined : placeholder}
      defaultValue={value}
      autoComplete="off"
      autoCorrect="off"
      autoCapitalize="off"
      spellCheck="false"
      inputProps={{
        style: {
          ...style,
        },
      }}
      InputProps={{
        disableUnderline: true,
      }}
    />
  );
});
