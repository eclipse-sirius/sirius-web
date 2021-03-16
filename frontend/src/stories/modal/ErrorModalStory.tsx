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

import { ErrorModal } from 'modals/ErrorModal';
import React, { useState } from 'react';
import { MemoryRouter } from 'react-router-dom';
import { Border } from 'stories/common/Border';

export const ErrorModalStory = () => {
  const [state, setState] = useState(true);

  let modal;
  if (state) {
    modal = (
      <ErrorModal
        title="Error modal title"
        message="The main message"
        additionalMessages={[
          'some additional messages',
          'another one',
          'an additional message that could be display on several lines according to the browser ratio.',
          'a last message',
        ]}
        onClose={() => setState(false)}
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
