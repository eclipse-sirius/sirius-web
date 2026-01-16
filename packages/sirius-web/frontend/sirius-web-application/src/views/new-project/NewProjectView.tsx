/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import Select, { SelectChangeEvent } from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { MouseEventHandler, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { LibrariesImportTable } from '../../libraryImport/LibrariesImportTable';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import { GQLProjectTemplate } from '../project-browser/create-projects-area/useProjectTemplates.types';
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
    selectedTemplateId: null,
    selectedLibraries: [],
  });

  const {
    viewer: {
      capabilities: {
        projects: { canCreate },
        libraries: { canList: canListLibraries },
      },
    },
  } = useCurrentViewer();

  const [urlSearchParams] = useSearchParams();

  const { data: allTemplates } = useAllProjectTemplates();
  useEffect(() => {
    if (allTemplates && allTemplates.viewer.allProjectTemplates.length > 0) {
      const availableTemplates = allTemplates.viewer.allProjectTemplates;
      let selectedTemplate: GQLProjectTemplate = availableTemplates[0];
      if (urlSearchParams.has('templateId')) {
        const templateIdFromUrl = urlSearchParams.get('templateId');
        const templateFromUrl: GQLProjectTemplate | undefined = availableTemplates.find(
          (template) => template.id === templateIdFromUrl
        );
        if (templateFromUrl) {
          selectedTemplate = templateFromUrl;
        }
      }
      const newName = state.pristineName ? selectedTemplate.label : state.name;
      setState((prevState) => ({
        ...prevState,
        name: newName,
        availableTemplates,
        selectedTemplateId: selectedTemplate.id,
      }));
    }
  }, [allTemplates, urlSearchParams, state.pristineName, state.name]);

  const { createProject, loading: loadingProjectCreation, newProjectId } = useCreateProject();
  const { Component: Footer } = useComponent(footerExtensionPoint);

  const onNameChange = (event) => {
    const value = event.target.value;
    setState((prevState) => ({
      ...prevState,
      name: value,
      pristineName: false,
    }));
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

  const handleSelectedLibrariesChange = (selectedLibraryIds: string[]) => {
    setState((prevState) => ({
      ...prevState,
      selectedLibraries: selectedLibraryIds,
    }));
  };

  const handleTemplateSelectionChange = (event: SelectChangeEvent<string>) => {
    const value = event.target.value;
    navigate(`/new/project?templateId=${value}`);
  };

  const handleProjectCreation: MouseEventHandler<HTMLButtonElement> = (event) => {
    event.preventDefault();
    createProject(state.name.trim(), state.selectedTemplateId, state.selectedLibraries);
  };

  const handleFormSubmitted: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    createProject(state.name.trim(), state.selectedTemplateId, state.selectedLibraries);
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

  const showTemplateSelection = state.availableTemplates !== null && state.availableTemplates.length > 1;
  const showLibrariesSelection = canListLibraries;
  let description = t('description');
  if (!showTemplateSelection && !showLibrariesSelection) {
    description = t('descriptionNameOnly');
  } else if (showTemplateSelection && !showLibrariesSelection) {
    description = t('descriptionNameAndTemplate');
  } else if (!showTemplateSelection && showLibrariesSelection) {
    description = t('descriptionNameAndLibraries');
  }

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
                  {description}
                </Typography>
              </div>
              <Paper>
                <form onSubmit={handleFormSubmitted} className={classes.form}>
                  <Typography variant="h6">{t('name')}</Typography>
                  <TextField
                    variant="standard"
                    error={isError}
                    helperText={t('nameTextfieldHelperText')}
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
                  {showTemplateSelection ? (
                    <List dense>
                      <ListItemButton
                        onClick={toggleTemplateSelection}
                        data-testid="template-selection-toggle"
                        disableGutters>
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
                          variant="standard"
                          value={state.selectedTemplateId}
                          onChange={handleTemplateSelectionChange}
                          fullWidth
                          data-testid="template">
                          {(state.availableTemplates || []).map((template) => (
                            <MenuItem value={template.id} key={template.id} data-testid={`template-${template.label}`}>
                              <ListItemText primary={template.label}></ListItemText>
                            </MenuItem>
                          ))}
                        </Select>
                      </Collapse>
                    </List>
                  ) : null}
                  {showLibrariesSelection ? (
                    <List dense>
                      <ListItemButton
                        onClick={toggleLibrariesImport}
                        data-testid="libraries-selection-toggle"
                        disableGutters>
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
                        <LibrariesImportTable onSelectedLibrariesChange={handleSelectedLibrariesChange} />
                      </Collapse>
                    </List>
                  ) : null}
                  <div className={classes.buttons}>
                    <Button
                      variant="contained"
                      type="submit"
                      disabled={isError || loadingProjectCreation}
                      onClick={handleProjectCreation}
                      data-testid="create-project"
                      loading={loadingProjectCreation}
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
