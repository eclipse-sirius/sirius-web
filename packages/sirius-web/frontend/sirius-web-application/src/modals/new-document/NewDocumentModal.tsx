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
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { NewDocumentModalProps, NewDocumentModalState } from './NewDocumentModal.types';
import { useCreateDocument } from './useCreateDocument';
import { useStereotypes } from './useStereotypes';
import { GQLStereotype } from './useStereotypes.types';

const useNewDocumentModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

export const NewDocumentModal = ({ editingContextId, onClose }: NewDocumentModalProps) => {
  const { classes } = useNewDocumentModalStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'model.create' });
  const [state, setState] = useState<NewDocumentModalState>({
    name: '',
    nameIsInvalid: true,
    pristine: true,
    stereotypeId: '',
  });

  const { data, loading: loadingStereotypes } = useStereotypes(editingContextId);
  const stereotypes: GQLStereotype[] = (data?.viewer?.editingContext?.stereotypes.edges ?? []).map((edge) => edge.node);
  useEffect(() => {
    if (state.stereotypeId === '' && stereotypes.length > 0) {
      setState((prevState) => ({ ...prevState, stereotypeId: stereotypes[0].id }));
    }
  }, [stereotypes]);

  const onNameChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const value = event.target.value;
    const nameIsInvalid = value.trim().length === 0;
    setState((prevState) => ({ ...prevState, name: value, nameIsInvalid, pristine: false }));
  };

  const onStereotypeChange = (event: SelectChangeEvent<string>) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, stereotypeId: value.toString() }));
  };

  const { createDocument, loading: documentCreationLoading, documentCreated } = useCreateDocument();

  const onCreateDocument = () => {
    createDocument(editingContextId, state.stereotypeId, state.name);
  };

  useEffect(() => {
    if (documentCreated) {
      onClose();
    }
  }, [documentCreated, onClose]);

  return (
    <Dialog
      open={true}
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="xs"
      fullWidth
      data-testid="create-new-model">
      <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
      <DialogContent>
        <div className={classes.form}>
          <TextField
            variant="standard"
            error={!state.pristine && state.nameIsInvalid}
            helperText={t('name.helperText')}
            label={t('name.label')}
            name="name"
            value={state.name}
            placeholder={t('name.placeholder')}
            data-testid="name"
            inputProps={{ 'data-testid': 'name-input' }}
            autoFocus={true}
            onChange={onNameChange}
            disabled={loadingStereotypes || documentCreationLoading}
          />
          <InputLabel id="newDocumentModalStereotypeLabel">{t('modelType.label')}</InputLabel>
          <Select
            variant="standard"
            value={state.stereotypeId}
            onChange={onStereotypeChange}
            disabled={loadingStereotypes || documentCreationLoading}
            labelId="newDocumentModalStereotypeLabel"
            fullWidth
            inputProps={{ 'data-testid': 'stereotype-input' }}
            data-testid="stereotype">
            {stereotypes.map((stereotype) => (
              <MenuItem value={stereotype.id} key={stereotype.id}>
                {stereotype.label}
              </MenuItem>
            ))}
          </Select>
        </div>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.nameIsInvalid || loadingStereotypes || documentCreationLoading}
          data-testid="create-document"
          color="primary"
          onClick={onCreateDocument}>
          {t('submit')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
