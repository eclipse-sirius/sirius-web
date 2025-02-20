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
import { OmniboxCommandOverrideComponentProps } from '@eclipse-sirius/sirius-components-omnibox';
import DialogContentText from '@mui/material/DialogContentText';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { makeStyles } from 'tss-react/mui';
import { useState } from 'react';
import { usePublishLibraries } from './usePublishLibraries';
import { useCurrentProject } from '../views/edit-project/useCurrentProject';
import Link from '@mui/material/Link';
import Dialog from '@mui/material/Dialog';
import { PublishStudioState } from './PublishStudioOmniboxCommandOverride.types';

const usePublishCommandStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  link: {
    fontStyle: 'italic',
  },
}));

export const PublishStudioOmniboxCommandOverride = ({ onClose }: OmniboxCommandOverrideComponentProps) => {
  const { classes } = usePublishCommandStyles();

  const [state, setState] = useState<PublishStudioState>({
    version: '',
    versionIsInvalid: true,
    pristine: true,
    description: '',
  });

  const onVersionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const value = event.target.value;
    const versionIsInvalid = value.trim().length === 0;
    setState((prevState) => ({ ...prevState, version: value, versionIsInvalid, pristine: false }));
  };

  const onDescriptionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, description: value.toString() }));
  };

  const { publishLibraries } = usePublishLibraries();

  const { project } = useCurrentProject();

  const onPublishStudio = () => {
    publishLibraries(project.id, 'studio-all', state.version, state.description);
    onClose();
  };

  return (
    <Dialog open onClose={onClose} fullWidth scroll="paper" data-testid="omnibox">
      <DialogTitle>Publish Studios</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Publish all the domains and descriptions in the project as independent libraries
        </DialogContentText>
        <div className={classes.form}>
          <TextField
            variant="standard"
            error={!state.pristine && state.versionIsInvalid}
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
          <Link className={classes.link} href={'/libraries'} rel="noopener noreferrer" target="_blank">
            See published libraries
          </Link>
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
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.versionIsInvalid}
          data-testid="publish-studio"
          color="primary"
          onClick={onPublishStudio}>
          Publish
        </Button>
      </DialogActions>
    </Dialog>
  );
};
