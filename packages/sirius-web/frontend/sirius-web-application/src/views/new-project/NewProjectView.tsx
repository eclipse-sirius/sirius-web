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

import { useComponent } from '@eclipse-sirius/sirius-components-core';
import { ExpandLess, ExpandMore } from '@mui/icons-material';
import Button from '@mui/material/Button';
import Collapse from '@mui/material/Collapse';
import Container from '@mui/material/Container';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Paper from '@mui/material/Paper';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { LibrariesImportTable } from '../../omnibox/LibrariesImportTable';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import { NewProjectViewState } from './NewProjectView.types';
import { useAllProjectTemplates } from './useAllProjectTemplates';
import { useCreateProject } from './useCreateProject';

const useNewProjectViewStyles = makeStyles()((theme) => ({
  newProjectView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  newProjectViewContainer: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(8),
  },
  titleContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'start',
    paddingBottom: theme.spacing(2),
  },
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
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

const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 1024;

export const NewProjectView = () => {
  const { classes } = useNewProjectViewStyles();
  const navigate = useNavigate();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'newProjectView' });

  const [state, setState] = useState<NewProjectViewState>({
    name: '',
    pristineName: true,
    availableTemplates: null,
    templateSelectionOpen: false,
    librariesImportOpen: false,
    selectedTemplate: 'create-project',
    selectedLibraries: [],
  });

  const {
    viewer: {
      capabilities: {
        projects: { canCreate },
      },
    },
  } = useCurrentViewer();

  const [urlSearchParams] = useSearchParams();
  useEffect(() => {
    if (urlSearchParams.has('templateId')) {
      setState((prevState) => ({ ...prevState, selectedTemplate: urlSearchParams.get('templateId') }));
    }
  }, [urlSearchParams]);

  const { data: allTemplates, loading } = useAllProjectTemplates();
  useEffect(() => {
    if (!loading) {
      const availableTemplates = allTemplates.viewer.allProjectTemplates;
      const newName = state.pristineName
        ? availableTemplates.find((template) => template.id === state.selectedTemplate)?.label || 'New Project'
        : state.name;
      setState((prevState) => ({ ...prevState, name: newName, availableTemplates }));
    }
  }, [loading, allTemplates, state.pristineName, state.name]);

  const { createProject, newProjectId } = useCreateProject();
  const { Component: Footer } = useComponent(footerExtensionPoint);

  const onNameChange = (event) => {
    const value = event.target.value;
    setState((prevState) => ({
      ...prevState,
      name: value,
      pristineName: false,
    }));
  };

  const onCreateNewProject: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    createProject(state.name.trim(), state.selectedTemplate, state.selectedLibraries);
  };

  const toggleTemplateSelection: React.MouseEventHandler<HTMLDivElement> = () => {
    setState((prevState) => ({
      ...prevState,
      templateSelectionOpen: !prevState.templateSelectionOpen,
    }));
  };

  const toggleLibrariesImport: React.MouseEventHandler<HTMLDivElement> = () => {
    setState((prevState) => ({
      ...prevState,
      librariesImportOpen: !prevState.librariesImportOpen,
    }));
  };

  const onSelectedLibrariesChange = (selectedLibraryIds: string[]) => {
    setState((prevState) => ({
      ...prevState,
      selectedLibraries: selectedLibraryIds,
    }));
  };

  useEffect(() => {
    if (!canCreate) {
      navigate('/errors/404');
    }
  }, [canCreate]);

  useEffect(() => {
    if (newProjectId) {
      navigate(`/projects/${newProjectId}/edit`);
    }
  }, [newProjectId]);

  const isError = isNameInvalid(state.name);
  return (
    <>
      <div className={classes.newProjectView}>
        <NavigationBar />
        <main className={classes.main}>
          <Container maxWidth="lg">
            <div className={classes.newProjectViewContainer}>
              <div className={classes.titleContainer}>
                <Typography variant="h2" align="center" gutterBottom>
                  {t('title')}
                </Typography>
                <Typography variant="h4" align="center" gutterBottom>
                  {t('description')}
                </Typography>
              </div>
              <Paper>
                <form onSubmit={onCreateNewProject} className={classes.form}>
                  <TextField
                    variant="outlined"
                    error={isError}
                    helperText={t('nameTextfieldHelperText')}
                    label={t('name')}
                    name="name"
                    value={state.name}
                    placeholder={t('nameTextfieldPlaceholder')}
                    slotProps={{
                      htmlInput: () => ({
                        'data-testid': 'name',
                      }),
                    }}
                    autoFocus={true}
                    onChange={onNameChange}
                  />
                  <List dense>
                    <ListItemButton onClick={toggleTemplateSelection} disableGutters>
                      <ListItemText
                        sx={(theme) => ({
                          display: 'flex',
                          flexDirection: 'row',
                          columnGap: theme.spacing(0.5),
                          alignItems: 'center',
                        })}
                        slots={{
                          primary: Typography,
                        }}
                        slotProps={{
                          primary: { variant: 'h6' },
                        }}
                        primary="Project Template"
                        secondary="(Optional)"
                      />
                      {state.templateSelectionOpen ? (
                        <ExpandLess
                          sx={(theme) => ({
                            color: theme.palette.text.secondary,
                          })}
                        />
                      ) : (
                        <ExpandMore
                          sx={(theme) => ({
                            color: theme.palette.text.secondary,
                          })}
                        />
                      )}
                    </ListItemButton>
                    <Collapse in={state.templateSelectionOpen} timeout="auto">
                      <Select
                        variant="outlined"
                        value={state.selectedTemplate}
                        onChange={(event) => {
                          const { value } = event.target;
                          const newName = state.pristineName
                            ? state.availableTemplates.find((template) => template.id === value)?.label || 'New Project'
                            : state.name;
                          setState((prevState) => ({
                            ...prevState,
                            name: newName,
                            selectedTemplate: value,
                          }));
                          navigate(`/new/project?templateId=${value}`);
                        }}
                        fullWidth
                        data-testid="template">
                        {(state.availableTemplates || []).map((template) => (
                          <MenuItem value={template.id} key={template.id}>
                            <ListItemText primary={template.label}></ListItemText>
                          </MenuItem>
                        ))}
                      </Select>
                    </Collapse>
                  </List>
                  <List dense>
                    <ListItemButton onClick={toggleLibrariesImport} disableGutters>
                      <ListItemText
                        sx={(theme) => ({
                          display: 'flex',
                          flexDirection: 'row',
                          columnGap: theme.spacing(0.5),
                          alignItems: 'center',
                        })}
                        slots={{
                          primary: Typography,
                        }}
                        slotProps={{
                          primary: { variant: 'h6' },
                        }}
                        primary={t('libraries')}
                        secondary={'(' + t('optional') + ')'}
                      />
                      {state.librariesImportOpen ? (
                        <ExpandLess
                          sx={(theme) => ({
                            color: theme.palette.text.secondary,
                          })}
                        />
                      ) : (
                        <ExpandMore
                          sx={(theme) => ({
                            color: theme.palette.text.secondary,
                          })}
                        />
                      )}
                    </ListItemButton>
                    <Collapse in={state.librariesImportOpen} timeout="auto">
                      <LibrariesImportTable onSelectedLibrariesChange={onSelectedLibrariesChange} />
                    </Collapse>
                  </List>
                  <div className={classes.buttons}>
                    <Button
                      variant="contained"
                      type="submit"
                      disabled={isError}
                      data-testid="create-project"
                      color="primary">
                      {t('submit')}
                    </Button>
                  </div>
                </form>
              </Paper>
            </div>
          </Container>
        </main>
        <Footer />
      </div>
    </>
  );
};
