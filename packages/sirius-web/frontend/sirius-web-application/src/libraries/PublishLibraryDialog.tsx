/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Link from '@mui/material/Link';
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { useCurrentProject } from '../views/edit-project/useCurrentProject';
import { PublishLibraryDialogProps, PublishLibraryDialogState } from './PublishLibraryDialog.types';
import { usePublishLibraries } from './usePublishLibraries';

const usePublishLibraryDialogStyles = makeStyles()((theme) => ({
  content: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
}));

export const PublishLibraryDialog = ({ open, title, message, publicationKind, onClose }: PublishLibraryDialogProps) => {
  const { classes } = usePublishLibraryDialogStyles();

  const [state, setState] = useState<PublishLibraryDialogState>({
    pristine: true,
    version: '',
    description: '',
  });

  const onVersionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, version: value, pristine: false }));
  };

  const onDescriptionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, description: value.toString() }));
  };

  const { publishLibraries, data } = usePublishLibraries();
  useEffect(() => {
    if (data) {
      onClose();
    }
  }, [data]);

  const { project } = useCurrentProject();

  const handlePublish = () => {
    publishLibraries(project.id, publicationKind, state.version, state.description);
  };

  const isVersionInvalid = state.version.trim().length === 0;
  const isInvalid: boolean = isVersionInvalid;

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth scroll="paper" data-testid="publish-library">
      <DialogTitle>{title}</DialogTitle>
      <DialogContent className={classes.content}>
        <DialogContentText>{message}</DialogContentText>
        <div className={classes.form}>
          <TextField
            variant="standard"
            error={!state.pristine && isVersionInvalid}
            helperText="The version cannot be empty"
            label="Version"
            name="version"
            value={state.version}
            placeholder="Enter your version here..."
            data-testid="version"
            inputProps={{ 'data-testid': 'version-input' }}
            autoFocus={true}
            onChange={onVersionChange}
          />
          <TextField
            variant="standard"
            label="Description"
            name="description"
            value={state.description}
            placeholder="Enter your description here..."
            data-testid="description"
            inputProps={{ 'data-testid': 'description-input' }}
            onChange={onDescriptionChange}
          />
        </div>
        <Link component={RouterLink} to="/libraries" rel="noopener noreferrer" target="_blank">
          See the published libraries
        </Link>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={isInvalid}
          data-testid="publish-library"
          color="primary"
          onClick={handlePublish}>
          Publish
        </Button>
      </DialogActions>
    </Dialog>
  );
};
