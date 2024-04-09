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
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormGroup from '@material-ui/core/FormGroup';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { FileUpload } from '../../core/file-upload/FileUpload';
import { UploadDocumentModalProps, UploadDocumentModalState } from './UploadDocumentModal.types';
import { UploadDocumentReport } from './UploadDocumentReport';
import { useUploadDocument } from './useUploadDocument';

const useFormStyles = makeStyles((theme: Theme) => ({
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
  const styles = useFormStyles();

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
