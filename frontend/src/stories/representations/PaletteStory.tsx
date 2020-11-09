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

import antenna from './images/antenna.svg';
import camera from './images/camera.svg';
import chipsetStandard from './images/chipset_standard.svg';
import chipset2Standard from './images/chipset2_standard.svg';
import fan from './images/fan.svg';

import { action } from '@storybook/addon-actions';
import { ContextualPalette } from 'diagram/palette/ContextualPalette';
import styles from './PaletteStory.module.css';

export const PaletteStory = () => {
  const descriptionId = 'descriptionId';
  const targetElement = { descriptionId: descriptionId };
  const tools = [
    {
      id: '1',
      label: 'Create first element',
      __typename: 'CreateNodeTool',
      targetDescriptions: [{ id: descriptionId }],
      imageURL: antenna,
    },
    {
      id: '2',
      label: 'Create second element',
      __typename: 'CreateNodeTool',
      targetDescriptions: [{ id: descriptionId }],
      imageURL: camera,
    },
    {
      id: '3',
      label: 'Create third element',
      __typename: 'CreateNodeTool',
      targetDescriptions: [{ id: descriptionId }],
      imageURL: chipsetStandard,
    },
    {
      id: '4',
      label: 'Create fourth element',
      __typename: 'CreateNodeTool',
      targetDescriptions: [{ id: descriptionId }],
      imageURL: chipset2Standard,
    },
    {
      id: '5',
      label: 'Create fifth element',
      __typename: 'CreateEdgeTool',
      edgeCandidates: [
        {
          sources: [{ id: descriptionId }],
          targets: [{ id: descriptionId }],
        },
      ],
      imageURL: fan,
    },
  ];

  const toolSections = [
    {
      id: '6',
      label: 'Tool Section',
      tools,
      defaultTool: tools[0],
    },
  ];

  const invokeTool = action('Invoke tool');
  const invokeDelete = action('Delete');
  const invokeLabelEdit = action('Edit');
  const invokeClose = action('Close');

  return (
    <div className={styles.paletteStory}>
      <div className={styles.contextualPalette}>
        <ContextualPalette
          toolSections={toolSections}
          targetElement={targetElement}
          invokeTool={invokeTool}
          invokeDelete={invokeDelete}
          invokeLabelEdit={invokeLabelEdit}
          invokeClose={invokeClose}
        />
      </div>
    </div>
  );
};
