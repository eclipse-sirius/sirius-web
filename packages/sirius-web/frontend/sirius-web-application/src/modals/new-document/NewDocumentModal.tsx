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
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { NewDocumentModalProps, NewDocumentModalState } from './NewDocumentModal.types';
import { useCreateDocument } from './useCreateDocument';
import { useStereotypes } from './useStereotypes';
import { GQLStereotype } from './useStereotypes.types';

const useNewDocumentModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

export const NewDocumentModal = ({ editingContextId, onClose }: NewDocumentModalProps) => {
  const classes = useNewDocumentModalStyles();
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

  const onStereotypeChange = (event: React.ChangeEvent<{ name?: string; value: unknown }>) => {
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
      <DialogTitle id="dialog-title">Create a new model</DialogTitle>
      <DialogContent>
        <div className={classes.form}>
          <TextField
            error={!state.pristine && state.nameIsInvalid}
            helperText="The name cannot be empty"
            label="Name"
            name="name"
            value={state.name}
            placeholder="Enter the name of the model"
            data-testid="name"
            inputProps={{ 'data-testid': 'name-input' }}
            autoFocus={true}
            onChange={onNameChange}
            disabled={loadingStereotypes || documentCreationLoading}
          />
          <InputLabel id="newDocumentModalStereotypeLabel">Model type</InputLabel>
          <Select
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
          Create
        </Button>
      </DialogActions>
    </Dialog>
  );
};
