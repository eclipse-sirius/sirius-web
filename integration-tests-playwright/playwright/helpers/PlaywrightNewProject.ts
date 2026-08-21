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

import { expect, type Locator, type Page } from '@playwright/test';

interface GQLCreateProjectSuccessPayload {
  __typename: 'CreateProjectSuccessPayload';
  project: {
    id: string;
  };
}

interface GQLCreateProjectErrorPayload {
  __typename: 'ErrorPayload';
}

interface GQLCreateProjectResponse {
  data: {
    createProject: GQLCreateProjectSuccessPayload | GQLCreateProjectErrorPayload;
  };
}

export class PlaywrightNewProject {
  readonly page: Page;
  readonly nameField: Locator;
  readonly createProjectButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.nameField = page.getByTestId('name');
    this.createProjectButton = page.getByTestId('create-project');
  }

  async visit(): Promise<void> {
    await this.page.goto('/new/project');
    await expect(this.nameField).not.toHaveValue('');
  }

  async visitProjects(): Promise<void> {
    await this.page.goto('/projects');
    await expect(this.page).toHaveURL(/\/projects\/?$/);
  }

  async selectTemplateFromProjects(templateLabel: string): Promise<void> {
    await this.page.getByTestId(`create-project-from-template-${templateLabel}`).click();
    await expect(this.nameField).toHaveValue(templateLabel);
  }

  async selectTemplateFromForm(templateLabel: string): Promise<void> {
    await this.page.getByTestId('template').click();
    await this.page.getByTestId(`template-${templateLabel}`).click();
    await expect(this.nameField).toHaveValue(templateLabel);
  }

  async openLibraries(): Promise<void> {
    await this.page.getByTestId('libraries-selection-toggle').click();
    await expect(this.page.getByTestId('libraries-import-table')).toBeVisible();
  }

  getLibraryCheckbox(libraryId: string): Locator {
    return this.page.getByTestId(`library-${libraryId}`).locator('input[type="checkbox"]');
  }

  async createProject(): Promise<string> {
    const createProjectResponsePromise = this.page.waitForResponse((response) => {
      const request = response.request();
      return (
        request.method() === 'POST' &&
        request.url().includes('/api/graphql') &&
        request.postDataJSON()?.operationName === 'createProject'
      );
    });

    await this.createProjectButton.click();

    const createProjectResponse = await createProjectResponsePromise;
    expect(createProjectResponse.ok()).toBeTruthy();
    const responseBody: GQLCreateProjectResponse = await createProjectResponse.json();
    const payload = responseBody.data.createProject;
    if (payload.__typename !== 'CreateProjectSuccessPayload') {
      throw new Error(`Project creation failed with payload ${payload.__typename}`);
    }
    return payload.project.id;
  }
}
