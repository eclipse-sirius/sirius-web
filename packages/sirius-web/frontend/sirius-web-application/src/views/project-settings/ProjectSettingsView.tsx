/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import { Footer } from '../../footer/Footer';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { ProjectImagesSettings } from './ProjectImagesSettings';

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
}));

export const ProjectSettingsView = () => {
  const classes = useProjectSettingsViewStyles();

  return (
    <div className={classes.projectSettingsView}>
      <NavigationBar />
      <main className={classes.main}>
        <Container maxWidth="xl">
          <Grid container justifyContent="center">
            <Grid item xs={8}>
              <ProjectImagesSettings />
            </Grid>
          </Grid>
        </Container>
      </main>
      <Footer />
    </div>
  );
};
