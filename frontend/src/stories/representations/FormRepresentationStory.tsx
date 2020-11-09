/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React from 'react';
import { Border } from 'stories/common/Border';
import { Properties } from 'properties/Properties';

const form = {
  id: 'rey',
  label: 'Rey',
  pages: [
    {
      id: 'details',
      label: 'Details',
      groups: [
        {
          id: 'identity',
          label: 'Identity',
          widgets: [
            { id: 'name', __typename: 'Textfield', label: 'Name', stringValue: 'Rey' },
            { id: 'order', __typename: 'Textfield', label: 'Order', stringValue: 'Jedi' },
            { id: 'graduated', __typename: 'Checkbox', label: 'Graduated', booleanValue: true },
            { id: 'biography', __typename: 'Textarea', label: 'Biography', stringValue: 'To be written...' },
            {
              id: 'friends',
              __typename: 'List',
              label: 'Friends',
              items: [
                { id: 'luke', label: 'Luke' },
                { id: 'leia', label: 'Leia' },
                { id: 'han', label: 'Han' },
                { id: 'chewie', label: 'Chewie' },
              ],
            },
          ],
        },
        {
          id: 'capabilities',
          label: 'Capabilities',
          widgets: [
            {
              id: 'color',
              __typename: 'Select',
              label: 'Lightsaber Color',
              value: 'blue',
              options: [
                { id: 'blue', label: 'Blue' },
                { id: 'green', label: 'Green' },
                { id: 'red', label: 'Red' },
              ],
            },
            {
              id: 'powers',
              __typename: 'Radio',
              label: 'Powers',
              options: [
                { id: 'speed', label: 'Speed', selected: false },
                { id: 'lightsaber', label: 'Lightsaber', selected: true },
                { id: 'ghost', label: 'Force Ghost', selected: false },
                { id: 'mind-control', label: 'Mind Control', selected: false },
              ],
            },
          ],
        },
      ],
    },
  ],
};

export const FormRepresentationStory = () => {
  return (
    <Border>
      <Properties projectId="projectId" form={form} subscribers={[]} widgetSubscriptions={[]} />
    </Border>
  );
};
