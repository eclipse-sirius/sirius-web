/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { ShareDiagramModal } from 'modals/share-diagram/ShareDiagramModal';
import React, { useState } from 'react';
import { MemoryRouter } from 'react-router-dom';
import { Border } from 'stories/common/Border';

export const ShareDiagramModalStory = () => {
  window.focus();
  const [state, setState] = useState(true);

  let modal;
  if (state) {
    modal = (
      <ShareDiagramModal
        onClose={() => setState(false)}
        url="http://localhost:3000/projects/9537e7be-bd31-4352-b9dc-97fc60f1a14c/edit/60bce9cd-0c68-4e4f-9a0d-e2e71d95f230"
      />
    );
  }

  return (
    <Border>
      <MemoryRouter>
        <div>
          <div>{modal}</div>
        </div>
      </MemoryRouter>
    </Border>
  );
};
