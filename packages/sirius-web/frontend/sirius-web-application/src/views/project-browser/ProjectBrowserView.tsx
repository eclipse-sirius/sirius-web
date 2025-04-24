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
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { CreateProjectArea } from './create-projects-area/CreateProjectArea';
import { ListProjectsArea } from './list-projects-area/ListProjectsArea';

const useProjectBrowserViewStyles = makeStyles()((theme) => ({
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

export const ProjectBrowserView = () => {
  const { classes } = useProjectBrowserViewStyles();
  const { Component: Footer } = useComponent(footerExtensionPoint);

  return (
    <div className={classes.projectsView}>
      <NavigationBar />
      <Container maxWidth="xl">
        <Grid container justifyContent="center">
          <Grid size={{ xs: 8 }}>
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
