/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import Renderer from 'react-test-renderer';

import { WorkflowCard } from '../WorkflowCard';

describe('WorkflowCard', () => {
  it('renders the workflow card', () => {
    const projectName = 'testProject';
    const pageIdentifier = 'testPage';
    const pages = [
      { identifier: 'testPage', name: 'Test Page' },
      { identifier: 'testPage1', name: 'Test Page 1' },
      { identifier: 'testPage2', name: 'Test Page 2' },
      { identifier: 'testPage3', name: 'Test Page 3' }
    ];
    const sections = [
      {
        identifier: 'testSection',
        name: 'Test Section',
        activities: [
          { identifier: 'testActivity', name: 'Test Activity' },
          { identifier: 'testActivity1', name: 'Test Activity 1' }
        ]
      },
      {
        identifier: 'testSection1',
        name: 'Test Section 1',
        activities: [{ identifier: 'testActivity', name: 'Test Activity' }]
      }
    ];
    const workflowCardComponent = Renderer.create(
      <WorkflowCard
        projectName={projectName}
        pageIdentifier={pageIdentifier}
        pages={pages}
        sections={sections}
      />
    );
    expect(workflowCardComponent.toJSON()).toMatchSnapshot();
  });
});
