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
import { ActionButton, DangerButton, IconButton } from 'core/button/Button';
import { Edit } from 'icons/Edit';
import { Download } from 'icons/Download';
import { Delete } from 'icons/Delete';
import { ArrowCollapsed } from 'icons/ArrowCollapsed';
import { ArrowExpanded } from 'icons/ArrowExpanded';
import { More } from 'icons/More';

import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import styles from './ButtonStory.module.css';

export const ButtonStory = () => {
  return (
    <div className={styles.button}>
      <ButtonSection />
      <LinkSection />
      <IconButtonSection />
    </div>
  );
};

const ButtonSection = () => {
  return (
    <div className={styles.buttonSection}>
      Buttons
      <MemoryRouter>
        <ActionButton onClick={() => {}} label="Action Button" data-testid="action" />
        <DangerButton onClick={() => {}} label="Danger Button" data-testid="danger" />
      </MemoryRouter>
    </div>
  );
};

const LinkSection = () => {
  return (
    <div className={styles.linkSection}>
      Links
      <MemoryRouter>
        <ActionButton to="/" label="Action Link" data-testid="action" />
        <DangerButton to="/" label="Danger Link" data-testid="danger" />
      </MemoryRouter>
    </div>
  );
};

const IconButtonSection = () => {
  return (
    <div className={styles.iconButtonSection}>
      IconButtons
      <MemoryRouter>
        <div className={styles.iconButtonContent}>
          <IconButton className={styles.iconButton} data-testid="rename">
            <Edit title="Rename the model" />
          </IconButton>
          <IconButton className={styles.iconButton} data-testid="download">
            <Download title="Download the model" />
          </IconButton>
          <IconButton className={styles.iconButton} data-testid="delete">
            <Delete title="Delete the model" />
          </IconButton>
          <IconButton className={styles.iconButton} data-testid="delete">
            <ArrowCollapsed title="Expand" />
          </IconButton>
          <IconButton className={styles.iconButton} data-testid="delete">
            <ArrowExpanded title="Collapse" />
          </IconButton>
          <IconButton className={styles.iconButton} data-testid="delete">
            <More title="More" />
          </IconButton>
        </div>
      </MemoryRouter>
    </div>
  );
};
