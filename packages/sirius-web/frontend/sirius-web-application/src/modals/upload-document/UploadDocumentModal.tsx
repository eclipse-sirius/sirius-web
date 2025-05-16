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
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormGroup from '@mui/material/FormGroup';
import { Theme } from '@mui/material/styles';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { FileUpload } from '../../core/file-upload/FileUpload';
import { UploadDocumentModalProps, UploadDocumentModalState } from './UploadDocumentModal.types';
import { UploadDocumentReport } from './UploadDocumentReport';
import { useUploadDocument } from './useUploadDocument';

const useFormStyles = makeStyles()((theme: Theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

export const UploadDocumentModal = ({ editingContextId, onClose }: UploadDocumentModalProps) => {
  const [state, setState] = useState<UploadDocumentModalState>({
    file: null,
  });
  const { classes: styles } = useFormStyles();

  const { uploadDocument, loading, uploadedDocument } = useUploadDocument();

  const onFileSelected = (file: File) => setState((prevState) => ({ ...prevState, file }));

  const performDocumentUpload: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    uploadDocument(editingContextId, state.file);
  };

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle id="dialog-title">Upload new model</DialogTitle>
      <DialogContent>
        <form
          id="upload-form-id"
          onSubmit={performDocumentUpload}
          encType="multipart/form-data"
          className={styles.form}>
          <FormGroup>
            <FileUpload onFileSelected={onFileSelected} data-testid="file" />
          </FormGroup>
        </form>
        <UploadDocumentReport uploadedDocument={uploadedDocument} />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={!state.file || loading || !!uploadedDocument}
          color="primary"
          type="submit"
          form="upload-form-id"
          loading={loading}
          data-testid="upload-document-submit">
          Upload
        </Button>
        <Button
          variant={uploadedDocument === null ? 'outlined' : 'contained'}
          color="primary"
          type="button"
          form="upload-form-id"
          data-testid="upload-document-close"
          onClick={() => onClose()}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
};
