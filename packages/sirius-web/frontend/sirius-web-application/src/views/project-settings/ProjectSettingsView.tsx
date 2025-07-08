/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { gql, useQuery } from '@apollo/client';
import { useComponent, useData, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Typography from '@mui/material/Typography';
import { SyntheticEvent, useEffect } from 'react';
import { generatePath, Navigate, Link as RouterLink, useNavigate, useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import {
  GQLGetProjectData,
  GQLGetProjectVariables,
  GQLProject,
  ProjectSettingsParams,
  ProjectSettingTabContribution,
  ProjectSettingTabProps,
} from './ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from './ProjectSettingsViewExtensionPoints';

const getProjectQuery = gql`
  query getProject($projectId: ID!) {
    viewer {
      project(projectId: $projectId) {
        id
        name
        capabilities {
          settings {
            canView
          }
        }
      }
    }
  }
`;

const useProjectSettingsViewStyles = makeStyles()((theme) => ({
  projectSettingsView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  center: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleLabel: {
    marginRight: theme.spacing(2),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
    color: 'inherit',
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
    gridTemplateColumns: '200px 1fr',
    gridTemplateRows: '1fr',
    gap: theme.spacing(4),
  },
  tab: {
    justifyContent: 'start',
    minHeight: 0,
  },
  noSettingsFound: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: theme.spacing(20),
  },
}));

export const ProjectSettingsView = () => {
  const { classes } = useProjectSettingsViewStyles();
  const { projectId, tabId } = useParams<ProjectSettingsParams>();
  const { data: projectSettingsTabContributions } = useData(projectSettingsTabExtensionPoint);
  const { loading, data, error } = useQuery<GQLGetProjectData, GQLGetProjectVariables>(getProjectQuery, {
    variables: {
      projectId,
    },
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);
  const project: GQLProject | null = data?.viewer.project;

  const selectedTabId: string | null =
    tabId && projectSettingsTabContributions.find((tab) => tab.id == tabId)
      ? tabId
      : projectSettingsTabContributions[0]?.id ?? null;
  const settingContentContribution: ProjectSettingTabContribution | null = selectedTabId
    ? projectSettingsTabContributions.filter((contribution) => contribution.id === selectedTabId)[0]
    : null;

  const navigate = useNavigate();
  const handleTabChange = (_event: SyntheticEvent, tabId: string) => {
    const pathname = generatePath('/projects/:projectId/settings/:tabId', {
      projectId,
      tabId,
    });
    navigate(pathname);
  };

  const SettingContent: () => JSX.Element = () => {
    if (settingContentContribution) {
      const { component: Component } = settingContentContribution;

      const props: ProjectSettingTabProps = {};
      return <Component {...props} />;
    }
    return null;
  };

  const { Component: Footer } = useComponent(footerExtensionPoint);

  if (loading) {
    return null;
  }
  if (!project || !project.capabilities.settings.canView) {
    return <Navigate to="/errors/404" replace />;
  }
  if (tabId && !projectSettingsTabContributions.find((tab) => tab.id == tabId)) {
    return <Navigate to="/errors/404" replace />;
  }

  const { id, name } = project;
  return (
    <div className={classes.projectSettingsView}>
      <NavigationBar>
        <div className={classes.center}>
          <div className={classes.title}>
            <Link
              variant="h6"
              component={RouterLink}
              to={`/projects/${id}/edit`}
              noWrap
              className={classes.titleLabel}
              data-testid={`navbar-${name}`}>
              {name}
            </Link>
          </div>
        </div>
      </NavigationBar>

      <main className={classes.main}>
        <Container maxWidth="xl">
          <div className={classes.header}>
            <Typography variant="h4">Settings</Typography>
          </div>
          {projectSettingsTabContributions.length > 0 && settingContentContribution != null ? (
            <div className={classes.tabs}>
              <Tabs value={selectedTabId} onChange={handleTabChange} orientation="vertical">
                {projectSettingsTabContributions.map(({ id, title, icon }) => (
                  <Tab className={classes.tab} label={title} icon={icon} iconPosition="start" key={id} value={id} />
                ))}
              </Tabs>
              <SettingContent />
            </div>
          ) : (
            <div className={classes.noSettingsFound}>
              <Typography variant="h5">No setting pages found</Typography>
            </div>
          )}
        </Container>
      </main>
      <Footer />
    </div>
  );
};
