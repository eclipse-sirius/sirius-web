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
import { MemoryRouter } from 'react-router-dom';

import { EditProjectNavbar } from 'navbar/EditProjectNavbar/EditProjectNavbar';
import { LoggedInNavbar } from 'navbar/LoggedInNavbar';
import { LoggedOutNavbar } from 'navbar/LoggedOutNavbar';
import { ProjectContext } from 'project/ProjectProvider';
import styles from './NavbarStory.module.css';

export const NavbarStory = () => {
  return (
    <div className={styles.navbar}>
      <LoggedOutNavbarSection />
      <LoggedInNavbarSection />
      <EditProjectNavbarSection />
    </div>
  );
};

const LoggedOutNavbarSection = () => {
  return (
    <div className={styles.section}>
      LoggedOutNavbar
      <MemoryRouter>
        <LoggedOutNavbar />
      </MemoryRouter>
    </div>
  );
};

const LoggedInNavbarSection = () => {
  return (
    <div className={styles.section}>
      LoggedInNavbar
      <MemoryRouter>
        <LoggedInNavbar />
      </MemoryRouter>
    </div>
  );
};

const EditProjectNavbarSection = () => {
  return (
    <div className={styles.section}>
      EditProjectNavbar
      <MemoryRouter>
        <ProjectContext.Provider
          value={{
            id: 'randomId',
            name: 'Top Secret',
            canRead: true,
            canEdit: true,
            canAdmin: true,
          }}>
          <EditProjectNavbar subscribers={[{ username: 'sbegaudeau' }, { username: 'jdoe' }]} />
        </ProjectContext.Provider>
      </MemoryRouter>
    </div>
  );
};
