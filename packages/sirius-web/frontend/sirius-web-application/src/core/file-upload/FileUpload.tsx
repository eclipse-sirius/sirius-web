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
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { FileUploadProps, FileUploadState } from './FileUpload.types';

const useFileUploadViewStyles = makeStyles()((theme) => ({
  fileUpload: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
    cursor: 'pointer',
    height: 150,
    alignItems: 'center',
    justifyItems: 'center',
    border: `1px dashed ${theme.palette.secondary.main}`,
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
    fontSize: '16px',
    color: theme.palette.text.primary,
    fontWeight: 'bold',
  },
}));

export const FileUpload = ({ onFileSelected, 'data-testid': dataTestId }: FileUploadProps) => {
  const { classes: styles } = useFileUploadViewStyles();
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'core' });
  const fileInput = React.createRef<HTMLInputElement>();

  const [state, setState] = useState<FileUploadState>({
    file: null,
    message: t('clickToSelectFile'),
  });
  const { file, message } = state;

  // Update the message
  useEffect(() => {
    let message = t('clickToSelectFile');
    if (file) {
      message = file.name;
    }
    setState((prevState) => {
      return { ...prevState, message };
    });
  }, [file, t]);

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
        data-testid={dataTestId}
      />

      <Typography className={styles.message}>{message}</Typography>
    </label>
  );
};
