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
import { APIRequestContext, expect } from '@playwright/test';

const createProjectFromTemplateQuery = `
  mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
    createProjectFromTemplate(input: $input) {
      __typename
      ... on CreateProjectFromTemplateSuccessPayload {
        project {
          id
        }
        representationToOpen {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
  `;

const createProjectQuery = `
 mutation createProject($input: CreateProjectInput!) {
   createProject(input: $input) {
       __typename    
       ... on CreateProjectSuccessPayload {
             project {
                     id        
             }      
       }    
       ... on ErrorPayload {
             message     
      }  
    }
  }
  `;

const deleteProjectQuery = `
    mutation deleteProject($input: DeleteProjectInput!) {
      deleteProject(input: $input) {
        __typename
      }
    }
    `;

export class PlaywrightProject {
  readonly request: APIRequestContext;

  constructor(request: APIRequestContext) {
    this.request = request;
  }

  async createProjectFromTemplate(templateId: string): Promise<{ projectId: string; representationId: string }> {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        templateId: templateId,
      },
    };
    const response = await this.request.post('http://localhost:8080/api/graphql', {
      data: {
        query: createProjectFromTemplateQuery,
        variables,
      },
    });

    expect(response.ok()).toBeTruthy();
    const jsonResponse = await response.json();

    const payload = jsonResponse.data.createProjectFromTemplate;
    const projectId = payload.project.id;
    const representationToOpenId = payload.representationToOpen?.id;
    return { projectId, representationId: representationToOpenId };
  }

  async createProject(projectName: string): Promise<{ projectId: string }> {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        name: projectName,
        natures: [],
      },
    };
    const response = await this.request.post('http://localhost:8080/api/graphql', {
      data: {
        query: createProjectQuery,
        variables,
      },
    });

    expect(response.ok()).toBeTruthy();
    const jsonResponse = await response.json();

    const payload = jsonResponse.data.createProject;
    const projectId = payload.project.id;
    return { projectId };
  }

  async deleteProject(projectId: string) {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
      },
    };
    const response = await this.request.post('http://localhost:8080/api/graphql', {
      data: {
        query: deleteProjectQuery,
        variables,
      },
    });

    expect(response.ok()).toBeTruthy();
  }
}
