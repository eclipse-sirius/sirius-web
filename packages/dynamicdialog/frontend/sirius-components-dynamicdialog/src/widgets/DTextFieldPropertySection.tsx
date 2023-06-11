/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { useState } from 'react';
import { DTextFieldPropertySectionProps } from './DTextFieldPropertySection.types';
/**
 * Defines the content of a Textfield property section.
 * The content is submitted when the focus is lost and when pressing the "Enter" key.
 */
export const DTextFieldPropertySection = ({
  dialogDescriptionId,
  widgetDescriptionId,
  outputVariableName,
  label,
  editingContextId,
  initialValue,
  setValue,
}: DTextFieldPropertySectionProps) => {
  const [currentText, setText] = useState<String>(initialValue);
  const [typingTimeout, setTypingTimeout] = useState(null);

  // Fonction de rappel exécutée lorsque l'utilisateur arrête d'écrire pendant 0.5s
  const onChange = (event) => {
    const value = event.target.value;
    setText(value);

    if (typingTimeout) {
      clearTimeout(typingTimeout);
    }

    setTypingTimeout(
      setTimeout(() => {
        setValue(outputVariableName, value);
      }, 500)
    );
  };

  // const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   const { value } = event.target;
  //   setText(value);
  // };

  // const onBlur = () => {
  //   setValue(outputVariableName, currentText);
  // };

  const onKeyPress = (e) => {
    if (e.nativeEvent.key == 'Enter') {
      setValue(outputVariableName, currentText);
    }
  };

  return (
    <div
    // onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
    //   if (!event.currentTarget.contains(event.relatedTarget)) {
    //     onBlur();
    //   }
    // }}
    >
      <Typography variant="subtitle2">{label}</Typography>
      <TextField
        name={label}
        placeholder={label}
        value={currentText}
        spellCheck={false}
        margin="dense"
        fullWidth
        onChange={onChange}
        onKeyPress={onKeyPress}
        data-testid={label}
      />
    </div>
  );
};
