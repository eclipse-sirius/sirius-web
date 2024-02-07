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
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import { Footer } from '../../footer/Footer';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { CreateProjectArea } from './create-projects-area/CreateProjectArea';
import { ListProjectsArea } from './list-projects-area/ListProjectsArea';

const useProjectsViewStyles = makeStyles((theme) => ({
  projectsView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(5),
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
}));

export const ProjectBrowser = () => {
  const classes = useProjectsViewStyles();
  return (
    <div className={classes.projectsView}>
      <NavigationBar />
      <Container maxWidth="xl">
        <Grid container justifyContent="center">
          <Grid item xs={8}>
            <main className={classes.main}>
              <CreateProjectArea />
              <ListProjectsArea />
            </main>
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </div>
  );
};
