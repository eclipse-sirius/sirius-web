/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './FileUpload.module.css';

const propTypes = {
  onFileSelected: PropTypes.func.isRequired,
  'data-testid': PropTypes.string.isRequired,
};

const DEFAULT_MESSAGE = 'Click here to select a file';

/**
 * Display a file upload form.
 *
 * @author hmarchadour
 */
export const FileUpload = ({ onFileSelected, 'data-testid': dataTestid }) => {
  const fileInput: React.LegacyRef<HTMLInputElement> = React.createRef();
  const initialState = {
    file: null,
    message: DEFAULT_MESSAGE,
  };

  const [state, setState] = useState(initialState);
  const { file, message } = state;

  // Update the message
  useEffect(() => {
    let message = DEFAULT_MESSAGE;
    if (file) {
      message = file.name;
    }
    setState((prevState) => {
      return { ...prevState, message };
    });
  }, [file]);

  // Update the file selection.
  const onFileInputChange = () => {
    const { files } = fileInput.current;
    let file = null;
    if (files.length === 1) {
      file = files[0];
    }

    onFileSelected(file);
    setState((prevState) => {
      return { ...prevState, file };
    });
  };

  return (
    <label className={styles.fileUpload}>
      <input
        type="file"
        name="file"
        id="file"
        className={styles.inputfile}
        ref={fileInput}
        onChange={onFileInputChange}
        data-testid={dataTestid}
      />
      <Text className={styles.message}>{message}</Text>
    </label>
  );
};
FileUpload.propTypes = propTypes;
