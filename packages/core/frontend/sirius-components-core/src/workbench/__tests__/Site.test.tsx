/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { ThemeProvider } from '@material-ui/core/styles';
import { cleanup, fireEvent, render, screen } from '@testing-library/react';
import { afterEach, expect, test } from 'vitest';
import { theme } from '../../theme';
import { Site } from '../Site';
import { WorkbenchViewContribution } from '../WorkbenchViewContribution';

afterEach(() => cleanup());

const MockView1 = () => {
  return <div id="mock-view-1"></div>;
};

const MockView2 = () => {
  return <div id="mock-view-2"></div>;
};

const MockView3 = () => {
  return <div id="mock-view-3"></div>;
};

const hasClass = (element: HTMLElement, classPrefix: string) => {
  return Array.from(element.classList).some((className) => className.startsWith(classPrefix));
};

const isIconHighlighted = (title: string) => {
  return hasClass(screen.getByTestId('viewselector-' + title), 'makeStyles-viewSelectorIconSelectedLeft');
};

test('should render an empty site with no selector or view', () => {
  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {}}
        contributions={[]}
      />
    </ThemeProvider>
  );

  const selectors = screen.queryAllByTestId('viewselector-', { exact: false });
  expect(selectors.length).toBe(0);
  const views = screen.queryAllByTestId('view-', { exact: false });
  expect(views.length).toBe(0);
});

test('should initially display the first view', () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
    <WorkbenchViewContribution title="Third" side="left" icon={<div></div>} component={MockView3} />,
  ];
  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {}}
        contributions={contributions}
      />
    </ThemeProvider>
  );
  // All 3 selectors should be present
  const selectors = screen.queryAllByTestId('viewselector-', { exact: false });
  expect(selectors.length).toBe(3);
  // but only the first view component should be rendered
  expect(screen.getByTestId('view-First')).not.toBeNull();
  expect(screen.queryByTestId('view-Second')).toBeNull();
  expect(screen.queryByTestId('view-Third')).toBeNull();
});

test('should display a specific view when selected from its icon', () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
    <WorkbenchViewContribution title="Third" side="left" icon={<div></div>} component={MockView3} />,
  ];
  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {}}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  fireEvent.click(screen.getByTestId('viewselector-Second'));
  expect(screen.queryByTestId('view-First')).toBeNull();
  expect(screen.queryByTestId('view-Second')).not.toBeNull();
  expect(screen.queryByTestId('view-Third')).toBeNull();

  fireEvent.click(screen.getByTestId('viewselector-Third'));
  expect(screen.queryByTestId('view-First')).toBeNull();
  expect(screen.queryByTestId('view-Second')).toBeNull();
  expect(screen.queryByTestId('view-Third')).not.toBeNull();

  fireEvent.click(screen.getByTestId('viewselector-First'));
  expect(screen.queryByTestId('view-First')).not.toBeNull();
  expect(screen.queryByTestId('view-Second')).toBeNull();
  expect(screen.queryByTestId('view-Third')).toBeNull();
});

test('should highlight the icon of the selected view', () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
    <WorkbenchViewContribution title="Third" side="left" icon={<div></div>} component={MockView3} />,
  ];
  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {}}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  // Initial state: the first view is selected
  expect(isIconHighlighted('First')).toBeTruthy();
  expect(isIconHighlighted('Second')).toBeFalsy();
  expect(isIconHighlighted('Third')).toBeFalsy();

  fireEvent.click(screen.getByTestId('viewselector-Second'));
  expect(isIconHighlighted('First')).toBeFalsy();
  expect(isIconHighlighted('Second')).toBeTruthy();
  expect(isIconHighlighted('Third')).toBeFalsy();

  fireEvent.click(screen.getByTestId('viewselector-Third'));
  expect(isIconHighlighted('First')).toBeFalsy();
  expect(isIconHighlighted('Second')).toBeFalsy();
  expect(isIconHighlighted('Third')).toBeTruthy();

  fireEvent.click(screen.getByTestId('viewselector-First'));
  expect(isIconHighlighted('First')).toBeTruthy();
  expect(isIconHighlighted('Second')).toBeFalsy();
  expect(isIconHighlighted('Third')).toBeFalsy();
});

test("should toggle state when clicking on the selected view's icon (if expanded)", () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
  ];

  let toggleCalled = false;

  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {
          toggleCalled = true;
        }}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  fireEvent.click(screen.getByTestId('viewselector-First'));
  expect(isIconHighlighted('First')).toBeTruthy();
  expect(toggleCalled).toBeTruthy();
});

test("should toggle state when clicking on the selected view's icon (if collapsed)", () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
  ];

  let toggleCalled = false;

  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={false}
        toggleExpansion={() => {
          toggleCalled = true;
        }}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  fireEvent.click(screen.getByTestId('viewselector-First'));
  expect(isIconHighlighted('First')).toBeTruthy();
  expect(toggleCalled).toBeTruthy();
});

test('should toggle state when closed but a different view is selected', () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
  ];

  let toggleCalled = false;

  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={false}
        toggleExpansion={() => {
          toggleCalled = true;
        }}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  expect(isIconHighlighted('First')).toBeTruthy();

  fireEvent.click(screen.getByTestId('viewselector-Second'));
  expect(isIconHighlighted('First')).toBeFalsy();
  expect(isIconHighlighted('Second')).toBeTruthy();
  expect(toggleCalled).toBeTruthy();
});

test('should not toggle state when expanded and a different view is selected', () => {
  const contributions = [
    <WorkbenchViewContribution title="First" side="left" icon={<div></div>} component={MockView1} />,
    <WorkbenchViewContribution title="Second" side="left" icon={<div></div>} component={MockView2} />,
  ];

  let toggleCalled = false;

  render(
    <ThemeProvider theme={theme}>
      <Site
        editingContextId="editingContextId"
        selection={{ entries: [] }}
        setSelection={() => {}}
        readOnly={false}
        side="left"
        expanded={true}
        toggleExpansion={() => {
          toggleCalled = true;
        }}
        contributions={contributions}
      />
    </ThemeProvider>
  );

  expect(isIconHighlighted('First')).toBeTruthy();

  fireEvent.click(screen.getByTestId('viewselector-Second'));
  expect(isIconHighlighted('First')).toBeFalsy();
  expect(isIconHighlighted('Second')).toBeTruthy();
  expect(toggleCalled).toBeFalsy();
});
