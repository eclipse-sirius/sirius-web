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
import type { Meta, StoryObj } from '@storybook/react';
import { DisplayLibraryNavbarWrapper, NavigationBarWrapper } from '../../utils/navigationbar/NavigationBarWrapper';

const meta: Meta = {
  title: 'Bar/Navigation Bar',
  component: NavigationBarWrapper,
  tags: ['autodocs'],
  argTypes: {
    children: { name: 'Children', control: 'text' },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const NavigationBar: Story = {
  name: 'Default Navigation Bar',
  args: {
    children: 'Navigation Bar',
  },
  render: (args) => <NavigationBarWrapper {...args} />,
};

export const DisplayLibraryNavbar: Story = {
  name: 'Display Library Navigation Bar',
  argTypes: {
    children: {
      table: { disable: true },
    },
  },
  render: () => <DisplayLibraryNavbarWrapper />,
};
