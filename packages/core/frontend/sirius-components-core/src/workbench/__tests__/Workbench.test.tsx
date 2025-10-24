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
import { cleanup, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, expect, test, vi } from 'vitest';
import { Workbench } from '../Workbench';
import { RepresentationMetadata } from '../Workbench.types';
import { WorkbenchTestEnvironment } from './WorkbenchTestEnvironment';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

test('given a workbench, when no representation is opened, then the main area should be rendered', () => {
  render(
    <WorkbenchTestEnvironment initialSelection={null}>
      <Workbench
        editingContextId="editing-context-id"
        initialRepresentationSelected={null}
        initialWorkbenchConfiguration={null}
        onRepresentationSelected={() => {}}
        readOnly={false}
      />
    </WorkbenchTestEnvironment>
  );

  expect(screen.getByTestId('main-area')).not.toBeNull();
});

test('given a workbench, when views are provided, then they should be rendered', () => {
  render(
    <WorkbenchTestEnvironment initialSelection={null}>
      <Workbench
        editingContextId="editing-context-id"
        initialRepresentationSelected={null}
        initialWorkbenchConfiguration={null}
        onRepresentationSelected={() => {}}
        readOnly={false}
      />
    </WorkbenchTestEnvironment>
  );

  expect(screen.getByTestId('explorer-view')).not.toBeNull();
  expect(screen.getByTestId('details-view')).not.toBeNull();
});

test('given a workbench, when a representation is opened initially, then it should be rendered', () => {
  const representationMetadata: RepresentationMetadata = {
    id: 'representation-id',
    label: 'Representation',
    kind: 'kind',
    iconURLs: [],
    description: {
      id: 'representation-description-id',
    },
  };

  render(
    <WorkbenchTestEnvironment initialSelection={null}>
      <Workbench
        editingContextId="editing-context-id"
        initialRepresentationSelected={representationMetadata}
        initialWorkbenchConfiguration={null}
        onRepresentationSelected={() => {}}
        readOnly={false}
      />
    </WorkbenchTestEnvironment>
  );

  expect(screen.queryByTestId('main-area')).toBeNull();
  expect(screen.getByTestId('representation-id')).not.toBeNull();
});

test('given a workbench, when we open a representation, then it should be rendered', async () => {
  render(
    <WorkbenchTestEnvironment initialSelection={null}>
      <Workbench
        editingContextId="editing-context-id"
        initialRepresentationSelected={null}
        initialWorkbenchConfiguration={null}
        onRepresentationSelected={() => {}}
        readOnly={false}
      />
    </WorkbenchTestEnvironment>
  );

  expect(screen.getByTestId('main-area')).not.toBeNull();

  userEvent.click(screen.getByTestId('explorer-first-representation'));
  const firstRepresentation = await screen.findByTestId('first-representation');
  expect(firstRepresentation).not.toBeNull();
  expect(screen.queryByTestId('main-area')).toBeNull();
});

test('given a workbench, when ask for data accross workbench views, then we can retrieve it', async () => {
  render(
    <WorkbenchTestEnvironment initialSelection={null}>
      <Workbench
        editingContextId="editing-context-id"
        initialRepresentationSelected={null}
        initialWorkbenchConfiguration={null}
        onRepresentationSelected={() => {}}
        readOnly={false}
      />
    </WorkbenchTestEnvironment>
  );

  expect(screen.getByTestId('explorer-view')).not.toBeNull();
  expect(screen.getByTestId('details-view')).not.toBeNull();

  userEvent.click(screen.getByTestId('details-view-refresh'));
  expect(screen.getByTestId('details-view-content').textContent).toContain('Value from the explorer');
});
