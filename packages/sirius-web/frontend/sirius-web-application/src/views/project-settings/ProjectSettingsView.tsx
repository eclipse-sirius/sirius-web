/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { useData } from '@eclipse-sirius/sirius-components-core';
import AppBar from '@material-ui/core/AppBar';
import Container from '@material-ui/core/Container';
import Tab from '@material-ui/core/Tab';
import Tabs from '@material-ui/core/Tabs';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Footer } from '../../footer/Footer';

import { NavigationBar } from '../../navigationBar/NavigationBar';
import { ProjectSettingsParams } from './ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from './ProjectSettingsViewExtensionPoints';

const useProjectSettingsViewStyles = makeStyles((theme) => ({
  projectSettingsView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  header: {
    display: 'grid',
    gridTemplateColumns: '1fr max-content',
    alignItems: 'center',
    padding: theme.spacing(3),
  },
  tabs: {
    display: 'grid',
    gridTemplateRows: 'min-content 1fr',
  },
  tabPanels: {
    padding: theme.spacing(3),
  },
}));

export const ProjectSettingsView = () => {
  const classes = useProjectSettingsViewStyles();
  const { data: projectSettingsTabContributions } = useData(projectSettingsTabExtensionPoint);
  const { projectId } = useParams<ProjectSettingsParams>();
  const [value, setValue] = useState(0);
  const handleChange = (_, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.projectSettingsView}>
      <NavigationBar />
      <main className={classes.main}>
        <Container maxWidth="xl">
          <div className={classes.header}>
            <Typography variant="h4">Project Settings</Typography>
            <Link to={'/projects/' + projectId + '/edit'}>Back to project</Link>
          </div>
          <div className={classes.tabs}>
            <AppBar position="static">
              <Tabs value={value} onChange={handleChange}>
                {projectSettingsTabContributions.map(({ title }, index) => (
                  <Tab label={title} key={index} />
                ))}
              </Tabs>
            </AppBar>
            <div className={classes.tabPanels}>
              {projectSettingsTabContributions.map(({ component: ProjectSettingsTabComponent }, index) => (
                <div hidden={value !== index} key={index}>
                  {value === index && <ProjectSettingsTabComponent />}
                </div>
              ))}
            </div>
          </div>
        </Container>
      </main>
      <Footer />
    </div>
  );
};
