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
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormGroup from '@mui/material/FormGroup';
import Grow from '@mui/material/Grow';
import MenuItem from '@mui/material/MenuItem';
import MenuList from '@mui/material/MenuList';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import { Theme } from '@mui/material/styles';
import { useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { FileUpload } from '../../core/file-upload/FileUpload';
import {
  UploadDocumentModalProps,
  UploadDocumentModalState,
  UploadDocumentSplitButtonProps,
  UploadDocumentSplitButtonState,
} from './UploadDocumentModal.types';
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
    readOnly: false,
  });
  const { classes: styles } = useFormStyles();
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'model.upload' });

  const { uploadDocument, loading, uploadedDocument } = useUploadDocument();

  const onFileSelected = (file: File) => setState((prevState) => ({ ...prevState, file }));

  const onReadOnlyChange = (readOnly: boolean) => setState((prevState) => ({ ...prevState, readOnly }));

  const performDocumentUpload: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    uploadDocument(editingContextId, state.file, state.readOnly);
  };

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
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
        <UploadDocumentSplitButton
          readOnly={state.readOnly}
          onReadOnlyChange={onReadOnlyChange}
          disabled={!state.file || loading || !!uploadedDocument}
          loading={loading}
        />
        <Button
          variant={uploadedDocument === null ? 'outlined' : 'contained'}
          color="primary"
          type="button"
          form="upload-form-id"
          data-testid="upload-document-close"
          onClick={() => onClose()}>
          {t('cancel')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export const UploadDocumentSplitButton = ({
  readOnly,
  onReadOnlyChange,
  disabled,
  loading,
}: UploadDocumentSplitButtonProps) => {
  const [state, setState] = useState<UploadDocumentSplitButtonState>({
    open: false,
  });

  const uploadLabel = 'Upload';
  const uploadReadOnlyLabel = 'Upload (read-only)';

  const buttonGroupRef = useRef<HTMLDivElement>(null);
  const widgetRef = useRef<HTMLDivElement>(null);

  const handleMenuItemClick = (_event, readOnly) => {
    setState((prevState) => ({ ...prevState, open: false }));
    onReadOnlyChange(readOnly);
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    event.preventDefault();
    setState((prevState) => ({ ...prevState, open: false }));
  };

  return (
    <div ref={widgetRef}>
      <ButtonGroup
        variant="contained"
        color="primary"
        ref={buttonGroupRef}
        aria-label="split button"
        disabled={disabled}>
        <Button
          data-testid="upload-document-split-button"
          variant="contained"
          color="primary"
          type="submit"
          form="upload-form-id"
          loading={loading}>
          {readOnly ? uploadReadOnlyLabel : uploadLabel}
        </Button>
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'upload-document-split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          onClick={handleToggle}>
          <ArrowDropDownIcon />
        </Button>
      </ButtonGroup>
      <Popper
        open={state.open}
        anchorEl={buttonGroupRef.current}
        transition
        placement="bottom"
        style={{ zIndex: 1400 }}>
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
            }}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id="split-button-menu">
                  <MenuItem selected={!readOnly} onClick={(event) => handleMenuItemClick(event, false)}>
                    {uploadLabel}
                  </MenuItem>
                  <MenuItem selected={readOnly} onClick={(event) => handleMenuItemClick(event, true)}>
                    {uploadReadOnlyLabel}
                  </MenuItem>
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
};
