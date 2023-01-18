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
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import React, { useEffect, useState } from 'react';
import { FileUploadProps } from './FileUpload.types';

const DEFAULT_MESSAGE = 'Click here to select a file';

const useFileUploadViewStyles = makeStyles(() => ({
  fileUpload: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
    cursor: 'pointer',
    height: 150,
    alignItems: 'center',
    justifyItems: 'center',
    border: '1px dashed var(--daintree)',
  },
  inputfile: {
    width: 0.1,
    height: 0.1,
    opacity: '0',
    overflow: 'hidden',
    position: 'absolute',
    zIndex: -1,
  },
  message: {
    fontSize: 'var(--font-size-4)',
    color: 'var(--daintree)',
    fontWeight: 'bold',
  },
}));

export const FileUpload = ({ onFileSelected, dataTestid: dataTestid }: FileUploadProps) => {
  const styles = useFileUploadViewStyles();
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
    let file: File = null;
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

      <Typography className={styles.message}>{message}</Typography>
    </label>
  );
};
