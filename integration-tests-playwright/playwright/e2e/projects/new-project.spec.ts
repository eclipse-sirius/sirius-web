/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNewProject } from '../../helpers/PlaywrightNewProject';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

const projectEditUrlPattern = /\/projects\/[a-z0-9-]+\/edit\/?$/;

const getProjectId = (url: string): string | null => {
  const projectId = new URL(url).pathname.match(/^\/projects\/([^/]+)\/edit\/?$/)?.[1];
  return projectId ?? null;
};

test.describe('project creation', () => {
  let projectId: string | null = null;

  test.beforeEach(() => {
    projectId = null;
  });

  test.afterEach(async ({ page, request }) => {
    const projectIdToDelete = projectId ?? getProjectId(page.url());
    if (projectIdToDelete !== null) {
      await new PlaywrightProject(request).deleteProject(projectIdToDelete);
    }
  });

  test.describe('given the new project form', () => {
    test('when the name is empty, then project creation is disabled', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visit();

      await newProject.nameField.fill('');

      await expect(newProject.createProjectButton).toBeDisabled();
    });

    test('when the name is invalid, then a valid name enables project creation', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visit();

      await newProject.nameField.fill('Cy');
      await expect(newProject.createProjectButton).toBeDisabled();

      await newProject.nameField.fill('Playwright Project');
      await expect(newProject.createProjectButton).toBeEnabled();
    });

    test('when the create button is clicked, then the new project is opened', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visit();
      await newProject.nameField.fill('Playwright Project - New');
      await expect(newProject.createProjectButton).toBeEnabled();

      projectId = await newProject.createProject();

      await expect(page).toHaveURL(projectEditUrlPattern);
    });
  });

  test.describe('given the projects browser page', () => {
    test('when the Blank Project template is selected, then its initial configuration is displayed', async ({
      page,
    }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await newProject.selectTemplateFromProjects('Blank Project');

      await expect(page).toHaveURL(/\/new\/project\?templateId=blank-project$/);
      await expect(newProject.nameField).toHaveValue('Blank Project');
      await expect(page.getByTestId('template')).toContainText('Blank Project');

      await newProject.openLibraries();
      await expect(page.getByTestId('libraries-import-table').locator('input[type="checkbox"]:checked')).toHaveCount(0);
    });

    test('when a Blank Project is created with a custom name, then the custom name is displayed', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await newProject.selectTemplateFromProjects('Blank Project');
      await newProject.nameField.fill('Custom Project Name');

      projectId = await newProject.createProject();

      await expect(page).toHaveURL(projectEditUrlPattern);
      await expect(page.getByTestId('navbar-title')).toContainText('Custom Project Name');
    });

    test('when the Papaya template is selected, then its required library cannot be unselected', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await newProject.selectTemplateFromProjects('Papaya - Blank');
      await newProject.openLibraries();

      const requiredLibraryCheckbox = newProject.getLibraryCheckbox('papaya:java:0.0.3');
      await expect(requiredLibraryCheckbox).toBeChecked();
      await expect(requiredLibraryCheckbox).toBeDisabled();
    });

    test('when a Papaya project is created with an optional library, then its content is initialized', async ({
      page,
    }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await newProject.selectTemplateFromProjects('Papaya - Blank');
      await newProject.openLibraries();
      await newProject.getLibraryCheckbox('papaya:reactivestreams:0.0.1').check();

      projectId = await newProject.createProject();

      await expect(page).toHaveURL(projectEditUrlPattern);
      await expect(page.getByTestId('navbar-title')).toContainText('Papaya - Blank');
      const explorer = new PlaywrightExplorer(page);
      await expect(await explorer.getTreeItemLabel('Papaya')).toBeVisible();
      await expect(await explorer.getTreeItemLabel('Java')).toBeVisible();
      await expect(await explorer.getTreeItemLabel('Reactive Streams')).toBeVisible();
    });

    test('when Studio is selected from all templates, then its initial configuration is displayed', async ({
      page,
    }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await page.getByTestId('show-all-templates').click();
      await newProject.selectTemplateFromProjects('Studio');

      await expect(page).toHaveURL(/\/new\/project\?templateId=studio-template$/);
      await expect(newProject.nameField).toHaveValue('Studio');
      await expect(page.getByTestId('template')).toContainText('Studio');

      await newProject.openLibraries();
      await expect(page.getByTestId('libraries-import-table').locator('input[type="checkbox"]:checked')).toHaveCount(0);
    });

    test('when a Studio is created with a custom name, then its content is initialized', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await page.getByTestId('show-all-templates').click();
      await newProject.selectTemplateFromProjects('Studio');
      await newProject.nameField.fill('Custom Studio');

      projectId = await newProject.createProject();

      await expect(page).toHaveURL(projectEditUrlPattern);
      await expect(page.getByTestId('navbar-title')).toContainText('Custom Studio');
      const explorer = new PlaywrightExplorer(page);
      await expect(await explorer.getTreeItemLabel('DomainNewModel')).toBeVisible();
      await expect(await explorer.getTreeItemLabel('ViewNewModel')).toBeVisible();
    });

    test('when another template is selected, then the default name is updated', async ({ page }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await page.getByTestId('show-all-templates').click();
      await newProject.selectTemplateFromProjects('Studio');

      await newProject.selectTemplateFromForm('Papaya - Blank');

      await expect(newProject.nameField).toHaveValue('Papaya - Blank');
    });

    test('when another template and a library are selected, then the project content is initialized', async ({
      page,
    }) => {
      const newProject = new PlaywrightNewProject(page);
      await newProject.visitProjects();
      await page.getByTestId('show-all-templates').click();
      await newProject.selectTemplateFromProjects('Studio');
      await newProject.selectTemplateFromForm('Papaya - Blank');
      await newProject.openLibraries();
      await newProject.getLibraryCheckbox('papaya:java:0.0.1').check();

      projectId = await newProject.createProject();

      await expect(page).toHaveURL(projectEditUrlPattern);
      await expect(page.getByTestId('navbar-title')).toContainText('Papaya - Blank');
      const explorer = new PlaywrightExplorer(page);
      await expect(await explorer.getTreeItemLabel('Papaya')).toBeVisible();
      await expect(await explorer.getTreeItemLabel('Java')).toBeVisible();
    });
  });
});
